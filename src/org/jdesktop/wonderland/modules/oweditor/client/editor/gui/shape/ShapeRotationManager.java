package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class ShapeRotationManager {
    
    private InternalShapeMediatorInterface smi = null;
    private ArrayList<ShapeObject> rotatingShapes = null;
    
    public ShapeRotationManager(InternalShapeMediatorInterface smi){
        rotatingShapes = new ArrayList<ShapeObject>();
        this.smi = smi;
    }
    
    public void initializeRotation(){
        rotatingShapes.clear();
        rotatingShapes.addAll(smi.getSelectedShapes());

    }

}
