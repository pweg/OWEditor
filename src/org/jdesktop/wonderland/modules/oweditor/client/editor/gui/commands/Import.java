package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

public class Import implements Command{
    
    /*private String name;
    private String image_url;
    private double x, y, z;
    private double rotationX, rotationY, rotationZ;
    private double scale;*/
    
    public Import(){
    }

    @Override
    public void execute(GUIObserverInterface goi) {
        
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
