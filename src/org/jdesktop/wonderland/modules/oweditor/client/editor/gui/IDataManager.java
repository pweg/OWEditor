package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IImage;

/**
 * This is the connection between gui and data package.
 * 
 * @author Patrick
 *
 */
public interface IDataManager {
    
    /**
     * Returns an image from the data package.
     * 
     * @param name The name of the image.
     * @param dir The user directory of the image.
     * @return A bufferedImage, or null if the image was not found.
     */
    public BufferedImage getImage(String name, String dir) ;
    
    /**
     * Returns a list of servers, stored in the data package.
     * 
     * @return A string array containing the servers.
     */
    public String[] getServerList();
    
    /**
     * Returns the image library from the data package.
     * 
     * @return Images in an array list.
     */
    public ArrayList<IImage> getImgLib() ;
    
    /**
     * Returns a data object.
     * 
     * @param id The id of the object.
     * @return The data object, or null, if it is not found.
     */
    public IDataObject getDataObject(long id);
    
    /**
     * Transforms gui coordinates back into the virtual world coordinates.
     * 
     * @param p The coordinates to transform.
     * @return The transformed coordinates.
     */
    public Point2D.Double transformCoordsBack(Point p);

    /**
     * Transforms gui coordinates back into virtual world coordinates 
     * using width and height. 
     * 
     * @param coords The coordinates to transform.
     * @param width The width.
     * @param height The height.
     * @return The transformed points.
     */
    public Point2D.Double transformCoordsBack(Point coords, int width, int height);

    /**
     * Returns the current user name.
     * 
     * @return The username.
     */
    public String getUserName();
    
    /**
     * Checks, whether the user has the rights to move an
     * object or not.
     * 
     * @param id The id of the object.
     * @return True, if it is possible to move, false otherwise.
     */
    public boolean checkRightsMove(long id);

}
