package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.util.ArrayList;

public class ShapeCopyManager {

    private ArrayList<ShapeObject> copyShapes = null;
    private ArrayList<ShapeObject> translatedCopyShapes;
    private GUIController gc = null;
    
    public ShapeCopyManager(GUIController gc){
        this.gc = gc;
        copyShapes = new ArrayList<ShapeObject>();
        translatedCopyShapes = new ArrayList<ShapeObject>();
    }



    public void initilaizeCopy() {
        copyShapes.clear();
        copyShapes.addAll(gc.samm.getSelection());
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
