package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.DataObjectObserver;

public class BackupManager {    
    private static final Logger LOGGER =
            Logger.getLogger(DataObjectObserver.class.getName());
    
    private HashMap<Long, BackupObject> backup = null;
    private HashMap<String, Long> stringToID = null;
    
    public BackupManager(){
        backup = new LinkedHashMap<Long, BackupObject>();
        stringToID = new LinkedHashMap<String, Long>();
    }
    
    public BackupObject getObject(long id){
        return backup.get(id);
    }

    public BackupObject getObject(String name) {
        long id = stringToID.get(name);
        stringToID.remove(name);
        
        BackupObject object = backup.get(id);
        
        if(object.isForDeletion()){
            LOGGER.log(Level.INFO, id + "backup entry was marked for removal " +
                    "before. Removing now.");
            backup.remove(id);
        }
        
        return object;
    }
    
    public void addObject(ServerObject object){
        double rotationX = object.rotationX;
        double rotationY = object.rotationY;
        double rotationZ = object.rotationZ;

        double scale = object.scale;
        
        long id = object.id;
        
        BackupObject bobject = new BackupObject(object, rotationX, rotationY,
                rotationZ, scale);
        
        backup.put(id, bobject);
    }
    
    public void clearBackup(){
        HashMap<Long, BackupObject> new_map = new LinkedHashMap<Long, BackupObject>();

        if(stringToID.size() > 0){
            
            LOGGER.log(Level.INFO, stringToID.size() + "backup entry/entries found which " +
            		"is/are not ready to remove yet. Marking for removal.");
            Iterator<Entry<String, Long>> it = stringToID.entrySet().iterator();
        
            while (it.hasNext()) {
                Entry<String, Long> entry = it.next();
                long id = entry.getValue();
                
                BackupObject object = backup.get(id);
                object.setDeletion(true);
                new_map.put(id, object);
            }
        }
        
        backup.clear();
        backup.putAll(new_map);
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
