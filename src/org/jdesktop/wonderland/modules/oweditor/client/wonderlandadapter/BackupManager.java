package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.math.Vector3f;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellCache;
import org.jdesktop.wonderland.common.cell.CellID;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.DataObjectObserver;

class MapEntry{
    public long id = 0;
    public Vector3f coordinates = null; 
}

public class BackupManager {    
    private static final Logger LOGGER =
            Logger.getLogger(DataObjectObserver.class.getName());
    
    private HashMap<Long, BackupObject> backup = null;
    private HashMap<String, MapEntry> copyTranslation = null;
    private SessionManager sm = null;
    
    public BackupManager(SessionManager sm){
        backup = new LinkedHashMap<Long, BackupObject>();
        copyTranslation = new LinkedHashMap<String, MapEntry>();
        this.sm = sm;
    }
    
    public BackupObject getBackup(long id){
        return backup.get(id);
    }
    
    public BackupObject getBackup(String name) {
        long id = copyTranslation.get(name).id;        
        BackupObject object = backup.get(id);
                
        return object;
    }
    
    public Cell getCell(long id){
        return backup.get(id).getCell();
    }
    
    public void addObject(long id){
        CellCache cache = sm.getCellCache();
            
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", sm.getSession());
            return;
        }
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        
        Vector3f rotation = CellInfoReader.getRotation(cell);
        
        float rotationX = rotation.x;
        float rotationY = rotation.y;
        float rotationZ = rotation.z;

        float scale = CellInfoReader.getScale(cell);
        
        BackupObject bobject = new BackupObject(cell, rotationX, rotationY,
                rotationZ, scale);
        
        backup.put(id, bobject);
    }
    
    public void clearBackup(){
        HashMap<Long, BackupObject> new_map = new LinkedHashMap<Long, BackupObject>();

        if(copyTranslation.size() > 0){
            
            LOGGER.log(Level.INFO, "{0}" + "backup entry/entries found which " +
            		"is/are not ready to remove yet. Marking for removal.", copyTranslation.size());
            Iterator<Entry<String, MapEntry>> it = copyTranslation.entrySet().iterator();
        
            while (it.hasNext()) {
                Entry<String, MapEntry> entry = it.next();
                long id = entry.getValue().id;
                
                BackupObject object = backup.get(id);
                object.setDeletion(true);
                new_map.put(id, object);
            }
        }
        backup.clear();
        backup.putAll(new_map);
    }
    
    public void addTranslation(long id, String name, Vector3f coordinates){
        
        MapEntry entry = new MapEntry();
        entry.id = id;
        entry.coordinates = coordinates;
        copyTranslation.put(name, entry);
    }
    
    public Vector3f getTranslation(String name){
        return copyTranslation.get(name).coordinates;
    }
    
    public boolean translationContainsKey(String name){
        return copyTranslation.containsKey(name);
    }
    
    public void removeTranslation(String name){
        Long id = copyTranslation.get(name).id;
        
        BackupObject object = backup.get(id);
            
        if(object != null && object.isForDeletion()){
            LOGGER.log(Level.INFO, "{0}" + "backup entry was marked for removal " +
                    "before. Removing now.", id);
            backup.remove(id);
        }
        copyTranslation.remove(name);
    }

}
