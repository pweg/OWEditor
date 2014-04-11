package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.DraggingObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.ShapeObject;

/**
 * Interface for collision strategies.
 * 
 * @author Patrick
 */
public interface sCollisionStrategy {
    
    public boolean checkForCollision(ArrayList<ShapeObject> shapes,
            ArrayList<DraggingObject> draggingShapes);
}
