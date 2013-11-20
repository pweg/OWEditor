package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.TranslatedObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.FrameToShapeInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.InputToShapeInterface;

public interface ExternalShapeInterface {
    
    public void registerFrameInterface(FrameToShapeInterface frameInterface);

    public void registerInputInterface(InputToShapeInterface input);

    public ExternalShapeToFrameInterface getFrameInterface();
    
    public ExternalShapeToInputFacadeInterface getInputInterface();

    public void createShape(TranslatedObjectInterface dataObject);

    public void updateShape(long id, int x, int y, String name);

    public void removeShape(long id);

    public void updateShapeCoordinates(long id, int x, int y);

    public void updateShapeRotation(long id, double rotation);

}
