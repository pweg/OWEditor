package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

/**
 * This method for retrieving the coordinates is used for rotation.
 * It calculates the coordinate out of the center using the 
 * shape dimensions. It reverts back the coordinates to the 
 * original coordinates. This are the coordinates which are used
 * by the adapter, BUT ARE NOT THE ACTUAL OBJECT COORDINATES.
 * For instance, the wonderland server uses the coordinates from
 * the center of the object. These methods only return the java2d
 * coordinates, which are on the left top side of a shape. 
 * 
 * @author Patrick
 *
 */
public class stateDraggingShapeRotation implements stateDraggingShape{

    @Override
    public int getX(ShapeDraggingObject shape, AffineTransform at) {
        Shape original = shape.getShape();
        try {
            AffineTransform revert_back = at.createInverse();
            Shape transformed = revert_back.createTransformedShape(original);
            double x = transformed.getBounds().getCenterX();

            return (int) Math.round(x - shape.getWidth()/2*shape.getScale());
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
        
            return (int) Math.round(y - shape.getHeight()/2*shape.getScale());
            
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
