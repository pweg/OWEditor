package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class ShapeRotationManager {
    
    private InternalShapeMediatorInterface smi = null;
    private ArrayList<ShapeObject> rotatingShapes = null;
    
    public ShapeRotationManager(InternalShapeMediatorInterface smi){
        rotatingShapes = new ArrayList<ShapeObject>();
        this.smi = smi;
    }
    
    public void initializeRotation(){
        rotatingShapes.clear();
        rotatingShapes.addAll(smi.getSelectedShapes());

    }

    public void rotate(Point p){
        ShapeBorder border = smi.getShapeBorder();
        
        Point rot_center = border.getCenter();
        
        int x = rot_center.x;
        int y = rot_center.y;
        
        double rotation = 0f;

        int deltaX = p.x - x;
        int deltaY = p.y - y;

        rotation = -Math.atan2(deltaX, deltaY);
        rotation = Math.toDegrees(rotation) + 180;
        
        System.out.println(rotation);
        border.setRotation(rotation);
    }

}
