package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Rectangle;
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
        Shape original = shape.getTransformedShape();
        try {
            AffineTransform revert_back = at.createInverse();
            Shape transformed = revert_back.createTransformedShape(original);
            Rectangle bounds = transformed.getBounds();
            double x = bounds.getCenterX();

            //Bounds have to be used for width, because shape returns the 
            //width of the original shape.
            return (int) Math.round(x - bounds.getWidth()/2);
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getY(ShapeDraggingObject shape, AffineTransform at) {
        Shape original = shape.getTransformedShape();
        try {
            AffineTransform revert_back = at.createInverse();
            Shape transformed = revert_back.createTransformedShape(original);
            Rectangle bounds = transformed.getBounds();
            double y = bounds.getCenterY();


            //Bounds have to be used for height, because shape returns the 
            //height of the original shape.
            return (int) Math.round(y - bounds.getHeight()/2);
            
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
