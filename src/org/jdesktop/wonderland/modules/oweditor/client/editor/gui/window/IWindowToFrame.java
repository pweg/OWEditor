package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IImage;

public interface IWindowToFrame {
    
    /**
     * Load a kmz throught the adapter.
     * 
     * @param url The kmz url.
     * @return An integer array containing the following information:
     * int[0]: the width of the kml object.
     * int[1]: the height of the kml object.
     */
    public int[] loadKMZ(String url);
    
    /**
     * Checks, whether the name already exists or not.
     * 
     * @param moduleName The name to be checked.
     * @param serverName The name of the selected server.
     * @return True, if a name conflict exists,
     * false otherwise.
     */
    public boolean importCheckName(String moduleName, String serverName);

    /**
     * Imports the loaded kmz model
     * 
     * @param name The name of the new model
     * @param image_url The url of the image representation
     * @param x The x coordinate of the new model.
     * @param y The y coordinate of the new model.
     * @param z The z coordinate of the new model.
     * @param rotationX The x rotation of the new model.
     * @param rotationY The y rotation of the new model.
     * @param rotationZ The z rotation of the new model.
     * @param scale The scale of the new model.
     * 
     * @return Return true, if creation is possible, false,
     * if the same name already exists.
     */
    public boolean importKMZ(String name, String image_url, double x,
            double y, double z, double rotationX, double rotationY, double rotationZ,
            double scale);
    
    /**
     * Cancels the import.
     */
    public void cancelImport();
    
    /**
     * Creates a dragging object which lets the user pick a location with it.
     * 
     * @param width The shapes width.
     * @param height The shapes height.
     * @param rotation The shapes rotation.
     * @param scale The shapes scale.
     */
    public void chooseLocation(int width, int height, double rotation,
            double scale);

    /**
     * Draws all the shapes.
     * 
     * @param g2 A graphics2d instance.
     * @param at A affine transform instance.
     */
    public void drawShapes(Graphics2D g2, AffineTransform at);

    /**
     * Initiates an undo.
     */
    public void undo();
    
    /**
     * Initiates a redo.
     */
    public void redo();

    /**
     * Returns the serverlist.
     * 
     * @return  A string array containing all server.
     */
    public String[] getServerList();

    /**
     * Searches, if an image with the name exists on the servern.
     * 
     * @param name The name of the image file.
     * @return True, if it exists, false otherwise.
     */
    public boolean imageExists(String name);

    /**
     * Returns the ids of all selected objects.
     * 
     * @return The ids in an array list.
     */
    public ArrayList<Long> getSelectedIDs();

    /**
     * Returns the users image library.
     * 
     * @return Images in an array list.
     */
    public ArrayList<IImage> getImgLib();

    /**
     * Gets an object from the data package.
     * 
     * @param id The id of the object.
     * @return The data object.
     */
    public IDataObject getDataObject(long id);

    /**
     * Uploads a image to the server.
     * 
     * @param imgUrl The url of the image.
     */
    public void uploadImage(String imgUrl);

    /**
     * Sets all properties of selected objects. If 
     * specific properties should not be set, pass null
     * for the variable. 
     * In the current implementation the changes in the lists
     * are all the same, due to the properties frame, but it
     * could be possibly used to change every object in a different
     * way.
     * 
     * @param ids The ids of the objects to change.
     * @param names The new names of the objects.
     * @param coordsX The new x coordinates.
     * @param coordsY The new y coordinates.
     * @param coordsZ The new z coordinates.
     * @param rotX The new x rotation.
     * @param rotY The new y rotation.
     * @param rotZ The new z rotation.
     * @param scale The new scale.
     * @param imgName The new image name.
     */
    public void setProperties(ArrayList<Long> ids, ArrayList<String> names,
            ArrayList<Float> coordsX, ArrayList<Float> coordsY, ArrayList<Float> coordsZ,
            ArrayList<Double> rotX, ArrayList<Double> rotY, ArrayList<Double> rotZ,
            ArrayList<Double> scale, ArrayList<String> imgName);

}
