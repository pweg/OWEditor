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
    public void notifyTranslationXY(long id, int x, int y);
    
    /**
     * Deletes an object.
     * 
     * @param id The id of the object.
     */
    public void notifyRemoval(long id);
    
    /**
     * Undos object removal.
     * 
     * @param id The id of the deleted object.
     */
    public void undoRemoval(long id);
    
    /**
     * Notifies a copy command, which makes a backup
     * of the given ids, in case they are deleted later
     * on.
     * 
     * @param object_ids All ids, which should be backed up.
     */
    public void notifyCopy(ArrayList<Long> object_ids);
    
    /**
     * Notifies a paste order, which copies previously 
     * copied objects.
     * 
     * @param id The id of the object, which should be copied.
     * @param x The x coordinate of the new object.
     * @param y The y coordinate of the new object.
     */
    public void notifyPaste(long id, int x, int y);
    
    /**
     * Undoes creational actions, which generate a new
     * object, like the paste, or import command.
     */
    public void undoObjectCreation();

    /**
     * Redoes creational actions, which generate a new
     * object, like the paste, or import command.
     */
    public void redoObjectCreation();

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
     * Checks for module name conflict. Returns true
     * if there is a conflict, false otherwise.
     * 
     * @param name The name, which should be checked.
     * @return True, if the name already exists, false otherwise.
     */
    public boolean importCheckName(String name);

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
     * @return Return true, if importing was a success,
     * false otherwise.
     */
    public boolean importKMZ(String name, String image_url, double x, double y,
            double z, double rotationX, double rotationY, double rotationZ,
            double scale);


    /**
     * Used during a conflict resolution. Copies an
     * existing module.
     * 
     * @param id The id of the module, which will be copied.
     *
    public void importConflictCopy(long id);
  
    /**
     * Overwrites a module during conflict resolution.
     * 
     * @param id The id of the module, which will be overwritten.
     *
    public void importConflictOverwrite(long id);*/

}
