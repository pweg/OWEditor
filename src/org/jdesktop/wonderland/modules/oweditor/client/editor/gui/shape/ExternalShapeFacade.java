package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

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
    public void createShape(TranslatedObjectInterface dataObject) {
        sm.getDataUpdate(dataObject);
    }

    @Override
    public void removeShape(long id) {
        sm.removeShape(id);
    }

    @Override
    public void updateShapeCoordinates(long id, int x, int y) {
        sm.translateShape(id, x, y);
    }

    @Override
    public void updateShape(long id, int x, int y, String name) {
        sm.changeShape(id, x, y, name);
    }

    @Override
    public void clearCurSelection() {
        ssm.clearCurSelection();
    }

    @Override
    public void clean() {
        stm.clearDraggingShapes();
        sm.removeSelectionRect();
    }
    
    @Override
    public void translation(int x, int y, Point start){
        ssm.translateShape(x, y, start, new sCollisionNotSelectedStrategy(smi));
    }

    @Override
    public void copyTranslate(int x, int y, Point start) {
        ssm.translateShape(x,y,start, new sCollisionAllStrategy());
    }

    @Override
    public boolean isMouseInObject(Point point) {
        return sm.isMouseInObject(point);
    }

    @Override
    public ShapeObject getShapeSuroundingPoint(Point p) {
        return sm.getShapeSuroundingPoint(p);
    }

    @Override
    public void selectionUpdate(Point start, Point end) {
        ssm.resizeSelectionRect(start, end);
    }
    
    @Override
    public void selectionReleased(){
        ssm.selectionRectReleased();         
        sm.removeSelectionRect();
    }

    @Override
    public boolean selectionSwitch(Point p) {
        ShapeObject shape = sm.getShapeSuroundingPoint(p);
        
        if(shape == null)
            return false;
        
        ssm.switchSelection(shape);
        return true;
    }

    @Override
    public void deleteCurrentSelection() {
        ssm.deleteCurrentSelection();
    }

    @Override
    public Point copyInitialize() {
        scm.initilaizeCopy();
        return ssm.getSelectionCenter();
    }

    @Override
    public void drawShapes(Graphics2D g2, AffineTransform at, double scale) {
        sm.drawShapes(g2, at, scale);
    }


    @Override
    public void createCopyShapes() {
        stm.createDraggingShapes(scm.getCopyShapes());
    }

    @Override
    public void copySave() {
        if(!stm.checkForCollision()){
            gc.setCopyUpdate(stm.getDraggingShapes());
        }
        stm.clearDraggingShapes();
    }

    @Override
    public void translationSave() {

        if(!stm.checkForCollision()){
            gc.setTranslationUpdate(stm.getDraggingShapes());
        }
        stm.clearDraggingShapes();
    }

    @Override
    public void translationInitialization(Point p) {
        
        ShapeObject shape = sm.getShapeSuroundingPoint(p);
        
        if(shape == null)
            return;
        
        /* When the shape is not selected, all other selected
        * all other selections will be removed.
        */
        if(!shape.isSelected()){
            ssm.clearCurSelection();
            ssm.setSelected(shape, true);
        }
        stm.createDraggingShapes(ssm.getSelection());
    }

    @Override
    public void popupInitialize(Point p) {
            
        ShapeObject shape = sm.getShapeSuroundingPoint(p);
        
        if(!ssm.isCurrentlySelected(shape)){
            ssm.clearCurSelection();
            ssm.setSelected(shape, true);
        }
    }

}
