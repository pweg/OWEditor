package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

public class Scale implements Command{

    long id;
    int oldX;
    int oldY;
    double oldScale;
    int newX;
    int newY;
    double newScale;
    
    public Scale(long id, int oldX, int oldY, double oldScale,
            int newX, int newY, double newScale){
        this.id = id;
        this.oldScale = oldScale;
        this.oldX = oldX;
        this.oldY = oldY;
        this.newScale = newScale;
        this.newX = newX;
        this.newY = newY;
    }

    @Override
    public void execute(GUIObserverInterface goi) {
        goi.notifyScaling(id, newX, newY, newScale);
    }

    @Override
    public void undo(GUIObserverInterface goi) {
        goi.notifyScaling(id, oldX, oldY, oldScale);
        
    }

}
