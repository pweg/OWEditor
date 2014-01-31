package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

public class Rotate implements Command{
    
    long id;
    int oldX;
    int oldY;
    double oldRotation;
    int newX;
    int newY;
    double newRotation;
    
    public Rotate(long id, int oldX, int oldY, double oldRotation,
            int newX, int newY, double newRotation){
        this.id = id;
        this.oldRotation = oldRotation;
        this.oldX = oldX;
        this.oldY = oldY;
        this.newRotation = newRotation;
        this.newX = newX;
        this.newY = newY;
    }

    @Override
    public void execute(GUIObserverInterface goi) {
        goi.notifyRotation(id, newX, newY, newRotation);
    }

    @Override
    public void undo(GUIObserverInterface goi) {
        goi.notifyRotation(id, oldX, oldY, oldRotation);
        
    }

}
