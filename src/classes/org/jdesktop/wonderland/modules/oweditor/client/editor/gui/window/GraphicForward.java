package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.ITransformedObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.IAdapterCommunication;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.IGraphicToInput;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.IGraphicToWindow;

/**
 * Implements the frameGraphicInterface, which forwards 
 * data changes from the gui package to the graphic
 * package.
 * 
 * @author Patrick
 *
 */
public class GraphicForward implements IGraphicForward{

    private IGraphicToWindow graphic = null;
    
    public GraphicForward(IAdapterCommunication adapter){

    }
    
    @Override
    public IGraphicToInput getGraphicInputInterface() {
        return graphic.getInputInterface();
    }

    @Override
    public void createShape(ITransformedObject dataObject) {
        graphic.createShape(dataObject);
    }

    @Override
    public void updateShape(long id, int x, int y, double z, String name) {
        graphic.updateShapeCoordinates(id, x, y, z);
        graphic.updateShapeName(id, name);
    }

    @Override
    public void removeShape(long id) {
        graphic.removeShape(id);
    }

    @Override
    public void updateShapeCoordinates(long id, int x, int y, double z) {
        graphic.updateShapeCoordinates(id, x, y, z);
    }

    @Override
    public void updateShapeRotation(long id, double rotation) {
        graphic.updateShapeRotation(id, rotation);
    }

    @Override
    public void updateShapeScale(long id, double scale) {
        graphic.updateShapeScale(id, scale);
    }
    
    protected void registerGraphicInterface(IGraphicToWindow graphic){
        this.graphic = graphic;
    }
    
    protected IGraphicToWindow getInterface(){
        return graphic;
    }
    
    @Override
    public void updateImage(long id, String imgName, String dir) {
        graphic.updateShapeImage(id, imgName, dir);
    }

    @Override
    public void updateName(Long id, String name) {
        graphic.updateShapeName(id, name);
    }
}
