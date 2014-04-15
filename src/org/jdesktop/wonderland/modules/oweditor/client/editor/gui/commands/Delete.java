package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import javax.swing.undo.UndoManager;

/**
 * Delete command.
 * 
 * @author Patrick
 */
public class Delete implements Command{
    
    ArrayList<Long> ids = null;
    
    public Delete(ArrayList<Long> ids){
        this.ids = ids;

        //null pointer prevention
        if(ids == null)
            this.ids = new ArrayList<Long>();
    }

    @Override
    public void execute(GUIObserverInterface goi)  throws Exception{
        int failcount = 0;
        
        for(Long id : ids){
            try{
                goi.notifyRemoval(id);
            }catch(Exception e){
                failcount++;
            }
        }
        
        //the command only failed, when every operation failed!
        if(failcount == ids.size())
            throw new CommandException();
    }

    @Override
    public void undo(GUIObserverInterface goi)  throws Exception{
        int failcount = 0;
        
        for(Long id : ids){
            try{
                goi.undoRemoval(id);
            }catch(Exception e){
                failcount++;
            }
        }
        
        //the command only failed, when every operation failed!
        if(failcount == ids.size())
            throw new CommandException();
    }

    @Override
    public void redo(GUIObserverInterface goi)  throws Exception{
        execute(goi);
    }
    

}
