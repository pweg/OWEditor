package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * An abstract shape object, which will be saved in the 
 * shape manager.
 * 
 * @author Patrick
 *
 */
public abstract class ShapeObject extends SimpleShapeObject{

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
    
    public abstract void paintName(Graphics2D g, AffineTransform at, double scale);
        
    /**
     * Returns the shapes name.
     * 
     * @return the name of the shape.
     */
    public abstract String getName();
    
    public abstract double getRotation();
    
    public abstract double getScale();
    
    public abstract ShapeObject clone();
    
    public abstract void setRotation(double rotation);
    
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
    
    

}
