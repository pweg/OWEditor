package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics;

import java.awt.geom.Area;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.DraggingObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.ShapeObject;


public class sCollisionNotSelectedStrategy implements sCollisionStrategy {
    
    private InternalShapeMediatorInterface smi = null;
    
    public sCollisionNotSelectedStrategy(InternalShapeMediatorInterface smi){
        this.smi = smi;
    }

    @Override
    public boolean checkForCollision(ArrayList<ShapeObject> shapes,
            ArrayList<DraggingObject> draggingShapes) {

        ArrayList<ShapeObject> shapes2 = new ArrayList<ShapeObject>();
        
        //Get every shape that is not moving at the moment.
        for(ShapeObject shape : smi.getAllShapes()){
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
    
    /*
    @Override
    public void createDraggingShapes(ArrayList<ShapeObject> selectedShapes) {
        
        //stm.createDraggingShapes(selectedShapes);
        
        if(smi.isDraggingShapesEmpty()){
            smi.setDraggingShapes(smi.createDraggingShapes(selectedShapes));            
        }
        
    }*/

}
