package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IWaitingDialog;

public interface IAdapterCommunication {
    
    /**
     * Calls the adapter for an object removal.
     * 
     * @param ids The object ids.
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
     * Cancels the import, deleting the imported model.
     */
    public void cancelImport();
    
    /**
     * Checks for naming conflict, when importing.
     * 
     * @param moduleName The name to be checked.
     * @param server The server where the model will be loaded.
     * @return  True, if a conflict exists, false otherwise.
     */
    public boolean importCheckName(String moduleName, String server);

    /**
     * Calls the adapter to import the pre-loaded model.
     * 
     * @param name The name of the new model
     * @param module_name The name of the module.
     * @param imgName The name of the image representation
     * @param imgDir The directory of the image.
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
    public boolean importKMZ(String name, String module_name,
            String imgName, String imgDir, double x, double y,
            double z, double rotationX, double rotationY, double rotationZ,
            double scale);

    /**
     * Does an undo action.
     */
    public void undo();
    
    /**
     * Does a redo action.
     */
    public void redo();

    /**
     * Looks whether or not an image with the name exists in the 
     * user directory.
     * 
     * @param name The image name.
     * @return True, if it exists, false otherwise.
     */
    public boolean imageExists(String name);

    /**
     * Uploads an image to the Server.
     * 
     * @param imgUrl The image url.
     */
    public void uploadImage(String imgUrl);

    /**
     * Sets all properties of selected objects. If 
     * specific properties should not be set, pass null
     * for the variable.
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
     * @param imgDir The directory of the new image.
     * @param furtherActions further actions
     */
    public void setProperties(ArrayList<Long> ids, ArrayList<String> names,
            ArrayList<Float> coordsX, ArrayList<Float> coordsY,
            ArrayList<Float> coordsZ, ArrayList<Double> rotX,
            ArrayList<Double> rotY, ArrayList<Double> rotZ,
            ArrayList<Double> scale, ArrayList<String> imgName,
            ArrayList<String> imgDir, 
            ArrayList<Object> furtherActions);

    /**
     * Adds the rights component to the ids.
     * 
     * @param ids The ids.
     */
    public void addRightsComponent(ArrayList<Long> ids);

    /**
     * Updates the objects. Used for the rights component
     * 
     * @param ids The object ids.
     */
    public void updateObjects(ArrayList<Long> ids);
    
    /**
     * Removes all objects on the server.
     */
    public void removeAll();

    /**
     * Loads a world from a file.
     * 
     * @param filepath The path to the file to load.
     */
    public void loadWorld(String filepath, IWaitingDialog dialog);

    /**
     * Saves a world from to a file.
     * 
     * @param filepath The path to the file to save.
     */
    public void saveWorld(String filepath, IWaitingDialog dialog);
    
}
