package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Point;
import java.util.ArrayList;

public class RotationManager {
    
    private InternalShapeMediatorInterface smi = null;
    private ArrayList<ShapeDraggingObject> rotatingShapes = null;
    
    public RotationManager(InternalShapeMediatorInterface smi){
        rotatingShapes = new ArrayList<ShapeDraggingObject>();
        this.smi = smi;
    }
    
    public void initializeRotation(){
        rotatingShapes.clear();
        rotatingShapes.addAll(smi.getDraggingShapes());

    }

    public void rotate(Point p){
        ShapeObjectBorder border = smi.getShapeBorder();
        
        Point rot_center = border.getCenter();
        
        double rotation = getAngle(rot_center, p);
        
        Point edge = border.getEdge();        
        double edge_rotation = getAngle(border.getOriginalCenter(), edge);
        
        border.setRotation(rotation-edge_rotation);
        
        for(ShapeDraggingObject shape : rotatingShapes){
            shape.setRotation(rotation-edge_rotation, rot_center);
        }
        
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
    
    public ArrayList<ShapeDraggingObject> getRotatedShapes(){
        return rotatingShapes;
    }

    public void setRotation(long id, double rotation) {
        ShapeObject shape = smi.getShape(id);
        shape.setRotation(rotation);
    }

}
