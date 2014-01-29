package org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectInterface;

public interface CoordinateTranslatorInterface {

    /**
     * Transforms the coordinates from virtual world coordinates
     * to GUI coordinates, using width and height.
     * THIS SHOULD BE USED WHEN GETTING COORDINATES FOR A MODEL.
     * 
     * @param x The x coordinate to transform.
     * @param y The y coordinate to transform.
     * @param width The width of the object.
     * @param height The height of the object.
     * @return The transformed integer coordinates in a point.
     */
    public Point transformCoordinatesInt(float x, float y, float width, float height);
    
    /**
     * Transforms coordinates back to virtual world coordinates,
     * using width and height.
     * THIS SHOULD BE USED WHEN GETTING COORDINATES FOR A MODEL.
     * 
     * @param x The x coordinate to transform.
     * @param y The y coordinate to transform.
     * @param width The width of the object.
     * @param height The height of the object.
     * @return The transformed double coordinates in a point.
     */
    public Point2D.Double transformCoordinatesBack(float x, float y, float width, float height);
    
    /**
     * Transforms just the x coordinate back to
     * virtual world coordinates.
     * 
     * @param x The x coordinate.
     * @return The transformed coordinate.
     */
    public double transformXBack(double x);
    
    /**
     * Transforms just the y coordinate back to
     * virtual world coordinates.
     * 
     * @param y The y coordinate.
     * @return The transformed coordinate.
     */
    public double transformYBack(double y);
    
    /**
     * Transforms the width into
     * the size, the GUI uses.
     * 
     * @param width The width.
     * @return The transformed width.
     */
    public int transformWidth(float width);

    /**
     * Transforms the height into
     * the size, the GUI uses.
     * 
     * @param height The height.
     * @return The transformed height.
     */
    public int transformHeight(float height);
    
    /**
     * Transforms the scale to scale which can be used
     * by the GUI.
     * 
     * This may be needed, if the scale is used
     * differently in other worlds.
     * 
     * @param scale The original scale.
     * @return The transformed scale.
     */
    public double getScale(double scale);
    
    /**
     * Reads the rotation from an object, which is needed
     * for the GUI
     * 
     * This has to happen, because the rotation could not be
     * the same in the stored object.
     * 
     * @param object The object which contains the rotation.
     * @return The rotation transformed for the GUI.
     */
    public double getRotation(DataObjectInterface object);

}
