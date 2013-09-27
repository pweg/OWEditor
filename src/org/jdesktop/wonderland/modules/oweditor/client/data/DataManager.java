package org.jdesktop.wonderland.modules.oweditor.client.data;

import java.util.HashMap;

public class DataManager implements DataManagerInterface{
    
    private HashMap<Integer, DataObject> data = null;
    private DataControler dc = null;
    
    DataManager(DataControler d){
        dc = d;
        data = new HashMap<Integer, DataObject>();
    }
    
    public void createNewObject(int id, int x, int y, int z, double rotation, 
            double scale, int width, int height){
        
        DataObject d = new DataObject(id,  x,  y,  z,  rotation,  scale, width, height);
        data.put(id, d);
        
        dc.setGUIUpdate(id);
        
    }
    
    public void updataData(int id, int x, int y, int z, double rotation, 
            double scale){
        
        if(!data.containsKey(id))
            return;
        
        DataObject d = data.get(id);
        d.setCoordinates(x, y, z);
        d.setRotation(rotation);
        d.setScale(scale);

        dc.setGUIUpdate(id);
    }
    
    @Override
    public DataObjectInterface getObject(int id){
        return data.get(id);
    }

    @Override
    public int getZ(int id) {
        DataObject object = data.get(id);
        
        if(object == null)
            return -1;
        
        return object.getZ();
    }

    
    
    
    
    

}
