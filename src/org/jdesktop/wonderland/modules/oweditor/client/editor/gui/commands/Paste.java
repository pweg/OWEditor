package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

public class Paste implements Command{
    
    long id;
    int x;
    int y;
    
    public Paste(long id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(GUIObserverInterface goi) {
        goi.notifyPaste(id, x, y);
    }

    @Override
    public void undo(GUIObserverInterface goi) {
        goi.notifyRemoval(id);
    }
    

}
