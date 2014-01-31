package org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces;

/**
 * This interface is used between the data and the adapter package.
 * 
 * @author Patrick
 *
 */
public interface AdapterObserverInterface {

    
    /**
     * Creates an empty data object interface, which can be used
     * to fill with data and then transmitted via updateObject or
     * createObject to the data manager.
     * 
     * @return An empty data object, which has only dummy values.
     */
    public DataObjectInterface createEmptyObject();

    /**
     * Creates a new data object with the given data object.
     * 
     * @param dataObject The data object which needs to be stored 
     * in the data manager.
     */
    public void notifyObjectCreation(DataObjectInterface dataObject);
    
    
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

}
