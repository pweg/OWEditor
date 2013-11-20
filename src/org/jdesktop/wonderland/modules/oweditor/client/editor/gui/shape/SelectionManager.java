package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * This class handles the pick up and drag feature for shapes,
 * as well as their selection and forwards changes for key presses
 * to the ShapeManager.
 * @author Patrick
 *
 */
public class SelectionManager {

    private ArrayList<ShapeObject> selectedShapes = null;
    private InternalShapeMediatorInterface smi = null;
    
    public SelectionManager(InternalShapeMediatorInterface smi){
        selectedShapes = new ArrayList<ShapeObject>();     
        
        this.smi = smi;
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
            selectedShapes.add(shape);
        }else{
            selectedShapes.remove(shape);
        }
    }
    
    /**
     * Switches the selection of a shape. When a shape was
     * selected it will be unselected and vice versa.
     * 
     * @param shape the shape in question.
     */
    public void switchSelection(ShapeObject shape){
        if(shape.isSelected()){
            selectedShapes.remove(shape);   
        }else{
            selectedShapes.add(shape);
        }
        shape.switchSelection();
    }
    
    /**
     * Removes all shapes from the current selection.
     */
    public void clearCurSelection(){
        
        for(ShapeObject shape : selectedShapes){
            shape.setSelected(false);
        }
        smi.clearDraggingShapes();
        selectedShapes.clear();
    }
        
    public boolean isCurrentlySelected(ShapeObject shape){
        long id = shape.getID();
        
        for(ShapeObject s : selectedShapes){
            if(id == s.getID())
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
        
        int width = 0;
        int height = 0;
        int x = 0;
        int y = 0;

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
        SimpleShapeObject r = smi.getSelectionRectangle();
        
        if (r == null)
            smi.createSelectionRect(x,y,width,height); 
        else
            r.set(x, y, width, height);
        
        //smi.setSelectionRect(x,y,width, height);
        
    }

    /**
     * Searches for shapes which are in the selection rectangle and
     * adds them to the selection.
     */
    public void selectionRectReleased() {
        
        ArrayList<ShapeObject> list = smi.getShapesInSelectionRect();
        
        for(ShapeObject shape : list){
            switchSelection(shape);
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
        return selectedShapes;
    }
        
    /**
     * Deletes all shapes, which are in the current selection.
     */
    public void deleteCurrentSelection(){        
        for(ShapeObject shape : selectedShapes){
            smi.setObjectRemoval(shape.getID());
        }
        smi.clearCurSelection();
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
        
        for(ShapeObject shape : selectedShapes){
            int s_x = (int) Math.round(shape.getX()+shape.getWidth()/2);
            int s_y = (int) Math.round(shape.getY()+shape.getHeight()/2);

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

    public Point getSelectionCoords(){
        
        int min_x = Integer.MAX_VALUE;
        int min_y = Integer.MAX_VALUE;
        
        for(ShapeObject shape : selectedShapes){
            int s_x = shape.getTransformedShape().getBounds().x;
            int s_y = shape.getTransformedShape().getBounds().y;

            if(s_x < min_x)
                min_x = s_x;
            if(s_y < min_y)
                min_y = s_y;
        }
        
        return new Point(min_x,min_y);
    }

    
    /**
     * Searches for shape objects which are enclosed by the 
     * selection rectangle. 
     * 
     * @return an arraylist with shape objects. If
     * no object is in the selection rectangle, it returns an empty list.
     */
    public ArrayList<ShapeObject> getShapesInSelectionRect(){
        ArrayList<ShapeObject> list = new ArrayList<ShapeObject>();
        
        
        if(smi.getSelectionRectangle() == null)
            return list;
        
        for(ShapeObject shape : smi.getAllShapes()){
            Rectangle bounds = shape.getTransformedShape().getBounds();
            
            if(smi.getSelectionRectangle().getShape().contains(bounds)){
                list.add(shape);
            }
        }
        return list;
    }

}
