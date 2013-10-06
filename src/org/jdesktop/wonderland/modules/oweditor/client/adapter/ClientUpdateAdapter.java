package org.jdesktop.wonderland.modules.oweditor.client.adapter;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

public class ClientUpdateAdapter implements GUIObserverInterface{
    
    private AdapterController mc = null;
    
    public ClientUpdateAdapter(AdapterController m){
        mc = m;
    }

    @Override
    public void updateTranslation(long id, int x, int y, int z) {
        mc.getSUA().serverChangeEvent(id, x, y, 0, 0, 1, "");
        
    }

}
