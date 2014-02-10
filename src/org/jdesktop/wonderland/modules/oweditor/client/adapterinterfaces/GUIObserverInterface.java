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
     * @throws java.lang.Exception
     */
    public void notifyTranslationXY(long id, int x, int y) throws Exception;
    
    /**
     * Notifies a simple rotation, which means a rotation around
     * the y axis. This is the rotation done directly in the 
     * 2d environment.
     * 
     * @param id the object id.
     * @param x the x coordinate, the object is moved to.
     * @param y the y coordinate, the object is moved to.
     * @param rotation the rotation of the object.
     * @throws java.lang.Exception
     */
    public void notifyRotation(long id, int x, int y, double rotation) throws Exception;

    /**
     * Notifies adapter for object scaling.
     * 
     * @param id the object id.
     * @param x the x coordinate, the object is moved to.
     * @param y the y coordinate, the object is moved to.
     * @param scale the new scale.
     * @throws java.lang.Exception
     */
    public void notifyScaling(long id, int x, int y, double scale) throws Exception;
    /**
     * Deletes an object.
     * 
     * @param id The id of the object.
     * @throws java.lang.Exception
     */
    public void notifyRemoval(long id) throws Exception;
    
    /**
     * Undos object removal.
     * 
     * @param id The id of the deleted object.
     * @throws java.lang.Exception
     */
    public void undoRemoval(long id) throws Exception;
    
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
     * @throws java.lang.Exception
     */
    public void notifyPaste(long id, int x, int y) throws Exception;
    
    /**
     * Undoes creational actions, which generate a new
     * object, like the paste, or import command.
     * @throws java.lang.Exception
     */
    public void undoObjectCreation() throws Exception;

    /**
     * Redoes creational actions, which generate a new
     * object, like the paste, or import command.
     * @throws java.lang.Exception
     */
    public void redoObjectCreation() throws Exception;


    /**
     * Reads a KMZ file and returns the object bounds in an
     * integer array.
     * 
     * @param url The url in string.
     * @return The object bounds in integer format.
     * int[0] should be the xExtend 
     * int[1] should be the zExtend (which is y in ow editor)
     * @throws java.lang.Exception
     */
    public int[] loadKMZ(String url) throws Exception;
    
    /**
     * Checks for module name conflict. Returns true
     * if there is a conflict, false otherwise.
     * 
     * @param moduleName The name, which should be checked.
     * @param server The name of the selected server.
     * @return True, if the name already exists, false otherwise.
     * @throws java.lang.Exception
     */
    public boolean importCheckName(String moduleName, String server) throws Exception;

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
     * @throws java.lang.Exception
     */
    public void importKMZ(String name, String image_url, double x, double y,
            double z, double rotationX, double rotationY, double rotationZ,
            double scale) throws Exception;

    public void cancelImport();

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
