package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import java.util.ArrayList;
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

    private ArrayList<Long> pasteUndo = null;
    private ArrayList<Long> pasteRedo = null;
    
    public BackupManager(){
        backup = new LinkedHashMap<Long, BackupObject>();
        stringToID = new LinkedHashMap<String, Long>();

        pasteUndo = new ArrayList<Long>();
        pasteRedo = new ArrayList<Long>();
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
    
    /**
     * Adds a newly generated id of an 
     * object which was newly created by the user.
     * The creation could be through import, copy, etc.
     * 
     * @param id The id to add.
     */
    public void addCreatedID(long id){
        pasteUndo.add(id);
    }

    /**
     * Returns the last object id, which
     * was generated during a creation action.
     * This is used for a undo action.
     * 
     * @return A id in long.
     */
    public long getCreationUndoID(){
        
        if(pasteUndo.size() <= 0)
            return -1;

        long id = pasteUndo.get(pasteUndo.size()-1);
         
        pasteUndo.remove(pasteUndo.size()-1);  
        pasteRedo.add(id);
        return id;
    }
    
    /**
     * Returns the last id for the creation 
     * redo action.
     * 
     * @return A id in long.
     */
    public long getCreationRedoID(){
        
        if(pasteRedo.size() <= 0)
            return -1;

        long id = pasteRedo.get(pasteRedo.size()-1);
         
        pasteRedo.remove(pasteRedo.size()-1);  
        pasteUndo.add(id);
        return id;
        
    }

}
