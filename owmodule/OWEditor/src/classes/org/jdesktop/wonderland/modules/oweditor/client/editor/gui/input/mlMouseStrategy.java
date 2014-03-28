package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Point;

/**
 * A mouse strategy interface, used by the mouse and 
 * key listener.
 * 
 * @author Patrick
 *
 */
public interface mlMouseStrategy {
    
    /**
     * Called when the mouse is pressed.
     * 
     * @param p The mouse point.
     */
    public void mousePressed(Point p);

    /**
     * Called when the mouse is released.
     * 
     * @param p The mouse point.
     */
    public void mouseReleased(Point p);

    /**
     * Called when the mouse is dragged.
     * 
     * @param p The mouse point.
     */
    public void mouseDragged(Point p);

    /**
     * Called when the mouse is moved.
     * 
     * @param p The mouse point.
     */
    public void mouseMoved(Point p);

}
