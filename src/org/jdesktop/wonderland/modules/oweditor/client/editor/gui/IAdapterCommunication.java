package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.util.ArrayList;

public interface IAdapterCommunication {
    
    /**
     * Calls the adapter for an object removal.
     * 
     * @param id The object id.
     */
    public void setObjectRemoval(ArrayList<Long> ids);
    
    /**
     * Calls the adapter for a translation update.
     * 
     * @param ids The object ids making the translation.
     * @param coordinates The coordinates of each object. 
     * The list should organized like the ids, so that the position
     * of the id is the same position in the coordinates.
     */
    public void setTranslationUpdate(ArrayList<Long> ids, ArrayList<Point> coordinates);
    
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
     * @param ids The ids of the objects which should be pasted.
     * @param coordinates The coordinates of each paste object. 
     * The list should organized like the ids, so that the position
     * of the id is the same position in the coordinates.
     */
    public void setPasteUpdate(ArrayList<Long> ids, ArrayList<Point> coordinates);
    
    /**
     * Calls the adapter for a rotation update.
     * 
     * @param ids The ids of the objects to rotate.
     * @param coordinates The coordinates of each object. 
     * The list should organized like the ids, so that the position
     * of the id is the same position in the coordinates.
     * @param rotation The new rotation values of the objects, which should
     * be organized like the coordinates.
     */
    public void setRotationUpdate(ArrayList<Long> ids, ArrayList<Point> coordinates,
            ArrayList<Double> rotation);

    /**
     * Calls the adapter for a scale update.
     *  
     * @param ids The id of the object to scale.
     * @param coordinates The coordinates of each object. 
     * The list should organized like the ids, so that the position
     * of the id is the same position in the coordinates.
     * @param scale The scale of each object, which should be 
     * organized like the coordinates.
     */
    public void setScaleUpdate(ArrayList<Long> ids, ArrayList<Point> coordinates,
            ArrayList<Double> scale);

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
     * Checks for naming conflict, when importing.
     * 
     * @param name The name to be checked.
     * @return  True, if a conflict exists, false otherwise.
     */
    public boolean importCheckName(String name);

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
     * if there was an error.
     */
    public boolean importKMZ(String name, String image_url, double x, double y,
            double z, double rotationX, double rotationY, double rotationZ,
            double scale);

    /**
     * Used during a conflict resolution. Uses an
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

    public void undo();
    
    public void redo();
    
}
