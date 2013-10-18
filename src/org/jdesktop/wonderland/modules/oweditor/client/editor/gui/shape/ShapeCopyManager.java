package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUIController;

public class ShapeCopyManager {

    private ArrayList<ShapeObject> copyShapes = null;
    private ArrayList<ShapeObject> translatedCopyShapes;
    private InternalShapeMediatorInterface smi = null;
    
    public ShapeCopyManager(InternalShapeMediatorInterface smi){
        this.smi = smi;
        copyShapes = new ArrayList<ShapeObject>();
        translatedCopyShapes = new ArrayList<ShapeObject>();
    }



    public void initilaizeCopy() {
        copyShapes.clear();
        copyShapes.addAll(smi.getSelectedShapes());
    }
    
    public ArrayList<ShapeObject>getCopyShapes(){
        return copyShapes;
    }
    
    public ArrayList<ShapeObject>getTranslatedShapes(){
        return translatedCopyShapes;
    }



    public void setTranslatedShapes(ArrayList<ShapeObject> shapes) {
        translatedCopyShapes.clear();
        translatedCopyShapes.addAll(shapes);
    }

    
    

}
