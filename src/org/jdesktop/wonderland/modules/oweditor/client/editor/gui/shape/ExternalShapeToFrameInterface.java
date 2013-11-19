package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public interface ExternalShapeToFrameInterface {
    
    public void drawShapes(Graphics2D g2, AffineTransform at, double scale);

}
