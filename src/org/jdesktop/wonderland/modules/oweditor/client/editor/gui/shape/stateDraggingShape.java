package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.geom.AffineTransform;

public interface stateDraggingShape {
    
    public int getX(ShapeDraggingObject shape, AffineTransform at);
    public int getY(ShapeDraggingObject shape, AffineTransform at);

}
