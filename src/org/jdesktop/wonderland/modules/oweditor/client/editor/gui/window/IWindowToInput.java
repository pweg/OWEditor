package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public interface IWindowToInput {
    
    /**
     * Repaints the drawing panel.
     */
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
     * @param x the x position of the popup menu.
     * @param y the y position of the popup menu.
     */
    public void showPopupMenu(int x, int y);

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
     * This is used for dragging the viewport.
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
     * @param point The point which needs to be reverted.
     * @return The reverted point when possible, null otherwise.
     */
    public Point revertBack(Point point);


    /**
     * Is used for some menu items. When nothing is selected they get
     * deactivated. 
     * 
     * @param shapesSelected True means, that at least one shape is currently 
     * selected
     */
    public void selectionChange(boolean shapesSelected);

    /**
     * Is used for the paste menu item. This activates the
     * menu entry, if a copy was made and paste is possible.
     * 
     * @param copyShapesExist True if a copy was invoked and therefore
     * shapes for copy are ready.
     */
    public void copyChange(boolean copyShapesExist);

    /**
     * Called, when the translation operation is finished.
     */
    public void translateFinish();

    /**
     * Sets the to background option of the popup menu
     * visible.
     * 
     * @param b if true, the option to background is shown,
     * if false, the to foreground option is shown.
     */
    public void setToBGVisible(boolean b);

    /**
     * Sets the properties frame visible.
     * 
     * @param b If true, the frame becomes visible,
     * if false it will be hidden.
     */
    public void setPropertiesVisible(boolean b);

    /**
     * Shows/Hides the transformationbar
     * 
     * @param b If true, the bar will be visible,
     * if false it will be hidden.
     */
    public void setTransformBarVisible(boolean b);
    
    /**
     * Clears the text on the bottom toolbar.
     */
    public void clearToolbarText();


}
