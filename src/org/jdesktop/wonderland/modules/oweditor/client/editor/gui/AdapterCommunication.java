package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

/**
 * This class is used to forward events from the gui to the 
 * adapter. 
 * 
 * @author Patrick
 *
 */
public class AdapterCommunication implements IAdapterCommunication{

    private GUIObserverInterface goi = null;
    
    public AdapterCommunication(){
    }
    
    /**
     * Registers a GUIObserverInterface instance to this
     * class.
     * 
     * @param goi the observer instance.
     */
    public void registerObserver(GUIObserverInterface goi){
        this.goi = goi;
    }
    
    /**
     * Notifies the Adapter for a removal of an
     * object.
     * 
     * @param id the id of the object, that needs to 
     * be removed.
     */
    @Override
    public void setObjectRemoval(long id){
        goi.notifyRemoval(id);
    }
    
    /**
    * Sets an translation update for the adapter
    * 
    * @param id the id of the object that should be updated.
    * @param x the new x coordinate.
    * @param y the new y coordinate.
    * @param z the new z coordinate.
    */
    @Override
    public void setTranslationUpdate(long id, int x, int y) {
        goi.notifyTranslation(id, x, y);
    }
    
    @Override
    public void setCopyUpdate(ArrayList<Long> object_ids){
        goi.notifyCopy(object_ids);
    }

    @Override
    public void setPasteUpdate(long id, int x, int y) {
        goi.notifyPaste(id, x, y);
    }

    @Override
    public void setRotationUpdate(long id, int x, int y, double rotation) {
        goi.notifyRotation(id, x, y, rotation);
    }

    @Override
    public void setScaleUpdate(long id, int x, int y, double scale) {
        goi.notifyScaling(id, x, y, scale);
    }

    @Override
    public int[] loadKMZ(String url) {
        return goi.loadKMZ(url);
    }

    @Override
    public long importKMZ(String name, String image_url, double x, double y,
            double z, double rotationX, double rotationY, double rotationZ,
            double scale) {
        return goi.importKMZ(name, image_url, x,y,z, rotationX, 
                rotationY, rotationZ, scale);
    }

    @Override
    public void copyKMZ(long id, String image_url, double x, double y,
            double z, double rot_x, double rot_y, double rot_z, double scale) {
        goi.notifyCopy(id, image_url, x, y, z,
                rot_x,rot_y,rot_z,scale);
        
    }

    @Override
    public void overwriteKMZ(long id, String image_url, double x, double y,
            double z, double rot_x, double rot_y, double rot_z, double scale) {
        goi.notifyOverwrite(id, image_url, x, y, z,
                rot_x,rot_y,rot_z,scale);
    }
}
