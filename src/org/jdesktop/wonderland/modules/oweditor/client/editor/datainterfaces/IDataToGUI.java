package org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IDataObjectObserver;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IEnvironmentObserver;

/**
 * An interface used between the data and gui package.
 * 
 * @author Patrick
 *
 */
public interface IDataToGUI {
    
    /**
     * Returns a data object for the given id.
     * 
     * @param id the id of the object.
     * @return a data object, when the id is stored, otherwise null.
     */
    public IDataObject getObject(long id);
    
    /**
     * Returns a transformed data object for the given id.
     * The transformation is done with the registered transformation
     * manager from the adapter.
     * 
     * @param id The id of the object.
     * @return The transformed object.
     */
    public ITransformedObject getTransformedObject(long id);
    
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
    
    /**
     * Returns the serverlist in a string array.
     * 
     * @return The server list as string array.
     */
    public String[] getServerList();
    
    /**
     * Returns the users image library.
     * 
     * @return An arraylist containing buffered images.
     */
    public ArrayList<IImage> getImgLibrary();
    
    /**
     * Returns the user name.
     * 
     * @return The user name.
     */
    public String getUserName();
    
    /**
     * Returns the users image library directory.
     * 
     * @return The directory.
     */
    public String getUserImgDir();

    /**
     * Registers an observer for the data object manager, which informs
     * the gui on object creation and object changes.
     * 
     * @param domo the observer, which observes the data manager.
     */
    public void registerDataObjectObserver(IDataObjectObserver domo);
    
    /**
     * Registers an observer for the environment manager, which informs 
     * the gui on environmental changes, such as new widths/heights and
     * offsets.
     * 
     * @param en the observer, which observes the environment manager.
     */
    public void registerEnvironmentObserver(IEnvironmentObserver en);
    
    /**
     * Searches for an image in the user data manager.
     * 
     * @param name The name of the image.
     * @param dir The directory of the image.
     * @return Null, if the image does not exist, otherwise the image.
     */
    public BufferedImage getImage(String name, String dir);
    
}
