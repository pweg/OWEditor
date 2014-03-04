package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * An abstract shape object, which is used to represent
 * the virtual world objects.
 * 
 * @author Patrick
 *
 */
public abstract class ShapeObject extends SimpleShapeObject{
    

    /**
     * Returns the z coordinate.
     * 
     * @return The z coordinate.
     */
    public abstract double getZ();

    /**
     * Returns the shape id.
     * 
     * @return the id.
     */
    public abstract long getID();

    /**
     * Sets the selection of a shape.
     * 
     * @param select if true, the shape will have an outline in a different color
     * which is set in the GUISettings, in order to show that the shape is selected.
     * if false, the shape will be drawn with is normal border outline.
     */
    public abstract void setSelected(boolean select);
    
    /**
     * Returns, if the shape is selected.
     * 
     * @return true when the shape is selected, false otherwise.
     */
    public abstract boolean isSelected();
    
    /**
     * Switches the selection from selected to unselected and
     * vice versa.
     */
    public abstract void switchSelection();
        
    /**
     * Sets a new name for the shape.
     * 
     * @param name the new name
     */
    public abstract void setName(String name);
    
    /**
     * Paints the name of the object. This is not implemented in the
     * normal paint function, because the names have to be painted
     * later, in order to see them, if another object is blocking the view.
     * 
     * @param g the graphics2d instance.
     * @param at the affine transformation.
     * @param scale
     */
    public abstract void paintName(Graphics2D g, AffineTransform at);
        
    /**
     * Returns the shapes name.
     * 
     * @return the name of the shape.
     */
    public abstract String getName();
    
    /**
     * Returns true, if the shapes name is abbreviated,
     * false otherwise.
     * 
     * @return true, if the name is abbreviated,
     * false otherwise.
     */
    public abstract boolean isNameAbbreviated();
    
    /**
     * Returns the rotation.
     * 
     * @return The rotation.
     */
    public abstract double getRotation();
    
    /**
     * Returns the scale.
     * 
     * @return The scale.
     */
    public abstract double getScale();
    
    /**
     * Copies a given object.
     * 
     * @return A shape object.
     */
    public abstract ShapeObject clone();
    
    /**
     * Sets the rotation.
     * 
     * @param rotation The rotation.
     */
    public abstract void setRotation(double rotation);
    
    /**
     * Sets the scale
     * 
     * @param scale The scale.
     */
    public abstract void setScale(double scale);
    
    /**
     * Sets the dimensions and the position of the shape.
     * 
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param width the width.
     * @param height the height.
     */
    public abstract void set(int x, int y, int width, int height);

    /**
     * Sets the representational image.
     * 
     * @param img The image.
     */
    public abstract void setImage(BufferedImage img);
    
    

}
