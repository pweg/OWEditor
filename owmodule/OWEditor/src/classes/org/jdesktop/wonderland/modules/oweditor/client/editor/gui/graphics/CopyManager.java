package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.ShapeObject;

public class CopyManager {

    private ArrayList<ShapeObject> copyShapes = null;
    private IInternalMediator smi = null;
    
    public CopyManager(IInternalMediator smi){
        this.smi = smi;
        copyShapes = new ArrayList<ShapeObject>();
    }
    
    public ArrayList<Long> initilaizeCopy() {
        copyShapes.clear();
        ArrayList<ShapeObject> shapes = smi.getSelectedShapes();

        ArrayList<Long> ids = new ArrayList<Long>();
        
        for(ShapeObject shape : shapes){
            ShapeObject tmp = shape.clone();
            copyShapes.add(tmp);
            
            ids.add(shape.getID());
        }
        
        return ids;
       
    }
    
    public ArrayList<ShapeObject>getCopyShapes(){
        return copyShapes;
    }

}
