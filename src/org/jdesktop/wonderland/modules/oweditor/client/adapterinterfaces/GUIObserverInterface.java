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
     * Different component types, for adding new components.
     */
    //This component is used for the rights, aka the OWL security component.
    public byte RIGHTSCOMPONENT = 1;
    
    /**
     * Makes a translation update for an object. When only 
     * one coordinate changes, all other coordinates have to be
     * set as well. This method should be called when a graphical
     * translation is made (like the user moves the shapes in the editor).
     * This translation takes editor intern coordinates, which are then
     * transformed by the adapter.
     * 
     * @param id the object id.
     * @param x the x coordinate, the object is moved to.
     * @param y the y coordinate, the object is moved to.
     * @throws java.lang.Exception
     */
    public void notifyTranslationXY(long id, int x, int y) throws Exception;
    
    /**
     * Makes a translation update for an object. Unlike the XY translation, this
     * method uses virtual world coordinates, which WILL NOT BE TRANSFORMED.
     * 
     * @param id the object id.
     * @param x the x coordinate, the object is moved to.
     * @param y the y coordinate, the object is moved to.
     * @param z the z coordinate, the object is moved to.
     * @throws java.lang.Exception
     */
    public void notifyTranslation(Long id, double x, double y, double z) throws Exception;
    
    /**
     * Notifies a simple rotation, which means a rotation around
     * the y axis in Open wonderland.
     * 
     * @param id the object id.
     * @param rot_x the rotation in the x axis of the object.
     * @param rot_y the rotation in the y axis of the object.
     * @param rot_z the rotation in the z axis of the object.
     * @throws java.lang.Exception
     */
    public void notifyRotation(long id, double rot_x, double rot_y, double rot_z) throws Exception;

    /**
     * Notifies adapter for object scaling.
     * 
     * @param id the object id.
     * @param scale the new scale.
     * @throws java.lang.Exception
     */
    public void notifyScaling(long id, double scale) throws Exception;
    
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
     * Notifies a change of the representational image.
     * 
     * @param id The id of the object, where the image should be changed.
     * @param dir The directory of the image.
     * @param name The name of the image.
     * @throws java.lang.Exception
     */
    public void notifyImageChange(long id, String dir, String name) 
        throws Exception;

    /**
     * Notifies a change of an object name.
     * 
     * @param id The id of the object.
     * @param name The new name of the object.
     * @throws Exception 
     */
    public void notifyNameChange(Long id, String name) throws Exception;
    
    /**
     * Undoes creational actions, which generate a new
     * object, like the paste, or import command, if there was 
     * no id given back.
     * @throws java.lang.Exception
     */
    public void undoObjectCreation() throws Exception;

    /**
     * Redoes creational actions, which generate a new
     * object, like the paste, or import command, if there was no
     * id given back.
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
     * Checks if an image file already exists.
     * 
     * @param name The name of the image.
     * @return True, if the image already exists, false otherwise.
     */
    public boolean imageFileExists(String name);

    /**
     * Imports the laded KMZ file and creates a new module for
     * the model.
     * 
     * @param name The name of the new model
     * @param module_name The name of the module.
     * @param imgName The name of the image representation.
     * @param x The x coordinate of the new model.
     * @param y The y coordinate of the new model.
     * @param z The z coordinate of the new model.
     * @param rotationX The x rotation of the new model.
     * @param rotationY The y rotation of the new model.
     * @param rotationZ The z rotation of the new model.
     * @param scale The scale of the new model.
     * 
     * @return Return the id of the new object, or -1 if something failed.
     * @throws java.lang.Exception
     */
    public long importKMZ(String name, String module_name,
            String imgName, String imgDir, double x, double y,
            double z, double rotationX, double rotationY, double rotationZ,
            double scale) throws Exception;

    /**
     * Cancels the import, which removes the uploaded tmp object.
     */
    public void cancelImport();
    
    /**
     * Uploads an image to the server.
     * 
     * @param imageUrl The path of the image.
     * @throws java.lang.Exception
     */
    public void uploadImage(String imageUrl) throws Exception;

    /**
     * Creates a new component for an object.
     * 
     * @param id The object id.
     * @param component The byte code for the component.
     * @throws java.lang.Exception
     */
    public void notifyComponentCreation(long id, byte component) throws Exception;
    
    /**
     * Removes a new component for an object.
     * 
     * @param id The object id.
     * @param component The byte code for the component.
     * @throws java.lang.Exception
     */
    public void notifyComponentRemoval(long id, byte component) throws Exception;

    /**
     * Sets a specific right for a specific id.
     * 
     * @param id The object id.
     * @param oldType The old right type.
     * @param oldName The old right name
     * @param type The right type
     * @param name The right name.
     * @param owner The ownership.
     * @param addSubObjects The permission to add new sub objects.
     * @param changeAbilities The permission to change abilities.
     * @param move The permission to move objects.
     * @param view The permission to view objects.
     * @throws java.lang.Exception
     */
    public void setRight(long id, String oldType, String oldName,
            String type, String name,
            boolean owner, boolean addSubObjects,
            boolean changeAbilities, boolean move, boolean view) throws Exception;

    /**
     * Removes a specific right.
     * 
     * @param id The object id.
     * @param type The type of the right.
     * @param name The name of the right.
     * @throws java.lang.Exception
     */
    public void removeRight(long id, String type, String name) throws Exception;

    /**
     * Updates the object.
     * 
     * @param id 
     */
    public void updateObjects(long id);
    

}
