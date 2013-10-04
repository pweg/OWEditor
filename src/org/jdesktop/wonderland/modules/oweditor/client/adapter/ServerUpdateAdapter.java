package org.jdesktop.wonderland.modules.oweditor.client.adapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataUpdateAdapterInterface;

public class ServerUpdateAdapter {
    
    private AdapterController mc = null;
    private DataUpdateAdapterInterface di = null;
    
    public ServerUpdateAdapter(AdapterController m){
        mc = m;
    }
    
    public void serverChangeEvent(long id, int x, int y, int z, int rotation, int scale,
            String name){
        if(di == null){
            System.out.println("DataInterface is not in the adapter");
            return;
        }
        
        DataObjectInterface object = di.createEmptyObject();
        object.setID(id);
        object.setCoordinates(x, y, z);
        object.setRotation(rotation);
        object.setScale(scale);
        object.setName(name);
        
        di.updateObject(object);
        
    }

    public void setDataUpdateInterface(DataUpdateAdapterInterface i) {
        di = i;
        
    }
    
    public void createObject(long id, int x, int y, int z, 
            double rotation, double scale, int width, int height,
            String name){
        
        DataObjectInterface object = di.createEmptyObject();
        object.setID(id);
        object.setCoordinates(x, y, z);
        object.setRotation(rotation);
        object.setScale(scale);
        object.setWidth(width);
        object.setHeight(height);
        object.setName(name);
        
        if(name != "")
            object.setName(name);

        di.createObject(object);
    }

}
