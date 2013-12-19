package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.DraggingObject;

/**
 * This method for retrieving the coordinates used for rotation.
 * It calculates the coordinate out of the center using the 
 * shape dimensions, BUT uses the ORIGINALS width in order to
 * get the right x,y coordinates for the server object. 
 * It reverts back the coordinates to the 
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
    public int getX(DraggingObject shape, AffineTransform at) {
        Shape original = shape.getTransformedShape();
        try {
            AffineTransform revert_back = at.createInverse();
            Shape transformed = revert_back.createTransformedShape(original);
            Rectangle bounds = transformed.getBounds();
            double x = bounds.getCenterX();
            
            //Shapes original width has to be used , because we want to get 
            //the originals coordinates. This may have to be considered, when
            //building the coordinate translator in the adapter package.
            return (int) Math.round(x - shape.getWidth()/2*shape.getScale());
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getY(DraggingObject shape, AffineTransform at) {
        Shape original = shape.getTransformedShape();
        try {
            AffineTransform revert_back = at.createInverse();
            Shape transformed = revert_back.createTransformedShape(original);
            Rectangle bounds = transformed.getBounds();
            double y = bounds.getCenterY();

            //Shapes original height has to be used , because we want to get 
            //the originals coordinates. This may have to be considered, when
            //building the coordinate translator in the adapter package.
            return (int) Math.round(y - shape.getHeight()/2*shape.getScale());
            
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
