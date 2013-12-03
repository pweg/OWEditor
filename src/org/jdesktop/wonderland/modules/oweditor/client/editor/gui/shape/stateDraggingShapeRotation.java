package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

public class stateDraggingShapeRotation implements stateDraggingShape{

    @Override
    public int getX(ShapeDraggingObject shape, AffineTransform at) {
        Shape original = shape.getShape();
        System.out.println(original.getBounds().x);
        
        try {
            AffineTransform revert_back = at.createInverse();
            Shape transformed = revert_back.createTransformedShape(original);
            
            double x = transformed.getBounds().getCenterX();
            System.out.println(x + " xxxx");

            return (int) Math.round(x - shape.getWidth()/2);
        } catch (NoninvertibleTransformException e) {
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
            double y = transformed.getBounds().getCenterY();
        
            return (int) Math.round(y - shape.getHeight()/2);
            
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
