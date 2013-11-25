package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.geom.Area;
import java.util.ArrayList;


public class sCollisionNotSelectedStrategy implements sCollisionStrategy {
    
    private InternalShapeMediatorInterface smi = null;
    
    public sCollisionNotSelectedStrategy(InternalShapeMediatorInterface smi){
        this.smi = smi;
    }

    @Override
    public boolean checkForCollision(ArrayList<ShapeObject> shapes,
            ArrayList<ShapeDraggingObject> draggingShapes) {

        ArrayList<ShapeObject> shapes2 = new ArrayList<ShapeObject>();
        
        //Get every shape that is not moving at the moment.
        for(ShapeObject shape : smi.getAllShapes()){
            boolean isMoving = false;
            for(ShapeDraggingObject selected : draggingShapes){
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
        for(ShapeDraggingObject selected : draggingShapes){
            for(ShapeObject shape : shapes2){
                Area areaA = new Area(shape.getTransformedShape());
                areaA.intersect(new Area(selected.getTransformedShape()));

                if(selected instanceof ShapeDraggingRect){
                    ShapeDraggingRect r = (ShapeDraggingRect) selected;
                    if(!areaA.isEmpty()){
                        is_collision = true;
                        r.setCollision(true);
                        break;
                    }else{
                        r.setCollision(false);
                    }
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
