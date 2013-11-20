package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CopyManager {
    
    private HashMap<Long, BackupObject> backup = null;
    private HashMap<String, Long> stringToID = null;
    
    public CopyManager(){
        backup = new LinkedHashMap<Long, BackupObject>();
        stringToID = new LinkedHashMap<String, Long>();
    }
    
    public BackupObject getObject(long id){
        return backup.get(id);
    }

    public BackupObject getObject(String name) {
        long id = stringToID.get(name);
        stringToID.remove(name);
        
        return backup.get(id);
    }
    
    public void addObject(ServerObject object){
        double rotationX = object.rotationX;
        double rotationY = object.rotationY;
        double rotationZ = object.rotationZ;

        double scaleX = object.scaleX;
        double scaleY = object.scaleY;
        double scaleZ = object.scaleZ;
        
        long id = object.id;
        
        BackupObject bobject = new BackupObject(object, rotationX, rotationY,
                rotationZ, scaleX, scaleY, scaleZ);
        
        backup.put(id, bobject);
    }
    
    public void clearBackup(){
        backup.clear();
    }
    
    public void addTranslation(long id, String name, float x, float y, float z){
        BackupObject object = backup.get(id);
        object.setTranslation(x, y, z);
        stringToID.put(name, id);
    }
    
    public boolean translationContainsKey(String name){
        return stringToID.containsKey(name);
    }

}
