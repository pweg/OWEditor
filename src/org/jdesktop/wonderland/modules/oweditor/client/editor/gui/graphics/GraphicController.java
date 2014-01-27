package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.TranslatedObjectInterface;
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

}
