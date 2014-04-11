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
 * Is used for creating copy names, which is needed in order for
 * copied cells to be recognized, because OWL does not return an id
 * for a copied cell.
 * 
 * @author Patrick
 */
public class CopyNameManager {
    
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    
    private LinkedHashMap<Long, Integer> copies = null;
    
    private final String separator = "|";
    
    public CopyNameManager(){
        copies = new LinkedHashMap<Long, Integer>();
    }
    
    /**
     * Creates a copy name for the late translation, which 
     * should be changed later.
     * 
     * @param session a Wonderland session.
     * @param id The cell id of the cell to copy.
     * @param name The name of the cell to copy.
     * @return The name for the copied cell.
     */
    public String createCopyName(WonderlandSession session, long id, String name){
        
        String count = "1";
           
        if(copies.containsKey(id)){
            int copy_count = copies.get(id) +1;
            count = String.valueOf(copy_count);
            copies.put(id, (copy_count));
        }else{
            copies.put(id, 1);
        }
        
        name = BUNDLE.getString("CopyName")+ name+"_"+count+separator
                +"ID"+session.getID()+"_"+id;
        return name;
    }
    
    /**
     * Creates a name for undo.
     * 
     * @param session The session.
     * @param id The id.
     * @param name The name.
     * @return  The name of the undo cell.
     */
    public String createUndoName(WonderlandSession session, long id, String name){
        return name + separator+"ID"+ session.getID()+"_"+id;
    }
    
    /**
     * Returns the real name of the cell, which should be used for the copy.
     * @param name The copy name of the cell.
     * @return The real name of the cell.
     */
    public String getRealName(String name){
        
        int index = name.lastIndexOf(separator);
        
        if(index == -1)
            return name;
        
        return name.substring(0,index);
    }
    
    
    
}
