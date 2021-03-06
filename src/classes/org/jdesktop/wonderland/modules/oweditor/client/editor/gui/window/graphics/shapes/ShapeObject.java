package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes;

import java.awt.Paint;

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
    @Override
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
     * @param imgName The image name.
     * @param dir The image directory.
     */
    public abstract void setImage(String imgName, String dir);
    
    /**
     * Sets the color of the shape.
     * 
     * @param color The color
     */
    public abstract void setColor(Paint color);
    
    

}
