package org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces;

import java.awt.image.BufferedImage;

/**
 * This interface is used between the data and the adapter package.
 * 
 * @author Patrick
 *
 */
public interface IAdapterObserver {
    
    /**
     * Sets a list of servers.
     * 
     * @param servers The serverlist as strings.
     */
    public void setServerList(String[] servers);

    
    /**
     * Creates an empty data object interface, which can be used
     * to fill with data and then transmitted via updateObject or
     * createObject to the data manager.
     * 
     * @return An empty data object, which has only dummy values.
     */
    public IDataObject createEmptyObject();

    /**
     * Creates a new data object with the given data object.
     * 
     * @param dataObject The data object which needs to be stored 
     * in the data manager.
     */
    public void notifyObjectCreation(IDataObject dataObject);
    
    
    /**
     * Notifies the translation event of an object.
     * 
     * @param id The object id.
     * @param x The new x coordinate of the object.
     * @param y The new y coordinate of the object.
     * @param z The new z coordinate of the object.
     */
    public void notifyTranslation(long id, float x, float y, float z);
    
    /**
     * Notifies the deletion event of an object.
     * 
     * @param id The object id.
     */
    public void notifyRemoval(long id);

    /**
     * Notifies the rotation event of an object.
     * 
     * @param id The object id.
     * @param rotationX The rotation around the x axis of the object.
     * @param rotationY The rotation around the y axis of the object.
     * @param rotationZ The rotation around the z axis of the object.
     */
    public void notifyRotation(long id, double rotationX, double rotationY, 
            double rotationZ);

    /**
     * Notifies the scaling event of an object.
     * 
     * @param id The object id.
     * @param scale The new scale of the object.
     */
    public void notifyScaling(long id, double scale);

    /**
     * Notifies a name change event.
     * 
     * @param id The id of the object.
     * @param name The new name.
     */
    public void notifyNameChange(Long id, String name);
    
    /**
     * Notifies the event, when a representation image is created, or changed.
     * 
     * @param id The id of the object.
     * @param img The new BufferedImage.
     * @param name The name of the new image.
     * @param dir The user directory of the new image.
     */
    public void notifyImageChange(long id, BufferedImage img, String name,
            String dir);

    /**
     * Sets up the name of the users directory.
     * 
     * @param dir The directory where the user stores his
     * image data.
     */
    public void setUserDir(String dir);
    
    /**
     * Updates the image library of the user.
     * 
     * @param img A new image.
     * @param name The name of the image.
     * @param dir The name of the directory
     */
    public void updateImgLib(BufferedImage img, String name, String dir);

    /**
     * Notifies for a rights component creation.
     * 
     * @param id The id.
     */
    public void notifyRightComponentCreation(long id);

    /**
     * Notifies for a rights removal.
     * 
     * @param id The id.
     */
    public void notifyRightComponentRemoval(long id);

    /**
     * Clears all rights of one id.
     * 
     * @param id The id of the object.
     */
    public void clearRights(long id);

    /**
     * Notifies a rights change event.
     * 
     * @param id The object id
     * @param type The rights type
     * @param name The rights name
     * @param owner The ownership of the object.
     * @param addSubObjects The permission to add sub objects.
     * @param changeAbilities The permission to change abilities.
     * @param move The permission to move the object.
     * @param view The permission to view the object.
     * @param isEditable is editable
     * @param isEverybody is everybody
     */
    public void notifyRightsChange(long id, String type, String name,
            boolean owner, boolean addSubObjects, boolean changeAbilities,
            boolean move, boolean view, boolean isEditable, boolean isEverybody);

    /**
     * Removes one right entry.
     * 
     * @param id The object id
     * @param type The rights type
     * @param name The rights name.
     */
    public void notifyRightsRemoval(long id, String type, String name);

}
