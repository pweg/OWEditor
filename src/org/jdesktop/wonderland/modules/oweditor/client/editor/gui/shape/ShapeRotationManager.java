package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUIController;

public class ShapeRotationManager {
    
    private InternalShapeMediatorInterface smi = null;
    private ArrayList<ShapeObject> rotatingShapes = null;
    
    public ShapeRotationManager(InternalShapeMediatorInterface smi){
        this.smi = smi;
    }
    
    public void initializeRotation(){
        rotatingShapes.clear();
        smi.getSelectedShapes();

    }

}
