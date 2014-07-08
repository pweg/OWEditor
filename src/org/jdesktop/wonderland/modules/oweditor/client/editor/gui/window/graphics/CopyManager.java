package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.ShapeObject;

/**
 * The copy manager is used for copy operations.
 * 
 * @author Patrick
 *
 */
public class CopyManager {

    private ArrayList<ShapeObject> copyShapes = null;
    private IInternalMediator smi = null;
    
    public CopyManager(IInternalMediator smi){
        this.smi = smi;
        copyShapes = new ArrayList<ShapeObject>();
    }
    
    /**
     * Initializes a copy.
     * 
     * @return The list of ids of shapes that are copied.
     */
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
    
    /**
     * Returns shapes that are copied.
     * 
     * @return A list of shapes.
     */
    public ArrayList<ShapeObject> getCopyShapes(){
        return copyShapes;
    }

}
