package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

/**
 * Command for setting the objects name.
 * 
 * @author Patrick
 */
public class SetName implements Command{
    
    private ArrayList<Long> ids = null;
    private ArrayList<String> oldNames = null;
    private ArrayList<String> newNames = null;
    
    public SetName(ArrayList<Long> ids, ArrayList<String> oldNames,
            ArrayList<String> newNames){
        this.ids = ids;
        this.oldNames = oldNames;
        this.newNames = newNames;
        
        if(ids == null)
            this.ids = new ArrayList<Long>();
    }

    @Override
    public void execute(GUIObserverInterface goi) throws Exception {
        setName(goi, newNames);
        
    }

    @Override
    public void undo(GUIObserverInterface goi) throws Exception {
        setName(goi, oldNames);
    }

    @Override
    public void redo(GUIObserverInterface goi) throws Exception {
        execute(goi);
    }
    
    /**
     * Sets the name.
     * 
     * @param goi The gui observer.
     * @param names The names to set.
     * @throws Exception 
     */
    private void setName(GUIObserverInterface goi, ArrayList<String> names)            
            throws Exception{
        int failcount = 0;
        
        try{
            for(int i=0;i < ids.size();i++){
                Long id = ids.get(i);
                String name = names.get(i);
                try{
                    goi.notifyNameChange(id, name);
                }catch(Exception e){
                    failcount++;
                }
            }
        }catch(Exception e){
            System.err.println("Scale command: list size dont match");
            throw e;
        }
        
        //the command only failed, when every operation failed!
        if(failcount == ids.size())
            throw new CommandException();
        
    }

}
