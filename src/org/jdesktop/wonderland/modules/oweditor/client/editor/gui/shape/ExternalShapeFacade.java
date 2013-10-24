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
    private CopyManager scm = null;
    private RotationManager srm = null;
    private SelectionManager ssm = null;
    private TranslationManager stm = null;

    public ExternalShapeFacade(GUIController gc){
        this.gc  = gc;
        this.smi = new InternalShapeMediator(gc);

        sm = new ShapeManager(smi);
        stm = new TranslationManager(smi);
        ssm = new SelectionManager(smi);
        srm = new RotationManager(smi);
        scm = new CopyManager(smi);
        
        smi.registerSelectionManager(ssm);
        smi.registerShapeManager(sm);
        smi.registerTranslationManager(stm);
    }

    @Override
    public void createShape(TranslatedObjectInterface dataObject) {
        sm.setDataUpdate(dataObject);
    }

    @Override
    public void removeShape(long id) {
        sm.removeShape(id);
    }

    @Override
    public void updateShapeCoordinates(long id, int x, int y) {
        stm.translateShape(id, x, y);
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
    public void cleanHelpingShapes() {
        sm.clearDraggingShapes();
        sm.removeSelectionRect();
    }

    @Override
    public void cleanAll() {
        sm.clearDraggingShapes();
        sm.removeSelectionRect();
        sm.removeBorder();
    }
    
    @Override
    public void translation(int x, int y, Point start){
        stm.translateShape(x, y, start, new sCollisionNotSelectedStrategy(smi));
    }

    @Override
    public boolean isMouseInObject(Point point) {
        ShapeObject shape = sm.getShapeSuroundingPoint(point);
        
        if(shape == null)
            return false;
        else
            return true;
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
    public void pasteInitialize() {
        stm.createDraggingShapes(scm.getCopyShapes());
        sm.setShapeStates(new stateDraggingShapeTranslation());
    }

    @Override
    public void pasteTranslate(int x, int y, Point start) {
        stm.translateShape(x,y,start, new sCollisionAllStrategy());
    }

    @Override
    public void pasteInsertShapes() {
        if(!stm.checkForCollision()){
            gc.setCopyUpdate(sm.getDraggingShapes());
        }
        sm.clearDraggingShapes();
    }

    @Override
    public void translationSetUpdate() {

        if(!stm.checkForCollision()){
            gc.setTranslationUpdate(sm.getDraggingShapes());
        }
        sm.clearDraggingShapes();
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
        sm.setShapeStates(new stateDraggingShapeTranslation());
    }

    @Override
    public boolean popupInitialize(Point p) {
            
        ShapeObject shape = sm.getShapeSuroundingPoint(p);
        
        if(shape == null){
            if(ssm.getSelection().size() == 0)
                return false;
            else
                return true;
        }
        
        if(!ssm.isCurrentlySelected(shape)){
            ssm.clearCurSelection();
            ssm.setSelected(shape, true);
        }
        return true;
    }

    @Override
    public boolean copyShapesExist() {
        if(scm.getCopyShapes().size() == 0)
            return false;
        return true;
    }

    @Override
    public void rotationInitialize() {
        sm.createDraggingShapes(ssm.getSelection());
        sm.setShapeStates(new stateDraggingShapeRotation());
        sm.createShapeBorder(gc.getDrawingPan().getScale(), 
                ssm.getSelectionCoords(), ssm.getSelection());

        srm.initializeRotation();
    }

    @Override
    public void isMouseInBorder(Point p) {
        byte value = sm.isInBorderShapes(p);
        
        if(value == ShapeObjectBorder.INEDGES){
            gc.setRotationStrategy();
        }else if (value == ShapeObjectBorder.INROTATIONCENTER){
            gc.setRotationCenterStrategy();
        }
    }

    @Override
    public void rotate(Point p) {
        srm.rotate(p);
        stm.setStrategy(new sCollisionNotSelectedStrategy(smi));
        stm.checkForCollision();
    }

    @Override
    public void rotateSetUpdate() {
        gc.setRotationUpdate(srm.getRotatedShapes());
    }

    @Override
    public void updateShapeRotation(long id, int x, int y, double rotation) {
        if(!stm.checkForCollision()){
            stm.translateShape(id, x, y);
            srm.setRotation(id, rotation);
        }
        cleanAll();
        
    }

    @Override
    public void rotationCenterSetUpdate() {
        srm.setRotationCenterUpdate(sm.getShapeBorder());
        int x = gc.getDrawingPan().getTranslationX();
        int y = gc.getDrawingPan().getTranslationY();
        for(ShapeDraggingObject shape : sm.getDraggingShapes()){
            shape.setRotationCenterUpdate(x,y);
        }
    }

    @Override
    public void rotationCenterTranslate(Point start, Point end) {
        srm.setRotationCenter(sm.getShapeBorder(), start, end);
    }

    @Override
    public void rotationSetUpdate() {
        sm.getShapeBorder().setRotationCenterUpdate();
        int x = gc.getDrawingPan().getTranslationX();
        int y = gc.getDrawingPan().getTranslationY();
        for(ShapeDraggingObject shape : sm.getDraggingShapes()){
            shape.setRotationCenterUpdate(x,y);
        }
    }

}
