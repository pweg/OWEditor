package org.jdesktop.wonderland.modules.oweditor.client.adapter;

public class ClientUpdateAdapter implements ClientUpdateInterface{
    
    private AdapterControler mc = null;
    
    public ClientUpdateAdapter(AdapterControler m){
        mc = m;
    }

    @Override
    public void updateTranslation(int id, int x, int y, int z) {
        mc.getSUA().serverChangeEvent(id, x, y, 0, 0, 1, "");
        
    }

}
