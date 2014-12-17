package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.ShapeObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.SimpleShapeObject;

/**
 * This class handles everything regarding selecting 
 * shapes.
 * 
 * @author Patrick
 *
 */
public class SelectionManager {

    private ArrayList<Long> selectedShapes = null;
    private IInternalMediator med = null;
    
    public SelectionManager(IInternalMediator med){ 
        selectedShapes = new ArrayList<Long>();
        
        this.med = med;
    }
    
    /**
     * Adds/removes one specific shape to/from the selection.
     * 
     * @param shape the shape in question.
     * @param selected true, if selected, false if otherwise.
     */
    public void setSelected(ShapeObject shape, boolean selected){
        if(shape == null)
            return;
        
        shape.setSelected(selected);
        
        if(selected){
            selectedShapes.add(shape.getID());
        }else{
            selectedShapes.remove(shape.getID());
        }
    }
    
    /**
     * Switches the selection of a shape. When a shape was
     * selected it will be unselected and vice versa.
     * 
     * @param shape the shape in question.
     * @param mode false: does only switch, if the shape is not selected, 
     * clearing the current selection. true: switches every time.
     */
    public void switchSelection(ShapeObject shape, boolean mode){
              
        if(shape.isSelected()){
            if(mode == true){
                selectedShapes.remove(shape.getID());   
            }
            else
                return;
        }else{
            if(mode == false)
                clearCurSelection();
            selectedShapes.add(shape.getID());
        }
        shape.switchSelection();
    }
    
    /**
     * Removes all shapes from the current selection.
     */
    public void clearCurSelection(){
        ArrayList<ShapeObject> shapes = med.getShapes(selectedShapes);
        
        for(ShapeObject shape : shapes){
            shape.setSelected(false);
        }
        med.clearDraggingShapes();
        selectedShapes.clear();
    }
        
    public boolean isCurrentlySelected(ShapeObject shape){
        long id = shape.getID();
        
        for(Long shape_id : selectedShapes){
            if(id == shape_id)
                return true;
        }
        return false;
    }
    
    /**
     * Resizes the selection rectangle.
     * 
     * @param start the start position of the rectangle
     * @param end the end position of the rectangle
     */
    public void resizeSelectionRect(Point start, Point end) {

        int sx = start.x;
        int sy = start.y;
        int ex = end.x;
        int ey = end.y;
        
        int width;
        int height;
        int x;
        int y;

        if(sx > ex){
            width = sx-ex;
            x=ex;
        }else{
            width = ex-sx;
            x=sx;
        }
        
        if(sy > ey){
            height = sy-ey;
            y=ey;
        }else{
            height = ey-sy;
            y=sy;
        }
        SimpleShapeObject r = med.getSelectionRectangle();
        
        if (r == null)
            med.createSelectionRect(x,y,width,height); 
        else
            r.set(x, y, width, height);
        
        //smi.setSelectionRect(x,y,width, height);
        
    }

    /**
     * Searches for shapes which are in the selection rectangle and
     * adds them to the selection.
     */
    public void selectionRectReleased() {
        
        ArrayList<ShapeObject> list = getShapesInSelectionRect();
        
        for(ShapeObject shape : list){
            shape.setSelected(true);
            selectedShapes.add(shape.getID());
            //switchSelection(shape, true);
        }
    }
    
    /**
     * Returns all selected shapes.
     * 
     * @return all shapes that are currently selected.
     * An empty list is returned, when no shape is 
     * selected.
     */
    public ArrayList<ShapeObject> getSelection(){
        return med.getShapes(selectedShapes);
    }
    
    public ArrayList<Long> getSelectionID(){
        return selectedShapes;
    }
        
    /**
     * Deletes all shapes, which are in the current selection.
     */
    public void deleteCurrentSelection(){   
        med.setObjectRemoval(selectedShapes);
        med.clearCurSelection();
    }
    
    /**
     * Returns the center point of the
     * current selected shapes.
     * 
     * @return a point, which is at the
     * center of all currently selected objects.
     */
    public Point getSelectionCenter(){
        
        int min_x = Integer.MAX_VALUE;
        int max_x = Integer.MIN_VALUE;
        int min_y = Integer.MAX_VALUE;
        int max_y = Integer.MIN_VALUE;
        
        ArrayList<ShapeObject> shapes = med.getShapes(selectedShapes);
        
        for(ShapeObject shape : shapes){
            Rectangle bounds = shape.getTransformedShape().getBounds();
            int s_x = (int) Math.round(bounds.getX()+bounds.getWidth()/2);
            int s_y = (int) Math.round(bounds.getY()+bounds.getHeight()/2);

            if(s_x > max_x)
                max_x = s_x;
            if(s_x < min_x)
                min_x = s_x;
            if(s_y > max_y)
                max_y = s_y;
            if(s_y < min_y)
                min_y = s_y;
        }
        int x = (int) Math.round(min_x + (max_x-min_x)/2);
        int y = (int) Math.round(min_y + (max_y-min_y)/2);
        return new Point(x,y);
    }
    
    /**
     * Searches for shape objects which are enclosed by the 
     * selection rectangle. 
     * 
     * @return An ArrayList with shape objects. If
     * no object is in the selection rectangle, it returns an empty list.
     */
    public ArrayList<ShapeObject> getShapesInSelectionRect(){
        ArrayList<ShapeObject> list = new ArrayList<ShapeObject>();
        
        
        if(med.getSelectionRectangle() == null)
            return list;
        
        for(ShapeObject shape : med.getAllShapes()){
            Rectangle bounds = shape.getTransformedShape().getBounds();
            
            if(med.getSelectionRectangle().getShape().contains(bounds)){
                list.add(shape);
            }
        }
        return list;
    }

}
