package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

public class Delete implements Command{
    
    ArrayList<Long> ids = null;
    
    public Delete(ArrayList<Long> ids){
        this.ids = ids;

        //null pointer prevention
        if(ids == null)
            this.ids = new ArrayList<Long>();
    }

    @Override
    public void execute(GUIObserverInterface goi) {
        for(Long id : ids)
            goi.notifyRemoval(id);
    }

    @Override
    public void undo(GUIObserverInterface goi) {
        for(Long id : ids)
            goi.undoRemoval(id);
        
    }

    @Override
    public void redo(GUIObserverInterface goi) {
        execute(goi);
    }

}
