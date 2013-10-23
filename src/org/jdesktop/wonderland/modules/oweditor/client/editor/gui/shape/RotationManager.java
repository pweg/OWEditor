package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Point;
import java.util.ArrayList;

public class RotationManager {
    
    private InternalShapeMediatorInterface smi = null;
    private ArrayList<ShapeObject> rotatingShapes = null;
    
    public RotationManager(InternalShapeMediatorInterface smi){
        rotatingShapes = new ArrayList<ShapeObject>();
        this.smi = smi;
    }
    
    public void initializeRotation(){
        rotatingShapes.clear();
        rotatingShapes.addAll(smi.getSelectedShapes());

    }

    public void rotate(Point p){
        ShapeObjectBorder border = smi.getShapeBorder();
        
        Point rot_center = border.getCenter();
        
        double rotation = getAngle(rot_center, p);
        
        Point edge = border.getEdge();        
        double edge_rotation = getAngle(border.getOriginalCenter(), edge);
        
        border.setRotation(rotation-edge_rotation);
    }
    
    private double getAngle(Point center, Point p){
        int x = center.x;
        int y = center.y;
        double rotation = 0f;
        
        int deltaX = p.x - x;
        int deltaY = p.y - y;
        
        rotation = -Math.atan2(deltaX, deltaY);
        rotation = Math.toDegrees(rotation)+180;
        
        return rotation;
    }

}
