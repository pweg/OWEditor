package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.util.ArrayList;


public interface sCollisionStrategy {
    
    public boolean checkForCollision(ArrayList<ShapeObject> shapes,
            ArrayList<ShapeObject> draggingShapes);
}
