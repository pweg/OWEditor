package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.TranslatedObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.FrameToShapeInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.InputToShapeInterface;

public interface GraphicToFrameInterface {
    
    public void drawShapes(Graphics2D g2, AffineTransform at);
    
    public void registerFrameInterface(FrameToShapeInterface frameInterface);

    public void registerInputInterface(InputToShapeInterface input);

    public GraphicToFrameInterface getFrameInterface();
    
    public GraphicToInputFacadeInterface getInputInterface();

    public void createShape(TranslatedObjectInterface dataObject);

    public void updateShape(long id, int x, int y, String name);

    public void removeShape(long id);

    public void updateShapeCoordinates(long id, int x, int y);

    public void updateShapeRotation(long id, double rotation);

    public void updateShapeScale(long id, double scale);

}
