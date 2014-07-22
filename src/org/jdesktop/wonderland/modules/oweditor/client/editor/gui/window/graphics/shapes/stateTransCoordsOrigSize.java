package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes;

import java.awt.Rectangle;
import java.awt.Shape;


/**
 * This state is used for retrieving the coordinates during a rotation.
 * It calculates the coordinate out of the center, using the 
 * the transformed shape, BUT uses the ORIGINALS size in order to
 * get the right x,y coordinates for the server object. 
 * This is needed because the rotated object does give wrong size information.
 * 
 * This are not the coordinates used by the adapter!
 * 
 * @author Patrick
 *
 */
public class stateTransCoordsOrigSize implements stateDraggingShape{

    @Override
    public int getX(DraggingObject shape) {
        Shape transformed = shape.getTransformedShape();
        Rectangle bounds = transformed.getBounds();
        double x = bounds.getCenterX();
            
        //Shapes original width has to be used , because we want to get 
        //the originals coordinates. This may have to be considered, when
        //building the coordinate translator in the adapter package.
        return (int) Math.round(x - (shape.getWidth()/2)*shape.getScale());

    }

    @Override
    public int getY(DraggingObject shape) {
        Shape transformed = shape.getTransformedShape();
        Rectangle bounds = transformed.getBounds();
        double y = bounds.getCenterY();

        //Shapes original height has to be used , because we want to get 
        //the originals coordinates. This may have to be considered, when
        //building the coordinate translator in the adapter package.
        return (int) Math.round(y - shape.getHeight()/2*shape.getScale());

    }

}
