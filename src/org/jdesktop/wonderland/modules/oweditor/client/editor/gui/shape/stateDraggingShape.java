package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.geom.AffineTransform;

/**
 * The states are used to determined the coordinates, which 
 * are returned by the dragging shape.
 * 
 * @author Patrick
 *
 */
public interface stateDraggingShape {
    
    /**
     * This function returns the x coordinate of the dragging shape.
     * They are transformed back into the server coordinates, BUT NOT
     * the actual object coordinates, which are used by the server.
     * They are ONLY scaled and translated back into the values
     * the server uses.
     * 
     * @param shape The shape, which will be probed for the coordinates.
     * @param at The last global transform.
     * @return The x coordinate.
     */
    public int getX(ShapeDraggingObject shape, AffineTransform at);
    
    /**
     * This function returns the y coordinate of the dragging shape.
     * They are transformed back into the server coordinates, BUT NOT
     * the actual object coordinates, which are used by the server.
     * They are ONLY scaled and translated back into the values
     * the server uses.
     * 
     * @param shape The shape, which will be probed for the coordinates.
     * @param at The last global transform.
     * @return The y coordinate.
     */
    public int getY(ShapeDraggingObject shape, AffineTransform at);

}
