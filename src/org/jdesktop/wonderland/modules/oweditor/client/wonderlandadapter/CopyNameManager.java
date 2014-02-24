/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import org.jdesktop.wonderland.client.comms.WonderlandSession;

/**
 *
 * @author Patrick
 */
public class CopyNameManager {
    
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    
    private LinkedHashMap<Long, Integer> copies = null;
    
    private String undoString = "_|IDcomMI$$ING|";
    
    public CopyNameManager(){
        copies = new LinkedHashMap<Long, Integer>();
    }
    
    /**
     * Creates a copy name for the late translation, which 
     * should be changed later.
     * @param sm a SessionManager instance.
     * @param id The cell id of the cell to copy.
     * @param name The name of the cell to copy.
     * @return The name for the copied cell.
     */
    public String createCopyName(SessionManager sm, long id, String name){
        WonderlandSession session = sm.getSession();
        
        String count = "1";
           
        if(copies.containsKey(id)){
            int copy_count = copies.get(id) +1;
            count = String.valueOf(copy_count);
            copies.put(id, (copy_count));
        }else{
            copies.put(id, 1);
        }
        
        name = BUNDLE.getString("CopyName")+ name+"_"+count+"ID"+session.getID()+"_"+id;
        return name;
    }
    
    public String createUndoName(String name){
        return name + undoString;
    }
    
    public boolean isUndoName( String name){
        
        if(name.contains(undoString))
            return true;
        else 
            return false;
    }
    
    /**
     * Returns the real name of the cell, which should be used for the copy.
     * @param name The copy name of the cell.
     * @return The real name of the cell.
     */
    public String getRealName(String name){
        
        int index = name.lastIndexOf("_");
        
        if(index == -1)
            return name;
        
        String new_name = name.substring(0,index);
        index = new_name.lastIndexOf("_");
        
        if(index == -1)
            return name;
        
        new_name = new_name.substring(0,index);
        
        return new_name;
    }
    
    
    
}
