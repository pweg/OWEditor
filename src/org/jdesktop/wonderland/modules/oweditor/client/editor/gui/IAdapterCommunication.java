package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.util.ArrayList;

public interface IAdapterCommunication {
    
    /**
     * Calls the adapter for an object removal.
     * 
     * @param id The object id.
     */
    public void setObjectRemoval(long id);
    
    /**
     * Calls the adapter for a translation update.
     * 
     * @param id The object id making the translation.
     * @param x The new x coordinate.
     * @param y The new y coordinate.
     */
    public void setTranslationUpdate(long id, int x, int y);
    
    /**
     * Calls the adapter for a copy update, which means
     * the objects will be backed up.
     * 
     * @param object_ids The objects from which a backup
     * should be made.
     */
    public void setCopyUpdate(ArrayList<Long> object_ids);
    
    /**
     * Calls the adapter for a paste update, which means
     * the given id will be copied to the given 
     * coordinates.
     * 
     * @param id The id of the object which should be copied.
     * @param x The x coordinate of the paste object.
     * @param y The y coordinate of the paste object.
     */
    public void setPasteUpdate(long id, int x, int y);
    
    /**
     * Calls the adapter for a rotation update.
     * 
     * @param id The id of the object to rotate.
     * @param x The new x coordinate of the rotated object.
     * @param y The new y coordinate of the rotated object.
     * @param rotation The new rotation value of the object.
     */
    public void setRotationUpdate(long id, int x, int y, double rotation);

    /**
     * Calls the adapter for a scale update.
     *  
     * @param id The id of the object to scale.
     * @param x The new x coordinate of the scaled object.
     * @param y The new y coordinate of the scaled object.
     * @param scale The new scale of the object.
     */
    public void setScaleUpdate(long id, int x, int y, double scale);

    /**
     * Calls the adapter for pre-loading a kmz model.
     * 
     * @param url The url of the model.
     * @return Returns the bounds of the model
     * int[0] is the x bound
     * int[1] is the y bound.
     */
    public int[] loadKMZ(String url);

    /**
     * Calls the adapter to import the pre-loaded model.
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
     * if the same name already exists
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
    public void copyKMZ(long id, String image_url, double x, double y, double z, double rot_x, double rot_y, double rot_z, double scale);

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
    public void overwriteKMZ(long id, String image_url, double x, double y, double z, double rot_x, double rot_y, double rot_z, double scale);

}
