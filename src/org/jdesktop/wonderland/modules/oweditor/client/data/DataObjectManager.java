package org.jdesktop.wonderland.modules.oweditor.client.data;

import java.util.HashMap;

public class DataObjectManager implements DataObjectManagerInterface{
    
    private HashMap<Integer, DataObject> data = null;
    private DataControler dc = null;
    
    DataObjectManager(DataControler d){
        dc = d;
        data = new HashMap<Integer, DataObject>();
    }
    
    
    public void createNewObject(DataObjectInterface dataObject){
        
        //DataObject d = new DataObject(id,  x,  y,  z,  rotation,  scale, width, height);
        int id = dataObject.getID();
        
        if(dataObject instanceof DataObject){
            data.put(id, (DataObject) dataObject);
            
            dc.setGUIUpdate(id);
        }
        
    }
    
    public void updataData(DataObjectInterface dataObject){
        
        int id = dataObject.getID();
        
        if(!data.containsKey(id))
            return;
        
        int x = dataObject.getX();
        int y = dataObject.getY();
        int z = dataObject.getZ();
        double rotation = dataObject.getRotation();
        double scale = dataObject.getScale();
        String name = dataObject.getName();
        
        DataObject d = data.get(id);
        d.setCoordinates(x, y, z);
        d.setRotation(rotation);
        d.setScale(scale);
        d.setName(name);

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

    @Override
    public DataObjectInterface getEmptyDataObject() {
        return new DataObject();
    }

    
    
    
    
    

}
