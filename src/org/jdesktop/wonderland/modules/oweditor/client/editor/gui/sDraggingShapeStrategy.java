package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.util.ArrayList;

public interface sDraggingShapeStrategy {
    
    public void createDraggingShapes(ArrayList<ShapeObject> selectedShapes);
    
    public boolean checkForCollision(ArrayList<ShapeObject> shapes,
            ArrayList<ShapeObject> draggingShapes);
}
