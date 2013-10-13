package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
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
    private GUIController gc = null;
    
    private MouseAndKeyListener mkListener = null;
    protected boolean collision = false;
    
    public SelectionManager(GUIController contr){
        selectedShapes = new ArrayList<ShapeObject>();     
        
        gc = contr;
        
        mkListener = new MouseAndKeyListener(gc);
        
        WindowDrawingPanel drawingPan = gc.drawingPan;
        drawingPan.addMouseListener(mkListener);
        drawingPan.addMouseMotionListener(mkListener);
        drawingPan.addMouseWheelListener(mkListener);
        gc.frame.addKeyListener(mkListener);
    }
    
    /**
     * Adds/removes one specific shape to/from the selection.
     * 
     * @param shape the shape in question.
     * @param selected true, if selected, false if otherwise.
     */
    public void setSelected(ShapeObject shape, boolean selected){
        shape.setSelected(selected);
        
        if(selected){
            selectedShapes.add(shape);
            //controler.getShapeManager().createDraggingShape(shape);
        }else{
            selectedShapes.remove(shape);
            //controler.getShapeManager().removeDraggingShape(shape);
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
            //controler.getShapeManager().removeDraggingShape(shape);
        }else{
            selectedShapes.add(shape);
            //controler.getShapeManager().createDraggingShape(shape);
        }
        shape.switchSelection();
    }
    
    /**
     * Removes all shapes from the current selection.
     */
    public void removeCurSelection(){
        
        for(ShapeObject shape : selectedShapes){
            shape.setSelected(false);
        }
        gc.sm.clearDraggingShapes();
        selectedShapes.clear();
    }
    
    /**
     * Moves the dragging shapes, which are shown, when shapes are dragged. 
     * 
     * @param x the new x value, where the shape is being dragged.
     * @param y the new y value, where the shape is being dragged.
     * @param start the start point from where the shape has been dragged.
     */
    public void translateShape(int x, int y, Point start, sDraggingShapeStrategy strategy){
        
        double scale = gc.drawingPan.getScale();
        int distance_x = start.x - x;
        int distance_y = start.y - y;
            
        double distance = start.distance(x, y);
        distance = distance / scale;

        ShapeManager sm = gc.sm;
        sm.setStrategy(strategy);
        sm.createDraggingShapes(selectedShapes);
        sm.translateDraggingShapes( distance_x/scale, distance_y/scale );
        collision = sm.checkForCollision();
          
        gc.drawingPan.repaint();
        
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
        gc.sm.setSelectionRect(x,y,width, height);
        
    }

    /**
     * Searches for shapes which are in the selection rectangle and
     * adds them to the selection.
     */
    public void selectionRectReleased() {
        
        ArrayList<ShapeObject> list = gc.sm.getShapesInSelectionRect();
        
        for(ShapeObject shape : list){
            setSelected(shape, true);
        }
    }
    
    /**
     * Searches for shapes which are in the selection rectangle and 
     * switches their selection.
     */
    public void selectionRectShiftReleased(){
        ArrayList<ShapeObject> list = gc.sm.getShapesInSelectionRect();

        for(ShapeObject shape : list){
            switchSelection(shape);
        }
    }
    
    public void copyCurrentSelection(){
        gc.scm.copyShapes(selectedShapes);
    }
        
    /**
     * Deletes all shapes, which are in the current selection.
     */
    public void deleteCurrentSelection(){        
        for(ShapeObject shape : selectedShapes){
            gc.setObjectRemoval(shape.getID());
        }
    }
    
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
    
    public int countSelected(){
        return selectedShapes.size();
    }

}
