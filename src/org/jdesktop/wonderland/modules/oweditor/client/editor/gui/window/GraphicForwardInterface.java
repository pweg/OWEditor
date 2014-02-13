package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.image.BufferedImage;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.ITransformedObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.IGraphicToInput;

/**
 * This is the interface, which forwards data changes  from the gui package
 * to the graphics package.
 * 
 * @author Patrick
 *
 */
public interface GraphicForwardInterface {

    
    public IGraphicToInput getGraphicInputInterface();

    public void createShape(ITransformedObject dataObject);

    public void updateShape(long id, int x, int y, String name);

    public void removeShape(long id);

    public void updateShapeCoordinates(long id, int x, int y);

    public void updateShapeRotation(long id, double rotation);

    public void updateShapeScale(long id, double scale);
    
    public void updateImage(long id, BufferedImage img);
}
