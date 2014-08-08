package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.cell.Cell;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.DataObjectObserver;

/**
 * This class is used for backing up objects, which are later needed
 * for undo/redo, or copy actions. It also stores original ids, which
 * is needed for recovery, when an object is deleted and recreated with
 * undo/redo. This is because OWL sets a new id for objects, even if they
 * are recreated.
 * 
 * @author Patrick
 */
public class BackupManager {    
    private static final Logger LOGGER =
            Logger.getLogger(DataObjectObserver.class.getName());
    
    private HashMap<Long, Cell> backup = null;
    
    //maps the actual id to the original id.
    private HashMap<Long, Long> originalIDs = null;
    
    //last active cell contains the last cell with the original id
    private HashMap<Long, ActiveCell> lastActiveID = null;
    
    //contains names, which are done by copy.
    private ArrayList<Long> whiteList = null;
    
    public BackupManager(SessionManager sm){
        backup = new LinkedHashMap<Long, Cell>();
        originalIDs = new HashMap<Long,Long>();
        lastActiveID= new HashMap<Long, ActiveCell>();
        whiteList = new ArrayList<Long>();
    }
    
    /**
     * Returns a backed up cell.
     * 
     * @param id The id of the cell.
     * @return The cell.
     */
    public Cell getCell(long id){
        return backup.get(id);
    }
    
    /**
     * Adds a cell to the backup.
     * 
     * @param cell The cell to add.
     */
    public void addCell(Cell cell){
        long id = CellInfoReader.getID(cell);
        backup.put(id, cell);
    }
    
    /**
     * Removes a cell from the backup.
     * 
     * @param id The id of the cell.
     */
    public void removeCell(long id){
        
        //copy cells may not be removed from backup, 
        //because they are needed for further copies.
        
        backup.remove(id);
    }
    
    /**
     * Cleans all lists, except copy and backup.
     * Backup only keeps the items with ids in the copy list.
     */
    public void clean(){
        
        HashSet<Long> keys = new HashSet<Long>();
        keys.addAll(backup.keySet());
            
        originalIDs.clear();
        lastActiveID.clear();
        whiteList.clear();
    }
    
    /**
     * Adds a copy id.
     * 
     * @param id The id.
     */
    
    
    /**
     * Searches whether an original id is stored in the
     * backup.
     * 
     * @param originalID The original id.
     * @return True, if this original id is stored, false otherwise.
     */
    public boolean containsOriginalID(long originalID){
        return lastActiveID.containsKey(originalID);
    }
    
    /**
     * Returns the original id to a current id.
     * 
     * @param id The current id.
     * @return The original id.
     */
    public long getOriginalID(long id){
        if(originalIDs.containsKey(id))
            return originalIDs.get(id);
        else
            return id;
    }
    
    /**
     * Returns the current id to an original id.
     * 
     * @param originalID The original id.
     * @return The current id.
     */
    public long getActiveID(long originalID){
        if(!lastActiveID.containsKey(originalID))
            return originalID;
        
        Cell cell = lastActiveID.get(originalID).cell;
        return CellInfoReader.getID(cell);
    }
    
    /**
     * Returns an active cell to an original id.
     *  
     * @param originalID The id.
     * @return The cell, or null, if it was not found.
     */
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
    
    /**
     * Looks whether this id is currently in use.
     * 
     * @param original_id The id.
     * @return True, if it is in use, false otherwise.
     */
    public boolean isActive(long original_id){
        if(!lastActiveID.containsKey(original_id))
            return false;
        return lastActiveID.get(original_id).active;
    }
    
    /**
     * Sets an id to active.
     * 
     * @param original_id The id.
     * @param active True, when active, false when not.
     */
    public void setActive(long original_id, boolean active){
        if(lastActiveID.containsKey(original_id)){
            lastActiveID.get(original_id).active = active;
        }
    }
    
    /**
     * Puts a name to the whitelist.
     * 
     * @param id The cell id.
     */
    public void addCopyWhiteList(long id){
        whiteList.add(id);
    }
    
    /**
     * Searches the whitelist for a name and removes it.
     * 
     * @param id The cell id.
     * @return True, if the name is on the list, false otherwise.
     */
    public boolean isOnWhiteList(long id){
        boolean b= whiteList.contains(id);
        whiteList.remove(id);
        return b;
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
