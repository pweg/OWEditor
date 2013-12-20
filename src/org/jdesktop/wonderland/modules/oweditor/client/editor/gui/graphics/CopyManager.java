package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.ShapeObject;

public class CopyManager {

    private ArrayList<ShapeObject> copyShapes = null;
    private InternalMediatorInterface smi = null;
    
    public CopyManager(InternalMediatorInterface smi){
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
