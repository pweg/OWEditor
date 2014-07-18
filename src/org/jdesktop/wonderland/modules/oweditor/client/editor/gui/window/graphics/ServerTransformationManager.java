package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.ShapeObject;

/**
 * This class is used to transform normal shapes, 
 * usually when a server update is due.
 * 
 * @author Patrick
 *
 */

public class ServerTransformationManager {
    
    private IInternalMediator med = null;
    
    public ServerTransformationManager(IInternalMediator med){
        this.med = med;
    }
    
    /**
     * Translates a shape with the id to the specified
     * coordinates. This is called, when an update
     * comes from the data package
     * 
     * @param id the id of the shape to translate.
     * @param x the new x coordinate.
     * @param y the new y coordinate.
     */
    public void translateShape(long id, int x, int y){
        ShapeObject shape = med.getShape(id);
        shape.setLocation(x, y);
    }

    /**
     * Sets the rotation of one shape.
     * 
     * @param id The id of the shape.
     * @param rotation The rotation angle.
     */
    public void setRotation(long id, double rotation) {
        ShapeObject shape = med.getShape(id);
        shape.setRotation(rotation);
    }

    /**
     * Sets the scale of a specific shape.
     * 
     * @param id The id of the shape.
     * @param scale The new scale.
     */
    public void setScale(long id, double scale) {
        ShapeObject shape = med.getShape(id);
        shape.setScale(scale);
    }
    

}
