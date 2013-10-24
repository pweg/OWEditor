package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Shape;

public class stateDraggingShapeRotation implements stateDraggingShape{

    @Override
    public int getX(ShapeDraggingObject shape, double scale) {
        Shape transformed = shape.getTransformedShape();
        
        double x = transformed.getBounds().getCenterX()/scale;

        return (int) Math.round(x - shape.getWidth()/2);
        
    }

    @Override
    public int getY(ShapeDraggingObject shape, double scale) {
        Shape transformed = shape.getTransformedShape();
        
        double y = transformed.getBounds().getCenterY()/scale;
        
        return (int) Math.round(y - shape.getHeight()/2);
    }

}
