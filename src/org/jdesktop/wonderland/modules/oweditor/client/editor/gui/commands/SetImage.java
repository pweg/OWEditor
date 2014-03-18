package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.AdapterCommunication;

public class SetImage implements Command{
    
     private static final Logger LOGGER =
            Logger.getLogger(SetImage.class.getName());
    
    private ArrayList<Long> ids = null;
    private ArrayList<String> oldName = null;
    private ArrayList<String> oldDir = null;
    private ArrayList<String> newName = null;
    private ArrayList<String> newDir = null;
    
    public SetImage(ArrayList<Long> ids, 
            ArrayList<String> oldName, ArrayList<String> oldDir, 
            ArrayList<String> newName, ArrayList<String> newDir){
        this.ids = ids;
        this.oldName = oldName;
        this.oldDir = oldDir;
        this.newName = newName;
        this.newDir = newDir;
        
        if(ids == null)
            ids = new ArrayList<Long>();
    }

    @Override
    public void execute(GUIObserverInterface goi) throws Exception {
        setImage(goi, newDir, newName);        
    }

    @Override
    public void undo(GUIObserverInterface goi) throws Exception {
        setImage(goi, oldDir, oldName);
    }

    @Override
    public void redo(GUIObserverInterface goi) throws Exception {
        execute(goi);
    }
    
    private void setImage(GUIObserverInterface goi,
            ArrayList<String> dirs, ArrayList<String> names)
                    throws Exception{
        int failcount = 0;
        
        try{
            for(int i=0;i < ids.size();i++){
                Long id = ids.get(i);
                    if(dirs != null){
                        String dir = dirs.get(i);
                        String name = names.get(i);
                    try{
                        goi.notifyImageChange(id, dir, name);
                    }catch(Exception e){
                        failcount++;
                    }
                }else
                    goi.notifyImageChange(id, null,null);
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
