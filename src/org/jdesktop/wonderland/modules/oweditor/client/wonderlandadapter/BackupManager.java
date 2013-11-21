package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.math.Vector3f;
import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.cell.Cell;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.DataObjectObserver;

public class BackupManager {    
    private static final Logger LOGGER =
            Logger.getLogger(DataObjectObserver.class.getName());
    
    private HashMap<Long, BackupObject> backup = null;
    private HashMap<String, Long> stringToID = null;
    private LinkedHashMap<String, Point> copyTranslation = null;
    
    public BackupManager(){
        backup = new LinkedHashMap<Long, BackupObject>();
        stringToID = new LinkedHashMap<String, Long>();
        copyTranslation = new LinkedHashMap<String, Point>();
    }
    
    public BackupObject getCell(long id){
        return backup.get(id);
    }
    
    public BackupObject getCell(String name) {
        long id = stringToID.get(name);        
        BackupObject object = backup.get(id);
                
        return object;
    }
    
    public void addObject(Cell cell){
        
        Vector3f rotation = CellInfoReader.getRotation(cell);
        
        float rotationX = rotation.x;
        float rotationY = rotation.y;
        float rotationZ = rotation.z;

        float scale = CellInfoReader.getScale(cell);
        
        long id = CellInfoReader.getID(cell);
        
        BackupObject bobject = new BackupObject(cell, rotationX, rotationY,
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
    
    public void addTranslation(long id, String name, int x, int y){
        stringToID.put(name, id);
        
        copyTranslation.put(name, new Point(x,y));
    }
    
    public Point getTranslation(String name){
        return copyTranslation.get(name);
    }
    
    public boolean translationContainsKey(String name){
        return stringToID.containsKey(name);
    }
    
    public void removeTranslation(String name){
        Long id = stringToID.get(name);
        
        
        BackupObject object = backup.get(id);
            
        if(object != null && object.isForDeletion()){
            LOGGER.log(Level.INFO, "{0}" + "backup entry was marked for removal " +
                    "before. Removing now.", id);
            backup.remove(id);
        }
        stringToID.remove(name);
        copyTranslation.remove(name);
    }

}
