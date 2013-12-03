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
public class AdapterCommunication implements AdapterCommunicationInterface{

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
}
