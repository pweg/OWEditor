package org.jdesktop.wonderland.modules.oweditor.client.adapter;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.ClientUpdateGUIInterface;

public class ClientUpdateAdapter implements ClientUpdateGUIInterface{
    
    private AdapterController mc = null;
    
    public ClientUpdateAdapter(AdapterController m){
        mc = m;
    }

    @Override
    public void updateTranslation(long id, int x, int y, int z) {
        mc.getSUA().serverChangeEvent(id, x, y, 0, 0, 1, "");
        
    }

}
