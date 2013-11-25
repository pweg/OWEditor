package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Point;
import java.util.ArrayList;

public class ScaleManager {
    
    private InternalShapeMediatorInterface smi = null;
    private ArrayList<ShapeDraggingObject> scalingShapes = null;
    
    public ScaleManager(InternalShapeMediatorInterface smi){
        scalingShapes = new ArrayList<ShapeDraggingObject>();
        this.smi = smi;
    }
    
    public void initializeRotation(){
        scalingShapes.clear();
        scalingShapes.addAll(smi.getDraggingShapes());

    }

    public void scale(Point p){
        ShapeObjectBorder border = smi.getShapeBorder();
        
        Point rot_center = border.getCenter();
        
        double rotation = getAngle(rot_center, p);
        
        Point edge = border.getEdge();        
        double edge_rotation = getAngle(border.getOriginalCenter(), edge);
        
        border.setScale(rotation-edge_rotation);
        
        for(ShapeDraggingObject shape : scalingShapes){
            shape.setScale(rotation-edge_rotation, rot_center);
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
    
    public ArrayList<ShapeDraggingObject> getScaledShapes(){
        return scalingShapes;
    }

    public void setScale(long id, double scale) {
        ShapeObject shape = smi.getShape(id);
        shape.setScale(scale);
    }

}
