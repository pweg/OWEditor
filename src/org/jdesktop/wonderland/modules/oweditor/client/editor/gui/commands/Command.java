package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

/**
 * Command interface.
 * 
 * @author Patrick
 */
public interface Command {
    
    /**
     * Executes the command.
     * 
     * @param goi The gui observer.
     * @throws Exception 
     */
    public void execute(GUIObserverInterface goi) throws Exception;
    
        
    /**
     * Undoes the command.
     * 
     * @param goi The gui observer.
     * @throws Exception 
     */
    public void undo(GUIObserverInterface goi) throws Exception;
       
    /**
     * Redoes the command.
     * 
     * @param goi The gui observer.
     * @throws Exception 
     */
    public void redo(GUIObserverInterface goi) throws Exception;

}
