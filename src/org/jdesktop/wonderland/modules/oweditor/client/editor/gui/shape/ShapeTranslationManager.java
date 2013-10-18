package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUIController;

public class ShapeTranslationManager {

    private ArrayList<ShapeObject> draggingShapes = null;

    //These are shapes for updates.
    private ArrayList<ShapeObject> updateShapes = null;
    private sCollisionStrategy strategy = null;
    private InternalShapeMediatorInterface smi = null;
    
    public ShapeTranslationManager(InternalShapeMediatorInterface smi){
        
        this.smi = smi;
        updateShapes = new ArrayList<ShapeObject>();
        draggingShapes = new ArrayList<ShapeObject>();
    }
    

    /**
     * Translates the dragging shapes(DraggingRectangles), which 
     * are seen, when trying to move objects.
     * 
     * @param selectedShapes all shapes which are selected and should be moved.
     * @param distance_x the x distance between old and new point.
     * @param distance_y the y distance between old and new point.
     */
    public void translateDraggingShapes( double distance_x, double distance_y){
        
        for(ShapeObject selShape : draggingShapes){
            selShape.setTranslation(distance_x, distance_y);
        }
    }
        
    public boolean isDraggingShapesEmpty(){
        
        if(draggingShapes.size() > 0)
            return false;
        return true;
    }

    /**
     * Removes all dragging shapes.
     */
    public void clearDraggingShapes() {
        draggingShapes.clear();
    }

    /**
     * Saves all dragging shapes for future update.
     * This is done, if the moving was successful.
     */
    public void saveDraggingShapes() {
        updateShapes.addAll(draggingShapes);
        draggingShapes.clear();
    }
    
    public ArrayList<ShapeObject> getDraggingShapes(){
        return draggingShapes;
    }
    
    /**
     * Returns all shapes, which need to be updated.
     * 
     * @return ArrayList which contains all shapes for future update.
     */
    public ArrayList<ShapeObject> getUpdateShapes() {
        ArrayList<ShapeObject> list = new ArrayList<ShapeObject>();
        list.addAll(updateShapes);
        updateShapes.clear();
        return list;
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
        
        return strategy.checkForCollision(smi.getAllShapes(), draggingShapes);
    }

    public void createDraggingShapes(ArrayList<ShapeObject> shapes){
        smi.createDraggingShapes(shapes);
    }


    public void setDraggingShapes(ArrayList<ShapeObject> shapes) {
        draggingShapes.clear();
        draggingShapes.addAll(shapes);
        
    }

}
