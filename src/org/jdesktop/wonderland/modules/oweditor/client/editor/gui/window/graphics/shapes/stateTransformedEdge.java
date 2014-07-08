package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes;

import java.awt.Rectangle;
import java.awt.Shape;


/**
 * This method for retrieving the coordinates used for the border creation.
 * It returns the normal coordinates of the transformed shape,
 * so in other words the left top edge. 
 * This are not the coordinates used by the adapter!
 * 
 * @author Patrick
 *
 */
public class stateTransformedEdge implements stateDraggingShape{

    @Override
    public int getX(DraggingObject shape) {
        Shape transformed = shape.getTransformedShape();
        Rectangle bounds = transformed.getBounds();
        double x = bounds.getX();

        return (int) Math.round(x );
    }

    @Override
    public int getY(DraggingObject shape) {
        Shape transformed = shape.getTransformedShape();
        Rectangle bounds = transformed.getBounds();
        double y = bounds.getY();
        
        return (int) Math.round(y);
            
    }

}
