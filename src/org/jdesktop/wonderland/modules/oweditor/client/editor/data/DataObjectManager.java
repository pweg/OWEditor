package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import java.util.HashMap;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;

public class DataObjectManager implements DataObjectManagerGUIInterface{
    
    private HashMap<Long, DataObject> data = null;
    private DataControler dc = null;
    
    DataObjectManager(DataControler d){
        dc = d;
        data = new HashMap<Long, DataObject>();
    }
    
    
    public void createNewObject(DataObjectInterface dataObject){
        
        //DataObject d = new DataObject(id,  x,  y,  z,  rotation,  scale, width, height);
        long id = dataObject.getID();
        
        if(dataObject instanceof DataObject){
            data.put(id, (DataObject) dataObject);
            
            dc.setGUIUpdate(id);
        }
        
    }
    
    public void updataData(DataObjectInterface dataObject){
        
        long id = dataObject.getID();
        
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
    public DataObjectInterface getObject(long id){
        return data.get(id);
    }

    @Override
    public int getZ(long id) {
        DataObject object = data.get(id);
        
        if(object == null)
            return -1;
        
        return object.getZ();
    }

    public DataObjectInterface getEmptyDataObject() {
        return new DataObject();
    }

    
    
    
    
    

}
