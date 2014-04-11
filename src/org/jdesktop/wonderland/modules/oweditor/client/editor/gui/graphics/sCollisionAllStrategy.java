package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics;

import java.awt.geom.Area;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.DraggingObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.ShapeObject;

/**
 * This strategy is used, when the object should collide with all other objects.
 * 
 * @author Patrick
 */
public class sCollisionAllStrategy implements sCollisionStrategy {
        

    public sCollisionAllStrategy() {
    }

    @Override
    public boolean checkForCollision(ArrayList<ShapeObject> shapes,
            ArrayList<DraggingObject> draggingShapes) {

        boolean is_collision = false;
        for(DraggingObject selected : draggingShapes){
            Area draggingArea = new Area(selected.getTransformedShape());
            
            for(ShapeObject shape : shapes){
                Area area = new Area(shape.getTransformedShape());

                area.intersect(draggingArea);
                if(!area.isEmpty()){
                    is_collision = true;
                    break;
                }
                
            }
            selected.setCollision(is_collision);
            }
        return is_collision;
    }
}
