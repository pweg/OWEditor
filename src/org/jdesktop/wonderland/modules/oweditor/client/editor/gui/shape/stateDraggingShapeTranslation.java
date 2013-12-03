package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

public class stateDraggingShapeTranslation implements stateDraggingShape{

    @Override
    public int getX(ShapeDraggingObject shape, AffineTransform at) {
        Shape original = shape.getShape();

        try {
            AffineTransform revert_back = at.createInverse();
            Shape transformed = revert_back.createTransformedShape(original);
            return (int) Math.round(transformed.getBounds().x);
        }catch (NoninvertibleTransformException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getY(ShapeDraggingObject shape, AffineTransform at) {
        Shape original = shape.getShape();
        
        try {
            AffineTransform revert_back = at.createInverse();
            Shape transformed = revert_back.createTransformedShape(original);
            return (int) Math.round(transformed.getBounds().y);
        }catch (NoninvertibleTransformException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
