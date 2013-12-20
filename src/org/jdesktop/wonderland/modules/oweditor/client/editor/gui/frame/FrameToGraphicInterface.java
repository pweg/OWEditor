package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.awt.Point;

public interface FrameToGraphicInterface {
    
    /**
     * Repaints the drawing panel.
     */
    public void repaint();
    
    /**
     * Returns the global scale.
     * 
     * @return The scale.
     */
    public double getScale();
    
    /**
     * Transforms a point back to the original coordinates,
     * meaning undoing global scaling and translation.
     * 
     * @param p The point which needs to be reverted.
     * @return The reverted point when possible, null otherwise.
     */
    public Point revertBack(Point p);

}
