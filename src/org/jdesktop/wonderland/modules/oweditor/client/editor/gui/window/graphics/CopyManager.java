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

    //list of copied shapes.
    private ArrayList<Long> copyShapes = null;
    
    private IInternalMediator med = null;
    
    public CopyManager(IInternalMediator med){
        this.med = med;
        copyShapes = new ArrayList<Long>();
    }
    
    /**
     * Initializes a copy.
     * 
     * @return The list of ids of shapes that are copied.
     */
    public ArrayList<Long> initilaizeCopy() {
        copyShapes.clear();
        ArrayList<ShapeObject> shapes = med.getSelectedShapes();

       // ArrayList<Long> ids = new ArrayList<Long>();
        
        for(ShapeObject shape : shapes){
            //ShapeObject tmp = shape.clone();
            //copyShapes.add(tmp);
            
            copyShapes.add(shape.getID());
        }
        return copyShapes;
    }
    
    /**
     * Returns shapes that are copied.
     * 
     * @return A list of shapes.
     */
    public ArrayList<ShapeObject> getCopyShapes(){
        return med.getShapes(copyShapes);
    }

}
