package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.util.ArrayList;

public class CopyManager {

    private ArrayList<ShapeObject> copyShapes = null;
    private InternalShapeMediatorInterface smi = null;
    
    public CopyManager(InternalShapeMediatorInterface smi){
        this.smi = smi;
        copyShapes = new ArrayList<ShapeObject>();
    }
    
    public void initilaizeCopy() {
        copyShapes.clear();
        ArrayList<ShapeObject> shapes = smi.getSelectedShapes();
        
        for(ShapeObject shape : shapes){
            ShapeObject tmp = shape.clone();
            copyShapes.add(tmp);
        }
       
    }
    
    public ArrayList<ShapeObject>getCopyShapes(){
        return copyShapes;
    }

}
