package org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces;

import java.util.ArrayList;

/**
 * This interface is used between the adapter and the gui,
 * in order to set updates made in the gui.
 * 
 * @author Patrick
 *
 */
public interface GUIObserverInterface {
    
    /**
     * Makes a translation update for an object. When only 
     * one coordinate changes, all other coordinates have to be
     * set as well.
     * 
     * @param id the object id.
     * @param x the x coordinate, the object is moved to.
     * @param y the y coordinate, the object is moved to.
     */
    public void notifyTranslation(long id, int x, int y);
    
    public void notifyRemoval(long id);
    
    public void notifyCopy(ArrayList<Long> object_ids);
    
    public void notifyPaste(long id, int x, int y);

    /**
     * Notifies a simple rotation, which means a rotation around
     * the y axis. This is the rotation done directly in the 
     * 2d environment.
     * 
     * @param id the object id.
     * @param x the x coordinate, the object is moved to.
     * @param y the y coordinate, the object is moved to.
     * @param rotation the rotation of the object.
     */
    public void notifyRotation(long id, int x, int y, double rotation);

    /**
     * Notifies adapter for object scaling.
     * 
     * @param id the object id.
     * @param x the x coordinate, the object is moved to.
     * @param y the y coordinate, the object is moved to.
     * @param scale the new scale.
     */
    public void notifyScaling(long id, int x, int y, double scale);


    /**
     * Reads a KMZ file and returns the object bounds in an
     * integer array.
     * 
     * @param url The url in string.
     * @return The object bounds in integer format.
     * int[0] should be the xExtend 
     * int[1] should be the zExtend (which is y in ow editor)
     */
    public int[] loadKMZ(String url);

    /**
     * Imports the laded KMZ file and creates a new module for
     * the model.
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
     * @return Return id if a conflict was found for the name,
     * otherwise -1.
     */
    public long importKMZ(String name, String image_url, double x, double y,
            double z, double rotationX, double rotationY, double rotationZ,
            double scale);


    /**
     * Used during a conflict resolution. Copies an
     * existing model.
     * 
     * @param id The id of the model, which will be copied.
     * @param image_url The image of the new object.
     * @param x The x coordinate of the new object.
     * @param y The y coordinate of the new object.
     * @param z The z coordinate of the new object.
     * @param rot_x The x rotation of the new object.
     * @param rot_y The y rotation of the new object.
     * @param rot_z The z rotation of the new object.
     * @param scale The scale of the new object.
     */
    public void notifyCopy(long id, String image_url, double x, double y, double z, double rot_x, double rot_y, double rot_z, double scale);
  
    /**
     * Overwrites a module during conflict resolution.
     * 
     * @param id The id of the model, which will be copied.
     * @param image_url The image of the new object.
     * @param x The x coordinate of the new object.
     * @param y The y coordinate of the new object.
     * @param z The z coordinate of the new object.
     * @param rot_x The x rotation of the new object.
     * @param rot_y The y rotation of the new object.
     * @param rot_z The z rotation of the new object.
     * @param scale The scale of the new object.
     */
    public void notifyOverwrite(long id, String image_url, double x, double y, double z, double rot_x, double rot_y, double rot_z, double scale);

}
