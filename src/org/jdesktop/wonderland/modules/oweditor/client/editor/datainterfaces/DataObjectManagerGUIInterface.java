package org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * An interface used between the data and gui package.
 * 
 * @author Patrick
 *
 */
public interface DataObjectManagerGUIInterface {
    
    /**
     * Returns a data object for the given id.
     * 
     * @param id the id of the object.
     * @return a data object, when the id is stored, otherwise null.
     */
    public DataObjectInterface getObject(long id);
    
    /**
     * Returns the z coordinate to an object, given by the id.
     * 
     * @param id the id of the object.
     * @return the z coordinate of the object.
     */
    public float getZ(long id);
    
    /**
     * Transforms coordinates used by the GUI back to the
     * original coordinates used by the virtual world.
     * 
     * DO ONLY USE FOR POINTS, NOT FOR MODEL COORDINATES.
     * 
     * @param coordinates The coordinates to be transformed back.
     * @return The transformed coordinates.
     */
    public Point2D.Double transformCoordsBack(Point coordinates);
    
    /**
     * Transforms coordinates used by the GUI back to the original 
     * coordinates used by the virtual world.
     * The transformation does incorporate width and height 
     * of the object AND SHOULD THEREFORE BE USED WHEN
     * TRANSFORMING COORDINATES FOR MODELS.
     * 
     * @param coordinates
     * @param width
     * @param height
     * @return
     */
    public Point2D.Double transformCoordsBack(Point coordinates, int width, int height);
    
}
