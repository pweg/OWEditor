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

        byte clicked = border.getCurrentClicked();
        
        int x = border.getX();
        int y = border.getY();
        
        double width = border.getWidth();
        double height = border.getHeight();
        
        double new_width = 0;
        double new_height = 0;
        
        double scale_x = 0;
        double scale_y = 0;

        
        switch(clicked){
            case(ShapeObjectBorder.UPPERLEFT):
                new_width = p.x - (x+width);
                new_height = p.y - (y+height);
                scale_x = new_width/width;
                scale_y = new_height/height;

                scale_x = Math.min(0, scale_x);
                scale_y = Math.min(0, scale_y);
                break;
            case(ShapeObjectBorder.UPPERRIGHT):
                new_width = p.x - x;
                new_height = p.y - (y+height);
                scale_x = new_width/width;
                scale_y = new_height/height;

                scale_x = Math.max(0, scale_x);
                scale_y = Math.min(0, scale_y);
                break;
            case(ShapeObjectBorder.BOTTOMLEFT):
                new_width = p.x - (x+width);
                new_height = p.y - y;
                scale_x = new_width/width;
                scale_y = new_height/height;

                scale_x = Math.min(0, scale_x);
                scale_y = Math.max(0, scale_y);
                break;
            case(ShapeObjectBorder.BOTTOMRIGHT):
                new_width = p.x - x;
                new_height = p.y - y;
                scale_x = new_width/width;
                scale_y = new_height/height;
                
                scale_x = Math.max(0, scale_x);
                scale_y = Math.max(0, scale_y);
                break;
            default:
                return;
                
        }
        
        double distance = 0;
        double scale = 0;
        
        scale = Math.max(Math.abs(scale_x), Math.abs(scale_y));
        
        if(scale < 0.1)
            return;
        

        System.out.println("scale       "+ scale_x + " " + scale_y);
        System.out.println("width       "+ width + " " + height);
        System.out.println("new width   "+ new_width + " " + new_height);
        System.out.println("Distance    "+ distance);
        
        double distance_x = 0;
        double distance_y = 0;
                
        switch(clicked){
            case(ShapeObjectBorder.UPPERLEFT):
                distance_x = (Math.abs(width*scale) - width);
                distance_y = (Math.abs(height*scale) - height);
                break;
            case(ShapeObjectBorder.UPPERRIGHT):
                distance_x = 0;
                distance_y = Math.abs(height*scale) - height;
                break;
            case(ShapeObjectBorder.BOTTOMLEFT):
                distance_x = Math.abs(width*scale) - width;
                distance_y = 0;
                break;
            case(ShapeObjectBorder.BOTTOMRIGHT):
                distance_x = 0;
                distance_y = 0;
                break;
            default:
                return;
                
        }

        border.setScale(scale);
        border.setTranslation(distance_x, distance_y);

        System.out.println("Distances   "+ distance_x + " " + distance_y);
        System.out.println("------------");
        
        
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

    public void scaleUpdate() {
        ShapeObjectBorder border = smi.getShapeBorder();
        border.updateTranslation();
    }

}
