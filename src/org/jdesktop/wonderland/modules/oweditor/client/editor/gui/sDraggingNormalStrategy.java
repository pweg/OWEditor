package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.geom.Area;
import java.util.ArrayList;

public class sDraggingNormalStrategy implements sDraggingShapeStrategy {
    
    private ShapeManager sm = null;
    private ShapeTranslationManager stm = null;
    
    public sDraggingNormalStrategy(ShapeManager sm, ShapeTranslationManager stm){
        this.sm = sm;
        this.stm = stm;
    }

    @Override
    public boolean checkForCollision(ArrayList<ShapeObject> shapes,
            ArrayList<ShapeObject> draggingShapes) {

        ArrayList<ShapeObject> shapes2 = new ArrayList<ShapeObject>();
        
        //Get every shape that is not moving at the moment.
        for(ShapeObject shape : sm.getShapes()){
            boolean isMoving = false;
            for(ShapeObject selected : draggingShapes){
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
        for(ShapeObject selected : draggingShapes){
            for(ShapeObject shape : shapes2){
                Area areaA = new Area(shape.getTransformedShape());
                areaA.intersect(new Area(selected.getTransformedShape()));

                if(selected instanceof ShapeObjectDraggingRect){
                    ShapeObjectDraggingRect r = (ShapeObjectDraggingRect) selected;
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

    @Override
    public void createDraggingShapes(ArrayList<ShapeObject> selectedShapes) {
        
        //stm.createDraggingShapes(selectedShapes);
        
        if(stm.isDraggingShapesEmpty()){
            stm.setDraggingShapes(sm.builtDraggingShapes(selectedShapes));            
        }
        
    }

}
