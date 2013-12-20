package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.TranslatedObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicToFrameInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicToInputFacadeInterface;

/**
 * This is the interface, which forwards data changes  from the gui package
 * to the graphics package.
 * 
 * @author Patrick
 *
 */
public interface FrameGraphicInterface {


    public GraphicToFrameInterface getFrameInterface();
    
    public GraphicToInputFacadeInterface getGraphicInputInterface();

    public void createShape(TranslatedObjectInterface dataObject);

    public void updateShape(long id, int x, int y, String name);

    public void removeShape(long id);

    public void updateShapeCoordinates(long id, int x, int y);

    public void updateShapeRotation(long id, double rotation);

    public void updateShapeScale(long id, double scale);
}
