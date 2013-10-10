package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

public class AdapterCommunication {

    private GUIObserverInterface goi = null;
    
    public AdapterCommunication(){
    }
    
    public void registerObserver(GUIObserverInterface goi){
        this.goi = goi;
    }
    

    public void setObjectRemoval(long id){
        goi.notifyRemoval(id);
    }

    /**
     * Sets an translation update for the adapter
     */
    public void setTranslationUpdate(long id, int x, int y, int z) {
        goi.notifyTranslation(id, x, y, z);
    }
}
