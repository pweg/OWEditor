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

    public void notifyTranslation(long id, int x, int y, int z) {
        dm.updateTranslation(id, x, y, z);
    }

}
