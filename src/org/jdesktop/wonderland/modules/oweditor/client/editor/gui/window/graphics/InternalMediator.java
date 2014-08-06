package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToGraphic;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.DraggingObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.ITransformationBorder;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.ShapeObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.SimpleShapeObject;

/**
 * The internal shape mediator, which is used for communication between
 * the classes in this package.
 * 
 * @author Patrick
 */
public class InternalMediator implements IInternalMediator{
    
    private ShapeManager sm = null;
    private SelectionManager ssm = null;
    private IWindowToGraphic window = null;
    
    public InternalMediator() {
    }

    @Override
    public void registerShapeManager(ShapeManager sm) {
        this.sm = sm;
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
    public void repaint() {
        window.repaint();
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
    public void setObjectRemoval(ArrayList<Long> ids) {
        window.setObjectRemoval(ids);
    }

    @Override
    public void clearCurSelection() {
        ssm.clearCurSelection();
    }

    @Override
    public ArrayList<DraggingObject> getDraggingShapes() {
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
    public ArrayList<ShapeObject> getShapes(ArrayList<Long> ids) {
        ArrayList<ShapeObject> list = new ArrayList<ShapeObject>();
        
        if(ids != null){
            for(Long id : ids){
                ShapeObject shape = sm.getShape(id);
                if(shape != null)
                    list.add(shape);
            }
        }
        
        return list;
    }

    @Override
    public ITransformationBorder getShapeBorder() {
        return sm.getShapeBorder();
    }

    @Override
    public void registerWindowInterface(
            IWindowToGraphic frameInterface) {
        this.window = frameInterface;
    }

    @Override
    public BufferedImage getImage(String name, String dir) {
        return window.getImage(name, dir);
    }

    @Override
    public void removeShape(long id) {
        sm.removeShape(id);
    }

    @Override
    public void addShape(ShapeObject shape) {
        sm.addShape(shape);
    }

    @Override
    public void readdShape(ShapeObject shape) {
        sm.readdShape(shape);
    }
}
