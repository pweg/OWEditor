package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public interface FrameToInputInterface {
    
    public void repaint();
    
    /**
     * Returns the current size of the drawing panel.
     * 
     * @return the current size of the drawing panel.
     */
    public Dimension getPanelSize();

    /**
     * Shows the popup menu at the specified position.
     * 
     * @param shapesSelected boolean, should be true, when shapes are selected.
     * @param copyShapesExist boolean, should be true, when copied shapes are stored.
     * @param x the x position of the popup menu.
     * @param y the y position of the popup menu.
     */
    public void showPopupMenu(boolean shapesSelected, boolean copyShapesExist, int x, int y);

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
     * Changes scale of the drawing panel.
     * 
     * @param scale the new scale.
     */
    public void changeScale(double scale);
    
    /**
     * Is used to update the mouse coordinates.
     * 
     * @param x The x value of the coordinates.
     * @param y The y value of the coordinates.
     */
    public void paintMouseCoords(int x, int y);

    /**
     * Transforms a point back to the original coordinates,
     * meaning undoing global scaling and translation.
     * 
     * @param p The point which needs to be reverted.
     * @return The reverted point when possible, null otherwise.
     */
    public Point revertBack(Point point);


}
