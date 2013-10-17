package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.geom.Area;
import java.util.ArrayList;

public class sDraggingCopyStrategy implements sDraggingShapeStrategy {
    
    private ShapeManager sm = null;
    private ShapeCopyManager scm = null;
    private ShapeTranslationManager stm = null;
    
    public sDraggingCopyStrategy(ShapeManager sm, ShapeCopyManager scm,
            ShapeTranslationManager stm){
        this.sm = sm;
        this.scm = scm;
        this.stm = stm;
    }

    @Override
    public boolean checkForCollision(ArrayList<ShapeObject> shapes,
            ArrayList<ShapeObject> draggingShapes) {

        boolean is_collision = false;
        for(ShapeObject selected : draggingShapes){
            for(ShapeObject shape : shapes){
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
    public void createDraggingShapes(ArrayList<ShapeObject> draggingShapes) {
        if(stm.isDraggingShapesEmpty()){
            stm.setDraggingShapes(sm.builtDraggingShapes(scm.getCopyShapes()));
        }
        
    }

}
