package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

public class TranslateXY implements Command{
    
    long id;
    int oldX;
    int oldY;
    int newX;
    int newY;
    
    public TranslateXY(long id, int oldX, int oldY,
            int newX, int newY){
        this.id = id;
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
        
    }

    @Override
    public void execute(GUIObserverInterface goi) {
        goi.notifyTranslation(id, newX, newY);
    }

    @Override
    public void undo(GUIObserverInterface goi) {
        goi.notifyTranslation(id, oldX, oldY);
        
    }

}
