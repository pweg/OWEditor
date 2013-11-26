package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Point;
import java.util.ArrayList;

public class TransformationManager {
    
    private InternalShapeMediatorInterface smi = null;
    private ArrayList<ShapeDraggingObject> transformedShapes = null;
    
    public TransformationManager(InternalShapeMediatorInterface smi){
        transformedShapes = new ArrayList<ShapeDraggingObject>();
        this.smi = smi;
    }
    
    public void initializeTransformation(){
        transformedShapes.clear();
        transformedShapes.addAll(smi.getDraggingShapes());

    }

    public void rotate(Point p){
        ShapeObjectBorder border = smi.getShapeBorder();
        
        Point rot_center = border.getCenter();
        
        double rotation = getAngle(rot_center, p);
        
        Point edge = border.getEdge();        
        double edge_rotation = getAngle(border.getOriginalCenter(), edge);
        
        border.setRotation(rotation-edge_rotation);
        
        for(ShapeDraggingObject shape : transformedShapes){
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


    public void scale(Point p){
        ShapeObjectBorder border = smi.getShapeBorder();
        
        int x = border.getX();
        int y = border.getY();
        
        double width = border.getWidth();
        double height = border.getHeight();
        
        double new_width = p.x - x;
        double new_height = p.y - y;
        
        double scale_x = new_width/width;
        double scale_y = new_height/height;
        
        double scale = Math.max(Math.abs(scale_x), Math.abs(scale_y));
        System.out.println(scale_x + " xscalele");
        System.out.println(scale_y + " yscalele");
        
        byte clicked = border.getCurrentClicked();

        
        double distance_x = 0;
        double distance_y = 0;
                
        switch(clicked){
            case(ShapeObjectBorder.UPPERLEFT):
                distance_x = (x*scale) - x;
                distance_y = (y*scale) - y;
                break;
            case(ShapeObjectBorder.UPPERRIGHT):
                distance_x = x - (x*scale);
                distance_y = (y*scale) - y;
                break;
            case(ShapeObjectBorder.BOTTOMLEFT):
                distance_x = (x*scale) - x;
                distance_y = y - (y*scale);
                break;
            case(ShapeObjectBorder.BOTTOMRIGHT):
                distance_x = x - (x*scale);
                distance_y = y - (y*scale);
                break;
            default:
                return;
                
        }

        border.setScale(scale);
        border.setTranslation(distance_x, distance_y);
        
        
        //Point rot_center = border.getCenter();
        
        //double rotation = getAngle(rot_center, p);
        
        //Point edge = border.getEdge();        
        //double edge_rotation = getAngle(border.getOriginalCenter(), edge);
        
        //border.setRotation(rotation-edge_rotation);
        
        for(ShapeDraggingObject shape : transformedShapes){
            //shape.setRotation(rotation-edge_rotation, rot_center);
        }
        
    }
    
    public ArrayList<ShapeDraggingObject> getTransformedShapes(){
        return transformedShapes;
    }

    public void setRotation(long id, double rotation) {
        ShapeObject shape = smi.getShape(id);
        shape.setRotation(rotation);
    }

    public void setRotationCenter(ShapeObjectBorder border, Point start, Point end) {
        int distance_x = start.x - end.x;
        int distance_y = start.y - end.y;
        
        border.setCenterTranslation(distance_x, distance_y);
        
        //border.setLocation(x, y);
    }

    public void setRotationCenterUpdate(ShapeObjectBorder border) {
        border.setRotationCenterUpdate();
    }

}
