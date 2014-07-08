package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes;

import java.awt.Rectangle;
import java.awt.Shape;


/**
 * This method calculates the coordinate from the center, using the 
 * transformed shape. 
 * This are not the coordinates used by the adapter!
 * 
 * @author Patrick
 *
 */
public class stateTransformedCenter implements stateDraggingShape{

    @Override
    public int getX(DraggingObject shape) {
        Shape transformed = shape.getTransformedShape();
        Rectangle bounds_transformed = transformed.getBounds();
        
            
        return (int) Math.round(bounds_transformed.getCenterX());

    }

    @Override
    public int getY(DraggingObject shape) {        
        Shape transformed = shape.getTransformedShape();
        Rectangle bounds_transformed = transformed.getBounds();
        
        return (int) Math.round(bounds_transformed.getCenterY());

    }

}
