package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Point;

public class TranslationManager {

    //These are shapes for updates.
    private sCollisionStrategy strategy = null;
    private InternalShapeMediatorInterface smi = null;

    public TranslationManager(InternalShapeMediatorInterface smi){
        
        this.smi = smi;
    }

    /**
     * Translates a shape with the id to the specified
     * coordinates. This is called, when an update
     * comes from the data package
     * 
     * @param id the id of the shape to translate.
     * @param x the new x coordinate.
     * @param y the new y coordinate.
     */
    public void translateShape(long id, int x, int y){
        ShapeObject shape = smi.getShape(id);
        shape.setLocation(x, y);
    }

    /**
     * Moves the dragging shapes, which are shown, when shapes are dragged. 
     * 
     * @param x the new x value, where the shape is being dragged.
     * @param y the new y value, where the shape is being dragged.
     * @param start the start point from where the shape has been dragged.
     */
    public void translateShape(int x, int y, Point start, sCollisionStrategy strategy){
        
        this.strategy = strategy;
        int distance_x = start.x - x;
        int distance_y = start.y - y;

        translateDraggingShapes(distance_x*(-1), distance_y*(-1));
        checkForCollision();
          
        smi.repaint();
        
    }

    /**
     * Translates the dragging shapes(DraggingRectangles), which 
     * are seen, when trying to move objects.
     * 
     * @param selectedShapes all shapes which are selected and should be moved.
     * @param distance_x the x distance between old and new point.
     * @param distance_y the y distance between old and new point.
     */
    public void translateDraggingShapes(double distance_x, double distance_y){
        
        for(ShapeDraggingObject selShape : smi.getDraggingShapes()){
            selShape.setTranslation(distance_x, distance_y);
        }
    }
    
    public void setStrategy(sCollisionStrategy strategy){
        this.strategy = strategy;
    }

    /**
     * Checks for collision when dragging shapes.
     * 
     * @return returns true, if a collision is detected and false otherwise.
     */
    public boolean checkForCollision() {
        if(strategy == null)
            return true;
        
        return strategy.checkForCollision(smi.getAllShapes(), smi.getDraggingShapes());
    }

}
