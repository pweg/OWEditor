package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public interface IDrawingPanel {

    /**
     * Returns the current size of the drawing panel.
     * 
     * @return the current size of the drawing panel.
     */
    public Dimension getSize();
    
    /**
     * Changes the scale of the drawing panel.
     * 
     * @param scale The new scale.
     */
    public void changeScale(double scale);
    
    /**
     * Returns the current mouse position in the drawing panel.
     * 
     * @return the mouse position as point.
     */
    public Point getMousePosition();
    
    /**
     * Returns the visible rectangle of the drawing panel.
     * 
     * @return the visible rectangle.
     */
    public Rectangle getVisibleRect();

    /**
     * Forwards the scrollRectToVisible() message to the drawing panel.
     * 
     * @param rect a visible rectangle
     */
    public void scrollRectToVisible(Rectangle rect);

    /**
     * Transforms a point back to the original coordinates,
     * meaning undoing global scaling and translation.
     * 
     * @param p The point which needs to be reverted.
     * @return The reverted point when possible, null otherwise.
     */
    public Point transformBack(Point p);

}
