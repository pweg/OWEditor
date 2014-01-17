package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;


/**
 * This method for retrieving the coordinates used for the border creation.
 * It calculates the coordinate out of the center using the 
 * shapes transformed dimensions. 
 * This are the coordinates  used
 * by the adapter, BUT ARE NOT THE ACTUAL OBJECT COORDINATES.
 * For instance, the wonderland server uses the coordinates from
 * the center of the object. These methods only return the java2d
 * coordinates, which are on the left top side of a shape. 
 * 
 * @author Patrick
 *
 */
public class stateDraggingShapeBorderBuild implements stateDraggingShape{

    @Override
    public int getX(DraggingObject shape, AffineTransform at) {
        Shape transformed = shape.getTransformedShape();
        Rectangle bounds = transformed.getBounds();
        double x = bounds.getCenterX();
          
        //Bounds have to be used for width, because shape returns the 
        //width of the original shape.
        return (int) Math.round(x - bounds.getWidth()/2);
    }

    @Override
    public int getY(DraggingObject shape, AffineTransform at) {
        Shape transformed = shape.getTransformedShape();
        Rectangle bounds = transformed.getBounds();
        double y = bounds.getCenterY();

        //Bounds have to be used for height, because shape returns the 
        //height of the original shape.
        return (int) Math.round(y - bounds.getHeight()/2);
            
    }

}
