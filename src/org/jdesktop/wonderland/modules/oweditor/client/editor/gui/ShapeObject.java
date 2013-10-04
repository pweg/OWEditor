package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

/**
 * An abstract shape object, which will be saved in the 
 * shape manager.
 * @author Patrick
 *
 */
public abstract class ShapeObject {
    
    /**
     * Returns the transformed version of the shape from
     * the last repaint.
     * 
     * @return The transformed shape.
     */
    public abstract Shape getTransformedShape();
    
    /**
     * Returns the original version of the shape.
     * 
     * @return The original shape.
     */
    public abstract Shape getShape();
    
    /**
     * Returns the shape id.
     * 
     * @return the id.
     */
    public abstract long getID();

    /**
     * Transforms and then paints the original shape.
     * 
     * @param g: Graphics2D.
     * @param at: the affine transformation, used for transforming the shape.
     * @param scale: the scale, which is needed to resize and wrapp the text size.
     */
    public abstract void paintOriginal(Graphics2D g, AffineTransform at, double scale);

    /**
     * Sets the selection of a shape.
     * 
     * @param select: if true, the shape will have an outline in a different color
     * which is set in the GUISettings, in order to show that the shape is selected.
     * if false, the shape will be drawn with is normal border outline.
     */
    public abstract void setSelected(boolean select);
    
    /**
     * Returns, if the shape is selected.
     * 
     * @return, true when the shape is selected, false otherwise.
     */
    public abstract boolean isSelected();
    
    /**
     * Switches the selection from selected to unselected and
     * vice versa.
     */
    public abstract void switchSelection();
    
    /**
     * Sets the location of the shape.
     * 
     * @param x: the x coordinate.
     * @param y: the y coordinate.
     */
    public abstract void setLocation(int x, int y);

    /**
     * Sets a new location for the shape.
     * 
     * @param distance_x: the distance the shape will move in the x coordinates.
     * @param distance_y: the distance the shape will move in the y coordinates.
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
     * Returns the shapes name.
     * 
     * @return the name of the shape.
     */
    public abstract String getName();
    

}
