package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.awt.Point;
import java.awt.Rectangle;

public interface FrameToInputInterface {
    
    public void repaint();

    public int getTranslationX();

    public int getTranslationY();
    
    /**
     * Returns the scale of the drawing panel.
     * 
     * @return the scale in double
     */
    public double getScale();

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


}
