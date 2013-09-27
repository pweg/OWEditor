package org.jdesktop.wonderland.modules.oweditor.client.adapter;

import org.jdesktop.wonderland.modules.oweditor.client.data.DataUpdateInterface;

public class ServerUpdateAdapter {
    
    private AdapterControler mc = null;
    private DataUpdateInterface di = null;
    
    public ServerUpdateAdapter(AdapterControler m){
        mc = m;
    }
    
    public void serverChangeEvent(int id, int x, int y, int z, int rotation, int scale){
        if(di == null){
            System.out.println("DataInterface is not in the adapter");
            return;
        }
        di.updateObject(id, x, y, z, rotation, scale);
        
    }

    public void setDataUpdateInterface(DataUpdateInterface i) {
        di = i;
        
    }
    
    public void createObject(int id, int x, int y, int z, 
            double rotation, double scale, int width, int height){
        di.createObject(id, x, y, z, rotation, scale, width, height);
    }

}
