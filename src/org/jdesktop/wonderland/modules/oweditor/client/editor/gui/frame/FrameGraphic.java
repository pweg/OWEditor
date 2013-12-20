package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.TranslatedObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.AdapterCommunicationInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicToFrame;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicToFrameInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicToInputFacadeInterface;

/**
 * Implements the frameGraphicInterface, which forwards 
 * data changes from the gui package to the graphic
 * package.
 * 
 * @author Patrick
 *
 */
public class FrameGraphic implements FrameGraphicInterface{

    private GraphicToFrameInterface graphic = null;
    
    public FrameGraphic(AdapterCommunicationInterface adapter){

        graphic = new GraphicToFrame(adapter);
    }
    

    @Override
    public GraphicToFrameInterface getFrameInterface() {
        return graphic.getFrameInterface();
    }

    @Override
    public GraphicToInputFacadeInterface getGraphicInputInterface() {
        return graphic.getInputInterface();
    }

    @Override
    public void createShape(TranslatedObjectInterface dataObject) {
        graphic.createShape(dataObject);
    }

    @Override
    public void updateShape(long id, int x, int y, String name) {
        graphic.updateShape(id, x, y, name);
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
    
    protected GraphicToFrameInterface getInterface(){
        return graphic;
    }
}
