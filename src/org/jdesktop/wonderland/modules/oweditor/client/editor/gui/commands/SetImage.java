package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

/**
 * Image setting command.
 * 
 * @author Patrick
 */
public class SetImage implements Command{
    
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
            this.ids = new ArrayList<Long>();
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
    
    /**
     * Sets the image.
     * 
     * @param goi The gui observer.
     * @param dirs The image directories.
     * @param names The image names.
     * @throws Exception 
     */
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
