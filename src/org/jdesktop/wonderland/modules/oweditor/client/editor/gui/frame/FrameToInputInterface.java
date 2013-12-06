package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public interface FrameToInputInterface {
    
    public void repaint();
    
    /**
     * Returns the translation value in x direction.
     * This translation value is used to move the whole 2d graph away
     * from the minus coordinates, to fit in the 0,0 coordinates of the shapes,
     * which do not allow for minus coordinates.
     * 
     * @return the value of the graph translation.
     */
    public int getTranslationX();
    
    /**
     * Returns the translation value in y direction.
     * This translation value is used to move the whole 2d graph away
     * from the minus coordinates, to fit in the 0,0 coordinates of the shapes,
     * which do not allow for minus coordinates.
     * 
     * @return the value of the graph translation.
     */
    public int getTranslationY();
    
    /**
     * Returns the current size of the drawing panel.
     * 
     * @return the current size of the drawing panel.
     */
    public Dimension getPanelSize();
    
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
    
    public void changeMouseCoords(int x, int y);


}
