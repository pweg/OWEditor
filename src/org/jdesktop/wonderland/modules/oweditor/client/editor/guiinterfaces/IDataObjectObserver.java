package org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces;

import java.awt.image.BufferedImage;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.ITransformedObject;


/**
 * This interface is used as an semi observer for the dataObjectManager.
 * Note that there should only be one observer, because only one 
 * gui needs to be updated. Creating one observer for each data object
 * does seem a little bit too costly.
 * 
 * @author Patrick
 *
 */
public interface IDataObjectObserver {

    /**
     * Notifies, that an object has changed.
     * 
     * @param dataObject the object which has been changed.
     */
    public void notifyCreation(ITransformedObject dataObject);
    
    /**
     * Notifies a translation.
     * 
     * @param id The object id.
     * @param x The new x coordinate.
     * @param y The new y coordinate.
     */
    public void notifyTranslation(long id, int x, int y);
    
    /**
     * Notifies an object removal.
     * 
     * @param id The object id.
     */
    public void notifyRemoval(long id);

    /**
     * Notifies a rotation change.
     * 
     * @param id The object id.
     * @param rotation The new rotation.
     */
    public void notifyRotation(long id, double rotation);

    /**
     * Notifies for scaling.
     * 
     * @param id The object id.
     * @param scale The new scale.
     */
    public void notifyScaling(long id, double scale);

    /**
     * Notifies an image change.
     * 
     * @param id The object id.
     * @param imgName The image name.
     * @param dir The image dir.
     */
    public void notifyImageChange(long id, String imgName, String dir);

    /**
     * Notifies a name change.
     * 
     * @param id The object id.
     * @param name The new name.
     */
    public void notifyNameChange(long id, String name);

    /**
     * Notifies a rights component creation.
     * 
     * @param id The object id.
     */
    public void notifyRightComponentCreated(long id);
}
