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

    //list of copied shapes. It has to be its own list,
    //in order to save the position of the shapes during the copy
    //action.
    private ArrayList<ShapeObject> copyShapes = null;
    
    private IInternalMediator med = null;
    
    public CopyManager(IInternalMediator med){
        this.med = med;
        copyShapes = new ArrayList<ShapeObject>();
    }
    
    /**
     * Initializes a copy.
     * 
     * @return The list of ids of shapes that are copied.
     */
    public ArrayList<Long> initilaizeCopy() {
        copyShapes.clear();
        
        ArrayList<ShapeObject> shapes = med.getSelectedShapes();
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
        ArrayList<ShapeObject> shapes = new ArrayList<ShapeObject>();
        shapes.addAll(copyShapes);
        
        return shapes;
    }

}
