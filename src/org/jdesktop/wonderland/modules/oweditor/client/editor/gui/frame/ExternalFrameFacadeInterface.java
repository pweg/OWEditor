package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.awt.Point;
import java.awt.Rectangle;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.MouseAndKeyListener;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape.ExternalShapeFacadeInterface;

public interface ExternalFrameFacadeInterface {
    
    /**
     * Adds a mouse and key listener to the drawing panel
     * 
     * @param mkListener the listener
     */
    public void addMouseListenerToDrawingPan(MouseAndKeyListener mkListener);
    
    /**
     * returns the drawing panel, which contains the 2d graphics, like shapes etc..
     * 
     * @return the drawing panel
     */
    public WindowDrawingPanel getDrawingPan();

    /**
     * registers the external shape facade to the frame controller.
     * 
     * @param esmi the shape facade.
     */
    public void registerShapeInterface(ExternalShapeFacadeInterface esmi);
    
    /**
     * This repaints ONLY the drawing panel, nothing else.
     */
    public void repaint();
    
    public void setVisible(boolean visibility);
    
    /**
     * Returns the scale of the drawing panel.
     * 
     * @return the scale in double
     */
    public double getScale();
    
    public int getTranslationX();
    
    public int getTranslationY();
    
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
     * Changes scale of the drawing panel.
     * 
     * @param scale the new scale.
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

    public void setNewWidth(int width);

    public void setNewHeight(int height);

    public void setNewMinX(int x);

    public void setNewMinY(int y);
    
    public ExternalFrameToShapeInterface getShapeInterface();
    

}
