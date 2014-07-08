package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

/**
 * An abstract simplified shape object.
 * 
 * @author Patrick
 *
 */
public abstract class SimpleShapeObject {
    
    /**
     * Returns the original version of the shape.
     * 
     * @return The original shape.
     */
    public abstract Shape getShape();
    
    /**
     * Returns the transformed version of the shape from
     * the last repaint.
     * 
     * @return The transformed shape.
     */
    public abstract Shape getTransformedShape();
    
    /**
     * Transforms and then paints the original shape.
     * 
     * @param g Graphics2D.
     * @param at the affine transformation, used for transforming the shape.
     */
    public abstract void paintOriginal(Graphics2D g, AffineTransform at);
    
    /**
     * Sets the location of the shape.
     * (This function is not implemented for dragging shapes)
     * 
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    public abstract void setLocation(int x, int y);

    /**
     * Sets a new location for the shape.
     * 
     * @param distance_x the distance the shape will move in the x coordinates.
     * @param distance_y the distance the shape will move in the y coordinates.
     */
    public abstract void setTranslation(double distance_x, double distance_y) ;
    
    /**
     * Returns the current x coordinate.
     * 
     * @return x coordinate integer.
     */
    public abstract int getX();

    /**
     * Returns the current y coordinate.
     * 
     * @return y coordinate integer.
     */
    public abstract int getY();

    /**
     * Returns the current width.
     * 
     * @return width integer.
     */
    public abstract int getWidth();

    /**
     * Returns the current height.
     * 
     * @return height integer.
     */
    public abstract int getHeight();

    /**
     * Sets the dimensions and the position of the shape.
     * (This function is not implemented for dragging shapes)
     * 
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param width the width.
     * @param height the height.
     */
    public abstract void set(int x, int y, int width, int height);
    

}
