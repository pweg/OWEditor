package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.cell.Cell;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.DataObjectObserver;

public class BackupManager {    
    private static final Logger LOGGER =
            Logger.getLogger(DataObjectObserver.class.getName());
    
    private HashMap<Long, Cell> backup = null;
    private ArrayList<Long> copyList = null;
    
    //maps the actual id to the original id.
    private HashMap<Long, Long> originalIDs = null;
    
    //last active cell contains the last cell with the original id
    private HashMap<Long, ActiveCell> lastActiveID = null;
    
    public BackupManager(SessionManager sm){
        backup = new LinkedHashMap<Long, Cell>();
        copyList = new ArrayList<Long>();
        originalIDs = new HashMap<Long,Long>();
        lastActiveID= new HashMap<Long, ActiveCell>();
    }
    
    public Cell getCell(long id){
        return backup.get(id);
    }
    
    public void addCell(Cell cell){
        long id = CellInfoReader.getID(cell);
        backup.put(id, cell);
    }
    
    public void removeCell(long id){
        
        //copy cells may not be removed from backup, 
        //because they are needed for further copies.
        if(isCopy(id))
            return;
        
        backup.remove(id);
    }
    
    public void clearCopy(){
        copyList.clear();
    }
    
    public void addCopy(long id){
        copyList.add(id);
    }
    
    public boolean isCopy(long id){
        for(long id2 : copyList){
            if(id2 == id)
                return true;
        }
        return false;
    }
    
    
    public boolean containsOriginalID(long originalID){
        if(lastActiveID.containsKey(originalID))
            return true;
        return false;
    }
    
    public long getOriginalID(long id){
        if(originalIDs.containsKey(id))
            return originalIDs.get(id);
        else
            return id;
    }
    
    public long getActiveID(long originalID){
        if(!lastActiveID.containsKey(originalID))
            return originalID;
        
        Cell cell = lastActiveID.get(originalID).cell;
        return CellInfoReader.getID(cell);
        
        
    }
    
    public Cell getActiveCell(long originalID){        
        if(lastActiveID.containsKey(originalID)){
            return lastActiveID.get(originalID).cell;
        }else{
            return backup.get(originalID);
        }
    }
    
    /**
     * Adds a mapping from the new id to an original id.
     * 
     * @param original_id The original id.
     * @param cell The new cell.
     */
    public void addOriginalCell(long original_id, Cell cell){
        
        long last_id = getActiveID(original_id);
        ActiveCell active = new ActiveCell(cell);
        
        lastActiveID.put(original_id, active);
        long id = CellInfoReader.getID(cell);
        
        originalIDs.remove(last_id);
        originalIDs.put(id, original_id);
    }
    
    public boolean isActive(long original_id){
        if(!lastActiveID.containsKey(original_id))
            return false;
        return lastActiveID.get(original_id).active;
    }
    
    public void setActive(long original_id, boolean active){
        if(lastActiveID.containsKey(original_id)){
            lastActiveID.get(original_id).active = active;
        }
    }
    
    class ActiveCell{
        Cell cell = null;
        //active, when the cell does currently exist.
        boolean active = true;
        
        ActiveCell(Cell cell){
            this.cell = cell;
        }
    }

}
