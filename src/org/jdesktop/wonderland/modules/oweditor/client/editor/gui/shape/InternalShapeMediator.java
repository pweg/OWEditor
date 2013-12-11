package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Point;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.AdapterCommunicationInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.FrameToShapeInterface;

public class InternalShapeMediator implements InternalShapeMediatorInterface{
    
    private ShapeManager sm = null;
    private SelectionManager ssm = null;
    private TranslationManager stm = null;
    private AdapterCommunicationInterface adapter = null;
    private FrameToShapeInterface frame = null;
    
    public InternalShapeMediator(AdapterCommunicationInterface adapter) {
        this.adapter = adapter;
    }

    @Override
    public void registerShapeManager(ShapeManager sm) {
        this.sm = sm;
    }

    @Override
    public void registerTranslationManager(TranslationManager stm) {
        this.stm = stm;
    }

    @Override
    public void registerSelectionManager(SelectionManager ssm) {
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
        sm.clearDraggingShapes();
    }

    @Override
    public double getScale() {
        return frame.getScale();
    }

    @Override
    public void translateDraggingShapes(double distance_x, double distance_y) {
        stm.translateDraggingShapes(distance_x, distance_y);
    }

    @Override
    public void createDraggingShapes(ArrayList<ShapeObject> selectedShapes) {
        sm.createDraggingShapes(selectedShapes);
    }

    @Override
    public void repaint() {
        frame.repaint();
    }

    @Override
    public void createSelectionRect(int x, int y, int width, int height) {
        sm.createSelectionRect(x, y, width, height);
    }

    @Override
    public ArrayList<ShapeObject> getShapesInSelectionRect() {
        return ssm.getShapesInSelectionRect();
    }

    @Override
    public void setObjectRemoval(long id) {
        adapter.setObjectRemoval(id);
    }

    @Override
    public void clearCurSelection() {
        ssm.clearCurSelection();
    }

    @Override
    public ArrayList<ShapeDraggingObject> getDraggingShapes() {
        return sm.getDraggingShapes();
    }

    @Override
    public SimpleShapeObject getSelectionRectangle() {
        return sm.getSelectionRectangle();
    }

    @Override
    public ShapeObject getShape(long id) {
        return sm.getShape(id);
    }

    @Override
    public ShapeObjectBorder getShapeBorder() {
        return sm.getShapeBorder();
    }

    @Override
    public void registerFrameInterface(
            FrameToShapeInterface frameInterface) {
        this.frame = frameInterface;
    }

    @Override
    public Point revertBack(Point p) {
        return frame.revertBack(p);
    }
    
    

}
