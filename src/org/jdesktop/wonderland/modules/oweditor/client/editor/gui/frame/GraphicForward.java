package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.TranslatedObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.AdapterCommunicationInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicToFrameInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicToInputInterface;

/**
 * Implements the frameGraphicInterface, which forwards 
 * data changes from the gui package to the graphic
 * package.
 * 
 * @author Patrick
 *
 */
public class GraphicForward implements GraphicForwardInterface{

    private GraphicToFrameInterface graphic = null;
    
    public GraphicForward(AdapterCommunicationInterface adapter){

    }
    
    @Override
    public GraphicToInputInterface getGraphicInputInterface() {
        return graphic.getInputInterface();
    }

    @Override
    public void createShape(TranslatedObjectInterface dataObject) {
        graphic.createShape(dataObject);
    }

    @Override
    public void updateShape(long id, int x, int y, String name) {
        graphic.updateShapeCoordinates(id, x, y);
        graphic.updateShapeName(id, name);
    }

    @Override
    public void removeShape(long id) {
        graphic.removeShape(id);
    }

    @Override
    public void updateShapeCoordinates(long id, int x, int y) {
        graphic.updateShapeCoordinates(id, x, y);
    }

    @Override
    public void updateShapeRotation(long id, double rotation) {
        graphic.updateShapeRotation(id, rotation);
    }

    @Override
    public void updateShapeScale(long id, double scale) {
        graphic.updateShapeScale(id, scale);
    }
    
    protected void registerGraphicInterface(GraphicToFrameInterface graphic){
        this.graphic = graphic;
    }
    
    protected GraphicToFrameInterface getInterface(){
        return graphic;
    }
}
