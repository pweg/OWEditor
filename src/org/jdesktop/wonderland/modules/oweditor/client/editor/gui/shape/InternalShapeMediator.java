package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Point;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUIController;

public class InternalShapeMediator implements InternalShapeMediatorInterface{
    
    private ShapeManager sm = null;
    private ShapeCopyManager scm = null;
    private ShapeRotationManager srm = null;
    private ShapeSelectionManager ssm = null;
    private ShapeTranslationManager stm = null;
    private GUIController gc = null;
    
    public InternalShapeMediator(GUIController gc) {
        this.gc = gc;
    }

    @Override
    public void registerShapeManager(ShapeManager sm) {
        this.sm = sm;
    }

    @Override
    public void registerTranslationManager(ShapeTranslationManager stm) {
        this.stm = stm;
    }

    @Override
    public void registerCopyManager(ShapeCopyManager scm) {
        this.scm = scm; 
    }

    @Override
    public void registerRotationManager(ShapeRotationManager srm) {
        this.srm = srm;
    }

    @Override
    public void registerSelectionManager(ShapeSelectionManager ssm) {
        this.ssm = ssm;
    }


    @Override
    public ArrayList<ShapeObject> getAllShapes() {
        return sm.getShapes();
    }

    @Override
    public ArrayList<ShapeObject> getSelectedShapes() {
        return ssm.getSelection();
    }

    @Override
    public void clearDraggingShapes() {
        stm.clearDraggingShapes();
    }

    @Override
    public double getScale() {
        return gc.getDrawingPan().getScale();
    }


    @Override
    public boolean checkForCollision(sCollisionStrategy strategy) {
        stm.setStrategy(strategy);
        return stm.checkForCollision();
    }

    @Override
    public void translateDraggingShapes(double distance_x, double distance_y) {
        stm.translateDraggingShapes(distance_x, distance_y);
    }

    @Override
    public void createDraggingShapes(ArrayList<ShapeObject> selectedShapes) {
        stm.setDraggingShapes(sm.builtDraggingShapes(selectedShapes));
    }

    @Override
    public void repaint() {
        gc.getDrawingPan().repaint();
    }

    @Override
    public void setSelectionRect(int x, int y, int width, int height) {
        sm.setSelectionRect(x, y, width, height);
    }

    @Override
    public ArrayList<ShapeObject> getShapesInSelectionRect() {
        return sm.getShapesInSelectionRect();
    }

    @Override
    public void setObjectRemoval(long id) {
        gc.setObjectRemoval(id);
    }

    @Override
    public void clearCurSelection() {
        ssm.clearCurSelection();
    }

    @Override
    public ArrayList<ShapeObject> getDraggingShapes() {
        return stm.getDraggingShapes();
    }

}
