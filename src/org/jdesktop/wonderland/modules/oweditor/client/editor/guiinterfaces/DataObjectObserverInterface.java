package org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.TranslatedObjectInterface;


/**
 * This interface is used as an semi observer for the dataObjectManager.
 * Note that there should only be one observer, because only one 
 * gui needs to be updated. Creating one observer for each data object
 * does seem a little bit too costly.
 * 
 * @author Patrick
 *
 */
public interface DataObjectObserverInterface {

    /**
     * Notifies, that an object has changed.
     * 
     * @param dataObject the object which has been changed.
     */
    public void notifyCreation(TranslatedObjectInterface dataObject);
    
    public void notifyTranslation(long id, int x, int y);
    
    public void notifyRemoval(long id);

    public void notifyChange(long id, int x, int y, String name);

    public void notifyRotation(long id, double rotation);

    public void notifyScaling(long id, double scale);
}
