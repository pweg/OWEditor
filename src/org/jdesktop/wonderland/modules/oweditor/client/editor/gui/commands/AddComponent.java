package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

/**
 * A command for adding components.
 * 
 * @author Patrick
 */
public class AddComponent implements Command{
    
    private ArrayList<Long> ids = null;
    private byte component;
    
    public AddComponent(ArrayList<Long> ids, byte component){
        this.ids = ids;
        this.component = component;
    }

    @Override
    public void execute(GUIObserverInterface goi) throws Exception {
        for(long id : ids)
            goi.notifyComponentCreation(id, component);
    }

    @Override
    public void undo(GUIObserverInterface goi) throws Exception {
        for(long id : ids)
            goi.notifyComponentRemoval(id, component);
    }

    @Override
    public void redo(GUIObserverInterface goi) throws Exception {
        execute(goi);
    }

}
