package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import java.awt.image.BufferedImage;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IAdapterObserver;

/**
 * This class is used to forward data updates from the adapter package.
 * 
 * @author Patrick
 *
 */
public class AdapterObserver implements IAdapterObserver{
    
    private DataObjectManager dm = null;
    private EnvironmentManager em = null;
    private UserDataManager um = null;

    /**
     * Creates a new dataUpdate instance.
     * 
     * @param dm a dataObjectManager instance, which is called,
     * when updates are made.
     * @param em a environmentManager instance.
     */
    public AdapterObserver(DataObjectManager dm, EnvironmentManager em,
            UserDataManager um) {
        this.dm = dm;
        this.em = em;
        this.um = um;
    }
    
    @Override
    public void setServerList(String[] servers) {
       em.setServerList(servers);
    }

    @Override
    public IDataObject createEmptyObject() {
        return dm.getEmptyDataObject();
    }

    @Override
    public void notifyObjectCreation(IDataObject dataObject) {
        dm.createNewObject((DataObject) dataObject);
    }

    public void notifyTranslation(long id, float x, float y, float z) {
        dm.updateTranslation(id, x, y, z);
    }

    @Override
    public void notifyRemoval(long id) {
        dm.removeObject(id);
    }

    @Override
    public void notifyRotation(long id, double rotationX, double rotationY, 
            double rotationZ) {
        dm.updateRotation(id,rotationX, rotationY, rotationZ);
    }

    @Override
    public void notifyScaling(long id, double scale) {
        dm.updateScale(id, scale);
    }
    
    @Override
    public void notifyImageChange(long id, BufferedImage img, String name, String path){
        dm.updateImage(id, img, name, path);
    }

    @Override
    public void setUserDir(String dir) {
        um.setUserDir(dir);
    }

    @Override
    public void updateImgLib(BufferedImage img, String name) {
        um.addNewImage(img, name);
    }


}
