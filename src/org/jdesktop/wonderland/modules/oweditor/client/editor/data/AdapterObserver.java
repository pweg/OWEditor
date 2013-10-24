package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.AdapterObserverInterface;

/**
 * This class is used to forward data updates from the adapter package.
 * 
 * @author Patrick
 *
 */
public class AdapterObserver implements AdapterObserverInterface{
    
    private DataObjectManager dm = null;

    /**
     * Creates a new dataUpdate instance.
     * 
     * @param dm a dataObjectManager instance, which is called,
     * when updates are made.
     */
    public AdapterObserver(DataObjectManager dm) {
        this.dm = dm;
    }

    @Override
    public DataObjectInterface createEmptyObject() {
        return dm.getEmptyDataObject();
    }

    @Override
    public void notifyObjectChange(DataObjectInterface dataObject) {
        dm.updataData(dataObject);
    }

    @Override
    public void notifyObjectCreation(DataObjectInterface dataObject) {
        dm.createNewObject(dataObject);
    }

    public void notifyTranslation(long id, float x, float y, float z) {
        dm.updateTranslation(id, x, y, z);
    }

    @Override
    public void notifyRemoval(long id) {
        dm.removeObject(id);
    }

    @Override
    public void notifyRotation(long id, float x, float y, float z,
            double rotation) {
        dm.updateRotation(id, x, y, z, rotation);
    }

}
