package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

/**
 * The import command is only a hollow command, because deleted
 * data will be stored in the backup manager in the adapter part,
 * therefore no needless baggage has to be stored here.
 * 
 * @author Patrick
 *
 */
public class Import implements Command{
  
    private long id = -1;
    
    public Import(){
    }

    @Override
    public void execute(GUIObserverInterface goi)  throws Exception{
        
    }

    @Override
    public void undo(GUIObserverInterface goi)  throws Exception{
        if(id != -1){
            goi.notifyRemoval(id);
        }
    }

    @Override
    public void redo(GUIObserverInterface goi)  throws Exception{
        if(id != -1)
            goi.undoRemoval(id);
    }

    public void setID(long id) {
        this.id = id;
    }

}
