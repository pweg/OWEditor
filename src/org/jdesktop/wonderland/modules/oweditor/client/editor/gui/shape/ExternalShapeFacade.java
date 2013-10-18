package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.TranslatedObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUIController;

public class ExternalShapeFacade implements ExternalShapeFacadeInterface{
    
    private GUIController gc = null;
    private InternalShapeMediatorInterface smi = null;

    private ShapeManager sm = null;
    private ShapeCopyManager scm = null;
    private ShapeRotationManager srm = null;
    private ShapeSelectionManager ssm = null;
    private ShapeTranslationManager stm = null;

    public ExternalShapeFacade(GUIController gc){
        this.gc  = gc;
        this.smi = new InternalShapeMediator(gc);

        sm = new ShapeManager(smi);
        stm = new ShapeTranslationManager(smi);
        ssm = new ShapeSelectionManager(smi);
        srm = new ShapeRotationManager(smi);
        scm = new ShapeCopyManager(smi);
        
        smi.registerCopyManager(scm);
        smi.registerRotationManager(srm);
        smi.registerSelectionManager(ssm);
        smi.registerShapeManager(sm);
        smi.registerTranslationManager(stm);
    }

    @Override
    public void getDataUpdate(TranslatedObjectInterface dataObject) {
        sm.getDataUpdate(dataObject);
    }

    @Override
    public void removeShape(long id) {
        sm.removeShape(id);
    }

    @Override
    public void translateShape(long id, int x, int y) {
        sm.translateShape(id, x, y);
    }

    @Override
    public void changeShape(long id, int x, int y, String name) {
        sm.changeShape(id, x, y, name);
    }

    @Override
    public void clearCurSelection() {
        ssm.clearCurSelection();
    }

    @Override
    public boolean checkCollision() {
        return stm.checkForCollision();
    }

    @Override
    public void clearDraggingShapes() {
        stm.clearDraggingShapes();
    }

    @Override
    public void saveDraggingShapes() {
        stm.saveDraggingShapes();
    }

    
    @Override
    public void translateShapeNormal(int x, int y, Point start){
        ssm.translateShape(x, y, start, new sCollisionNotSelectedStrategy(smi));
    }

    @Override
    public void translateShapeCopy(int x, int y, Point start) {
        ssm.translateShape(x,y,start, new sCollisionAllStrategy(smi));
    }

    @Override
    public void setSelected(ShapeObject shape, boolean selected) {
       ssm.setSelected(shape, selected);
    }

    @Override
    public boolean isMouseInObject(Point point) {
        return sm.isMouseInObject(point);
    }

    @Override
    public ArrayList<ShapeObject> getDraggingShapes() {
        return stm.getDraggingShapes();
    }

    @Override
    public void setTranslatedShapes() {
        scm.setTranslatedShapes(getDraggingShapes());
    }

    @Override
    public ShapeObject getShapeSuroundingPoint(Point p) {
        return sm.getShapeSuroundingPoint(p);
    }

    @Override
    public ArrayList<ShapeObject> getAllShapes() {
        return smi.getAllShapes();
    }

    @Override
    public boolean isCurrentlySelected(ShapeObject shape) {
        return ssm.isCurrentlySelected(shape);
    }

    @Override
    public void selectionRectShiftReleased() {
        ssm.selectionRectShiftReleased();
    }

    @Override
    public void removeSelectionRect() {
        sm.removeSelectionRect();
    }

    @Override
    public void resizeSelectionRect(Point start, Point end) {
        ssm.resizeSelectionRect(start, end);
    }

    @Override
    public void selectionRectReleased() {
        ssm.selectionRectReleased();
    }

    @Override
    public void switchSelection(ShapeObject shape) {
        ssm.switchSelection(shape);
    }

    @Override
    public void deleteCurrentSelection() {
        ssm.deleteCurrentSelection();
    }

    @Override
    public void initilaizeCopy() {
        scm.initilaizeCopy();
    }

    @Override
    public Point getSelectionCenter() {
        return ssm.getSelectionCenter();
    }

    @Override
    public void drawShapes(Graphics2D g2, AffineTransform at, double scale) {
        sm.drawShapes(g2, at, scale);
    }

    @Override
    public ArrayList<ShapeObject> getUpdateShapes() {
        return stm.getUpdateShapes();
    }

    @Override
    public ArrayList<ShapeObject> getTranslatedShapes() {
        return scm.getTranslatedShapes();
    }

    @Override
    public void createDraggingShapes() {
        stm.createDraggingShapes(ssm.getSelection());
    }

    @Override
    public void createCopyShapes() {
        stm.createDraggingShapes(scm.getCopyShapes());
    }

}
