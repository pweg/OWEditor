package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.util.ArrayList;

public class ShapeCopyManager {

    private ArrayList<ShapeObject> copyShapes = null;
    private ArrayList<ShapeObject> translatedCopyShapes;
    
    public ShapeCopyManager(){

        copyShapes = new ArrayList<ShapeObject>();
        translatedCopyShapes = new ArrayList<ShapeObject>();
    }
    

    
    /**
     * Saves the current selected shapes for further
     * insertion.
     */
    public void copyShapes(ArrayList<ShapeObject> toCopy){
        copyShapes.clear();
        
        for(ShapeObject shape : toCopy){
            copyShapes.add(shape);
        }
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
