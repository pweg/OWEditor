package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

public class Import implements Command{
    
    
    public Import(){
    }

    @Override
    public void execute(GUIObserverInterface goi) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void undo(GUIObserverInterface goi) {
        goi.undoObjectCreation();
    }

    @Override
    public void redo(GUIObserverInterface goi) {
        goi.redoObjectCreation();
    }

}
