package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

public interface Command {
    
    public void execute(GUIObserverInterface goi) throws Exception;
    
    public void undo(GUIObserverInterface goi) throws Exception;
    
    public void redo(GUIObserverInterface goi) throws Exception;

}
