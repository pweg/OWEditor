package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.TransformedObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.DraggingObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToGraphic;

public class GraphicController implements IGraphicToWindow{


    protected IInternalMediator smi = null;
    protected ShapeManager sm = null;
    protected TransformationManager srm = null;
    protected TranslationManager stm = null;
    protected SelectionManager ssm = null;
    protected CopyManager scm = null;
    
    protected GraphicToInputFacade inputInterface = null;
    protected IGraphicToWindow shapeFacadeInterface = null;

    protected IWindowToGraphic window = null;
    
    public GraphicController(){
        
        smi = new InternalMediator();
        
        sm = new ShapeManager(smi);
        stm = new TranslationManager(smi);
        ssm = new SelectionManager(smi);
        srm = new TransformationManager(smi);
        scm = new CopyManager(smi);
        
        //frameInterface = new ExternalShapeToFrame(sm);
        inputInterface = new GraphicToInputFacade();
        
        registerComponents();
    }
    
    private void registerComponents(){
        
        smi.registerSelectionManager(ssm);
        smi.registerShapeManager(sm);
        smi.registerTranslationManager(stm);
        
        inputInterface.registerCopyManager(scm);
        inputInterface.registerMediator(smi);
        inputInterface.registerSelectionManager(ssm);
        inputInterface.registerShapeManager(sm);
        inputInterface.registerTransformationManager(srm);
        inputInterface.registerTranslationManager(stm);
    }


    @Override
    public void drawShapes(Graphics2D g2, AffineTransform at) {
        sm.drawShapes(g2, at);
    }


    @Override
    public void createShape(TransformedObjectInterface dataObject) {
        sm.setDataUpdate(dataObject);
    }

    @Override
    public void createDraggingRect(int width, int height, int x, int y,
            double rotation, double scale) {
        sm.createDraggingRect(width, height, x, y, rotation, scale);
    }

    @Override
    public void createDraggingCircle(int width, int height, int x, int y,
            double rotation, double scale) {
        sm.createDraggingCircle(width, height, x, y, rotation, scale);
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
    public void updateShapeName(long id, String name) {
        sm.renameShape(id,name);
    }


    @Override
    public void updateShapeRotation(long id, double rotation) {
        srm.setRotation(id, rotation);
        
    }

    @Override
    public void updateShapeScale(long id, double scale) {
        srm.setScale(id, scale);
    }


    @Override
    public void registerWindowInterface(IWindowToGraphic window) {
        this.window = window;
        smi.registerWindowInterface(window);
        inputInterface.registerWindowInterface(window);
    }

    @Override
    public IGraphicToWindow getFrameInterface() {
        return this;
    }

    @Override
    public IGraphicToInput getInputInterface() {
        return inputInterface;
    }

    /*
     * This function can theoretically be used for 
     * all dragging shapes, when making an array of points,
     * but currently there is no need for it.
     */
    @Override
    public Point getDraggingCoords() {
        ArrayList<DraggingObject> shapes = sm.getDraggingShapes();
        int x = Integer.MAX_VALUE;
        int y = Integer.MAX_VALUE;
        
        for(DraggingObject shape : shapes){
            
            int sx = shape.getX();
            int sy = shape.getY();

            x = Math.min(sx, x);
            y = Math.min(sy, y);
        }
        
        sm.clearDraggingShapes();
        
        return new Point(x,y);
    }

}
