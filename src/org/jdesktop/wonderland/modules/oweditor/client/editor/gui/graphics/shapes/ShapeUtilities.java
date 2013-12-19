package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * This class contains some static functions, which can be used
 * for shape transformations.
 * 
 * @author Patrick
 *
 * Scaling part is from JFreeReport : a free Java reporting library
 * found under
 * http://www.java2s.com/Code/Java/2D-Graphics-GUI/ResizesortranslatesaShape.htm
 *
 */
public class ShapeUtilities {
    
    /**
     * Translates a shape over the given distance.
     * 
     * @param shape The shape being translated.
     * @param distance_x The distance on the x axis.
     * @param distance_y The distance on the y axis.
     * @return The translated shape.
     */
    public static final Shape setTranslation(Shape shape, 
            double distance_x, double distance_y) {        
        AffineTransform transform = new AffineTransform();
        transform.translate(distance_x, distance_y);
        return transform.createTransformedShape(shape);
    }
    

    /**
     * Scales the shape, without changing its coordinates.
     * Additional a translation can be made, using the two
     * translation values.
     * 
     * @param shape The shape, which should be scaled.
     * @param scale The scale.
     * @param translationX A translation on the x axis.
     * @param translationY A translation on the y axis.
     * @return The scaled shape.
     */
    public static final Shape scaleShape(Shape shape, double scale, 
            double translationX, double translationY){   
        
        Rectangle2D bounds = shape.getBounds2D();
        AffineTransform af = AffineTransform.getTranslateInstance(0 - bounds.getX(), 
                0 - bounds.getY());
        // apply normalisation translation ...
        Shape s = af.createTransformedShape(shape);

        af = AffineTransform.getScaleInstance(scale, scale);
        // apply scaling ...
        s = af.createTransformedShape(s);

        // now retranslate the shape to its original position ...
        af = AffineTransform.getTranslateInstance(bounds.getX()-translationX, 
                bounds.getY()-translationY);
        return af.createTransformedShape(s);
    }
    
    /**
     * Rotates a given shape.
     * 
     * @param shape The shape to rotate.
     * @param rotation The rotation.
     * @return The rotated shape.
     */
    public static final Shape rotateShape(Shape shape, double rotation){
        AffineTransform transform = new AffineTransform();
        
        transform.rotate(Math.toRadians(rotation), 
                shape.getBounds().getCenterX(), 
                shape.getBounds().getCenterY());
        return transform.createTransformedShape(shape);  
    }
}
