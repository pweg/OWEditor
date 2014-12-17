package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics;

import java.awt.geom.Area;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.DraggingObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.ShapeObject;

/**
 * This strategy should be used, when the shape should not collide
 * with objects currently selected, but with every other objects.
 * @author Patrick
 */
public class sCollisionNotSelectedStrategy implements sCollisionStrategy {
    
    public sCollisionNotSelectedStrategy(){
    }

    @Override
    public boolean checkForCollision(ArrayList<ShapeObject> shapes,
            ArrayList<DraggingObject> draggingShapes) {

        ArrayList<ShapeObject> shapes2 = new ArrayList<ShapeObject>();
        
        //Get every shape that is not moving at the moment.
        for(ShapeObject shape : shapes){
            boolean isMoving = false;
            for(DraggingObject selected : draggingShapes){
                if(selected.getID() == shape.getID()){
                    isMoving = true;
                    break;
                }
            }
            if(!isMoving){
                shapes2.add(shape);
            }
        }
        
        boolean is_collision = false;
        for(DraggingObject selected : draggingShapes ){
            
            for(ShapeObject shape : shapes2){
                Area areaA = new Area(shape.getTransformedShape());
                areaA.intersect(new Area(selected.getTransformedShape()));

                
                    if(!areaA.isEmpty()){
                        selected.setCollision(true);
                        is_collision = true;
                        break;
                    }else{
                        selected.setCollision(false);
                    }
                
            }
        }
        return is_collision;
    }

}
