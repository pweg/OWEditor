package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics;

import java.awt.Point;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.AdapterCommunicationInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.DraggingObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.ShapeObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.TransformationBorder;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.stateDraggingShapeRotation;

public class GraphicToInputFacade implements GraphicToInputFacadeInterface{

    protected ShapeManager sm = null;
    protected CopyManager scm = null;
    protected TransformationManager srm = null;
    protected SelectionManager ssm = null;
    protected TranslationManager stm = null;
    
    protected InternalMediatorInterface smi = null;
    
    private AdapterCommunicationInterface adapter = null;
    private ShapeController sc = null;
    
    public GraphicToInputFacade(ShapeController sc, AdapterCommunicationInterface adapter){
        this.adapter = adapter;
        this.sc = sc;
        registerComponents(sc);
    }
    
    private void registerComponents(ShapeController sc){
        sm = sc.sm;
        stm = sc.stm;
        scm = sc.scm;
        srm = sc.srm;
        ssm = sc.ssm;
        smi = sc.smi;
    }

    @Override
    public void translateIntialize(Point p) {
        
        ShapeObject shape = sm.getShapeSuroundingPoint(p);
        
        if(shape == null)
            return;
        
        /* When the shape is not selected, 
        *  all other selections will be removed.
        */
        if(!shape.isSelected()){
            ssm.clearCurSelection();
            ssm.setSelected(shape, true);
        }
        smi.createDraggingShapes(ssm.getSelection());
    }
    
    @Override
    public void translate(int x, int y, Point start){
        stm.translateShape(x, y, start, new sCollisionNotSelectedStrategy(smi));
    }
    
    @Override
    public void translateFinished() {

        if(!stm.checkForCollision()){
            
            for(DraggingObject shape : sm.getDraggingShapes()){
                long id = shape.getID();
                adapter.setTranslationUpdate(id, shape.getX(), shape.getY());
            }
        }
        sm.clearDraggingShapes();
    }
    @Override
    public Point copyInitialize() {
        scm.initilaizeCopy();
        
        ArrayList<Long> ids = new ArrayList<Long>();
        
        ArrayList<ShapeObject> copy = scm.getCopyShapes();
        
        for(ShapeObject shape : copy){
            ids.add(shape.getID());
        }
        
        adapter.setCopyUpdate(ids);
        return sc.frame.revertBack(ssm.getSelectionCenter());
    }

    @Override
    public boolean copyShapesExist() {
        if(scm.getCopyShapes().size() == 0)
            return false;
        return true;
    }

    @Override
    public void pasteInitialize() {
        smi.createDraggingShapes(scm.getCopyShapes());
    }

    @Override
    public void pasteTranslate(int x, int y, Point start) {
        stm.translateShape(x,y,start, new sCollisionAllStrategy());
    }

    @Override
    public void pasteFinished() {
        if(!stm.checkForCollision()){
            
            for(DraggingObject shape : sm.getDraggingShapes()){
                long id = shape.getID();
                adapter.setPasteUpdate(id, shape.getX(), shape.getY());
            }
        }
        sm.clearDraggingShapes();
    }

    @Override
    public void rotationInitialize() {
        sm.createDraggingShapes(ssm.getSelection());
        sm.setShapeStates(new stateDraggingShapeRotation());
        
        sm.createShapeBorder(ssm.getSelection(),
                TransformationBorder.MODEONECENTER);

        srm.initializeTransformation();
    }
    
    @Override
    public void rotate(Point p) {
        srm.rotate(p);
        stm.setStrategy(new sCollisionNotSelectedStrategy(smi));
        stm.checkForCollision();
    }
    
    @Override
    public void rotationCenterUpdate() {
        srm.setRotationCenterUpdate(sm.getShapeBorder());
    }

    @Override
    public void rotationCenterTranslate(Point start, Point end) {
        srm.setRotationCenter(sm.getShapeBorder(), start, end);
    }

    @Override
    public void rotationUpdate() {
        sm.getShapeBorder().setRotationCenterUpdate();
        for(DraggingObject shape : sm.getDraggingShapes()){
            shape.setRotationCenterUpdate();
        }
    }

    @Override
    public void rotateFinished() {
        
        if(stm.checkForCollision())
            return;
        for(DraggingObject shape : srm.getTransformedShapes()){
            long id = shape.getID();
            adapter.setRotationUpdate(id, shape.getX(), 
                    shape.getY(), shape.getRotation());
        }
    }
    
    @Override
    public void scaleInitialize() {
        sm.createDraggingShapes(ssm.getSelection());
        //sm.setShapeStates(new stateDraggingShapeTranslation());
        
        sm.createShapeBorder(ssm.getSelection(),
                TransformationBorder.MODEALLCENTER);

        srm.initializeTransformation();
    }


    @Override
    public void scale(Point p) {
        srm.scale(p);
        stm.setStrategy(new sCollisionNotSelectedStrategy(smi));
        stm.checkForCollision();
    }

    @Override
    public void scaleUpdate() {
        srm.scaleUpdate();
    }
    
    @Override
    public void scaleFinished(){
        if(stm.checkForCollision())
            return;
        for(DraggingObject shape : srm.getTransformedShapes()){
            long id = shape.getID();
            adapter.setScaleUpdate(id, shape.getX(), 
                    shape.getY(), shape.getScale());
        }
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
    public void cleanAll() {
        cleanHelpingShapes();
        sm.removeBorder();
    }

    @Override
    public void cleanHelpingShapes() {
        sm.clearDraggingShapes();
        sm.removeSelectionRect();
    }
    
    @Override
    public void clearCurSelection() {
        ssm.clearCurSelection();
    }

    @Override
    public void deleteCurrentSelection() {
        ssm.deleteCurrentSelection();
    }

    @Override
    public boolean selectionSwitch(Point p, boolean mode) {
        ShapeObject shape = sm.getShapeSuroundingPoint(p);
        
        if(shape == null)
            return false;
        
        ssm.switchSelection(shape, mode);
        return true;
    }

    @Override
    public void selectionRectUpdate(Point start, Point end) {
        ssm.resizeSelectionRect(start, end);
    }
    
    @Override
    public void selectionRectFinished(){
        ssm.selectionRectReleased();         
        sm.removeSelectionRect();
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
    public boolean isMouseInBorder(Point p) {
        byte value = sm.isInBorderShapes(p);
        
        if(value == TransformationBorder.INEDGES){
            return true;
        }
        return false;
    }
    
    @Override
    public boolean isMouseInBorderCenter(Point p){
        byte value = sm.isInBorderShapes(p);
        
        if (value == TransformationBorder.INROTATIONCENTER){
            return true;
        }
        return false;
    }

    @Override
    public String getShapeName(Point p) {

        ShapeObject shape = sm.getShapeSuroundingPoint(p);
        
        if(shape == null || !shape.isNameAbbreviated())
            return null;
        
        return shape.getName();
    }
    
    @Override
    public boolean paintShapeName(Point p, String name) {
        return sm.setNameToolTip(p, name);
    }
    
    @Override
    public boolean removeShapeName(){
        return sm.removeNameTooltip();
    }


}
