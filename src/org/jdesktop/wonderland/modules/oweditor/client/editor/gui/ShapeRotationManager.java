package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.util.ArrayList;

public class ShapeRotationManager {
    
    private GUIController gc = null;
    private ArrayList<ShapeObject> rotatingShapes = null;
    
    ShapeRotationManager(GUIController gc){
        this.gc = gc;
    }
    
    public void initializeRotation(){
        rotatingShapes.clear();
        gc.samm.getSelection();

    }

}
