package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

public interface IToolTip {
    
    /**
     * Returns the text, which is displayed in the tooltip.
     * 
     * @return A string containing the text.
     */
    public String getText();

    /**
     * Paints the tooltip.
     * 
     * @param g The graphics2d instance.
     * @param at The affine transformation, which is used to draw the components.
     */
    public void paint(Graphics2D g, AffineTransform at);

    /**
     * Sets the position of the tooltip.
     * 
     * @param p The new position as point.
     */
    public void setCoordinates(Point p);

    /**
     * Sets a new position and a new text.
     * 
     * @param coordinates The new coordinates as point.
     * @param text The new text as string.
     */
    public void set(Point coordinates, String text);
    
}
