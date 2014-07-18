package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics;

import java.awt.Point;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToGraphic;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.DraggingObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.ShapeObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.TransformationBorder;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.stateTransCoordsOrigSize;

/**
 * Implements the graphictoinput interface
 * 
 * @author Patrick
 *
 */
public class GraphicToInputFacade implements IGraphicToInput{

    private ShapeManager sm = null;
    private CopyManager cm = null;
    private EditorTransformationManager etm = null;
    private SelectionManager ssm = null;
    
    private IWindowToGraphic window = null;
    
    public GraphicToInputFacade(){
    }

    public void registerShapeManager(ShapeManager sm){
        this.sm = sm;
    }
    
    public void registerCopyManager(CopyManager cm){
        this.cm = cm;
    }
    
    public void registerSelectionManager(SelectionManager ssm){
        this.ssm = ssm;
    }
    
    public void registerEditorTransformManager(EditorTransformationManager etm){
        this.etm = etm;
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
        sm.createDraggingShapes(ssm.getSelection());
    }
    
    @Override
    public void draggingTranslate(Point end, Point start){
        etm.translateShape(end, start, new sCollisionNotSelectedStrategy());
    }
    
    @Override
    public void translateFinished() {

        if(!etm.checkForCollision(null)){
            ArrayList<Long> ids = new ArrayList<Long>();
            ArrayList<Point> coords = new ArrayList<Point>();
            
            for(DraggingObject shape : sm.getDraggingShapes()){
                ids.add(shape.getID());
                coords.add(new Point(shape.getX(), shape.getY()));
                
            }
            window.setTranslationUpdate(ids, coords);
        }
        sm.clearDraggingShapes();
    }
    
    @Override
    public Point getDraggingCenter(){
        
        ArrayList<DraggingObject> shapes = sm.getDraggingShapes();
        
        int min_x = Integer.MAX_VALUE;
        int max_x = Integer.MIN_VALUE;
        int min_y = Integer.MAX_VALUE;
        int max_y = Integer.MIN_VALUE;
        
        for(DraggingObject shape : shapes){
            int x = shape.getTransformedX();
            int y = shape.getTransformedY();
            
            int x2 = x + shape.getTransformedWidth();
            int y2 = y + shape.getTransformedHeight();
            
            max_x = Math.max(x2, max_x);
            max_y = Math.max(y2, max_y);
            min_x = Math.min(x, min_x);
            min_y = Math.min(y, min_y);
        }
        
        int center_x = (int) Math.round(min_x + (max_x-min_x)/2);
        int center_y = (int) Math.round(min_y + (max_y-min_y)/2);
        
        return new Point(center_x, center_y);
        
    }
    
    @Override
    public Point copyInitialize() {
        ArrayList<Long> ids = cm.initilaizeCopy();
        
        window.setCopyUpdate(ids);
        return ssm.getSelectionCenter();
    }

    @Override
    public void pasteInitialize() {
        sm.createDraggingShapes(cm.getCopyShapes());
    }

    @Override
    public void translate(Point end, Point start) {
        etm.translateShape(end, start, new sCollisionAllStrategy());
    }

    @Override
    public void pasteFinished() {
        if(!etm.checkForCollision(null)){
            ArrayList<Long> ids = new ArrayList<Long>();
            ArrayList<Point> coords = new ArrayList<Point>();
            
            for(DraggingObject shape : sm.getDraggingShapes()){
                ids.add(shape.getID());
                coords.add(new Point(shape.getX(), shape.getY()));
            }
            window.setPasteUpdate(ids, coords);
        }
        sm.clearDraggingShapes();
    }

    @Override
    public void rotationInitialize() {
        sm.createDraggingShapes(ssm.getSelection());
        sm.setShapeStates(new stateTransCoordsOrigSize());
        
        sm.createShapeBorder(ssm.getSelection(),
                TransformationBorder.MODEONECENTER);
    }
    
    @Override
    public void rotate(Point p) {
        etm.rotate(p);
        //etm.setStrategy();
        etm.checkForCollision(new sCollisionNotSelectedStrategy());
    }
    
    @Override
    public void rotationCenterUpdate() {
        etm.setRotationCenterUpdate(sm.getShapeBorder());
    }

    @Override
    public void rotationCenterTranslate(Point start, Point end) {
        etm.setRotationCenter(sm.getShapeBorder(), start, end);
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
        
        if(etm.checkForCollision(null))
            return;
        
        ArrayList<Long> ids = new ArrayList<Long>();
        ArrayList<Point> coords = new ArrayList<Point>();
        ArrayList<Double> rotation = new ArrayList<Double>();
        
        for(DraggingObject shape : sm.getDraggingShapes()){
            ids.add(shape.getID());
            coords.add(new Point(shape.getX(), shape.getY()));
            rotation.add(shape.getRotation());
        }
        window.setRotationUpdate(ids, coords, rotation);
    }
    
    @Override
    public void scaleInitialize() {
        sm.createDraggingShapes(ssm.getSelection());
        
        sm.createShapeBorder(ssm.getSelection(),
                TransformationBorder.MODENOCENTER);
    }


    @Override
    public void scale(Point p) {
        etm.scale(p);
        //stm.setStrategy();
        etm.checkForCollision(new sCollisionNotSelectedStrategy());
    }

    @Override
    public void scaleUpdate() {
        etm.scaleUpdate();
    }
    
    @Override
    public void scaleFinished(){
        if(etm.checkForCollision(null))
            return;
        
        ArrayList<Long> ids = new ArrayList<Long>();
        ArrayList<Point> coords = new ArrayList<Point>();
        ArrayList<Double> scale = new ArrayList<Double>();
        
        for(DraggingObject shape : sm.getDraggingShapes()){
            ids.add(shape.getID());
            coords.add(new Point(shape.getX(), shape.getY()));
            scale.add(shape.getScale());
        }
        window.setScaleUpdate(ids, coords, scale);
    }

    @Override
    public boolean popupInitialize(Point p) {
            
        ShapeObject shape = sm.getShapeSuroundingPoint(p);
        
        if(shape == null){
            if(ssm.getSelection().isEmpty())
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
    public boolean isMouseInBGObject(Point point) {
        
        ShapeObject shape = sm.getBGShapeSurroundingPoint(point);
        
        if(shape == null)
            return false;
        else{
            ssm.clearCurSelection();
            ssm.setSelected(shape, true);
            return true;
        }
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

    @Override
    public boolean isShapeSelected() {
        if(ssm.getSelection().size() > 0)
            return true;
        return false;
    }

    public void registerWindowInterface(IWindowToGraphic window) {
        this.window  = window;
    }
}
