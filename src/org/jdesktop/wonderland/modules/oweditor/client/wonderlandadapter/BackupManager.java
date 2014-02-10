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
    private SessionManager sm = null;
    
    public BackupManager(SessionManager sm){
        backup = new LinkedHashMap<Long, Cell>();
        copyList = new ArrayList<Long>();
        this.sm = sm;
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

}
