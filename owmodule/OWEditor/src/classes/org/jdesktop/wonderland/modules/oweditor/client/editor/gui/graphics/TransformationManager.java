package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics;

import java.awt.Point;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.DraggingObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.ShapeObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.ITransformationBorder;

/**
 * This class is used for transforming shapes (other
 * than translations), like rotation and scaling.
 * 
 * @author Patrick
 *
 */
public class TransformationManager {
    
    private IInternalMediator smi = null;
    private ArrayList<DraggingObject> transformedShapes = null;
    
    /**
     * Creates a new TransformationManager instance.
     * @param smi The internal mediator instance.
     */
    public TransformationManager(IInternalMediator smi){
        transformedShapes = new ArrayList<DraggingObject>();
        this.smi = smi;
    }
    
    /**
     * Initializes a transformation. Adds all
     * created dragging shapes to its own list.
     */
    public void initializeTransformation(){
        transformedShapes.clear();
        transformedShapes.addAll(smi.getDraggingShapes());
    }
    
    /**
     * Returns all transformed shapes.
     * 
     * @return A ArrayList containing all transformed shapes.
     */
    public ArrayList<DraggingObject> getTransformedShapes(){
        return transformedShapes;
    }

    /**
     * Rotates the shapes in its list.
     * The point is used to calculate the angle.
     * 
     * @param p The  point, which is used to calculate
     * the rotation angle.
     */
    public void rotate(Point p){
        ITransformationBorder border = smi.getShapeBorder();
        
        Point rot_center = border.getOriginalCenter();
        
        double rotation = getAngle(rot_center, p);
        
        Point edge = border.getEdge();        
        double edge_rotation = getAngle(border.getOriginalCenter(), edge);
                
        border.setRotation(rotation-edge_rotation);
        
        for(DraggingObject shape : transformedShapes){
            shape.setRotation(rotation-edge_rotation, border.getOriginalCenter());
        }
        
    }

    /**
     * Sets the rotation of one shape.
     * 
     * @param id The id of the shape.
     * @param rotation The rotation angle.
     */
    public void setRotation(long id, double rotation) {
        ShapeObject shape = smi.getShape(id);
        shape.setRotation(rotation);
    }

    /**
     * Translates the rotation center.
     * 
     * @param border The border for which its rotation center
     * should be updated.
     * @param start The start point of the translation.
     * @param end The end point of the translation.
     */
    public void setRotationCenter(ITransformationBorder border, Point start, Point end) {
        
        double distance_x = (start.x - end.x);
        double distance_y = (start.y - end.y);
        
        border.setCenterTranslation(distance_x, distance_y);
    }

    /**
     * Updates the rotation center. This means, the original
     * shape for the border and all dragging shapes is overwritten
     * in order to make further rotations possible.
     * 
     * @param border The border which should be updated.
     */
    public void setRotationCenterUpdate(ITransformationBorder border) {
        border.setRotationCenterUpdate();

        for(DraggingObject shape : transformedShapes){
            shape.setRotationCenterUpdate();
        }
    } 
    
    /**
     * Scales the shapes in its list.
     * 
     * @param p The point, which is used to calculate the 
     * scale.
     */
    public void scale(Point p){
        ITransformationBorder border = smi.getShapeBorder();

        byte clicked = border.getCurrentClicked();
        
        double x = border.getX();
        double y = border.getY();
        
        double width = border.getWidth();
        double height = border.getHeight();
        
        double new_width = 0;
        double new_height = 0;
        
        double scale_x = 0;
        double scale_y = 0;

        //Case for the four different border edges.
        //(Scale calculation is different for every edge)
        switch(clicked){
            case(ITransformationBorder.UPPERLEFT):;
                new_width = p.x - (x+width);
                new_height = p.y - (y+height);
                scale_x = new_width/width;
                scale_y = new_height/height;

                scale_x = Math.min(0, scale_x);
                scale_y = Math.min(0, scale_y);
                break;
            case(ITransformationBorder.UPPERRIGHT):
                new_width = p.x - x;
                new_height = p.y - (y+height);
                scale_x = new_width/width;
                scale_y = new_height/height;

                scale_x = Math.max(0, scale_x);
                scale_y = Math.min(0, scale_y);
                break;
            case(ITransformationBorder.BOTTOMLEFT):
                new_width = p.x - (x+width);
                new_height = p.y - y;
                scale_x = new_width/width;
                scale_y = new_height/height;

                scale_x = Math.min(0, scale_x);
                scale_y = Math.max(0, scale_y);
                break;
            case(ITransformationBorder.BOTTOMRIGHT):
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
        double scale = 0;
        
        scale = Math.max(Math.abs(scale_x), Math.abs(scale_y));
        
        if(scale < 0.1)
            return;
        
        double distance_x = 0;
        double distance_y = 0;
             
        //Case for the four different border edges.
        //(Every edge needs different distances)
        switch(clicked){
            case(ITransformationBorder.UPPERLEFT):
                distance_x = (Math.abs(width*scale) - width);
                distance_y = (Math.abs(height*scale) - height);
                break;
            case(ITransformationBorder.UPPERRIGHT):
                distance_x = 0;
                distance_y = Math.abs(height*scale) - height;
                break;
            case(ITransformationBorder.BOTTOMLEFT):
                distance_x = Math.abs(width*scale) - width;
                distance_y = 0;
                break;
            case(ITransformationBorder.BOTTOMRIGHT):
                distance_x = 0;
                distance_y = 0;
                break;
            default:
                return;
                
        }

        border.setScale(scale);
        border.setScaleDistance(distance_x, distance_y);

        /*
         * The original border coordinates have to be set, for
         * the shapes to calculate the right distance.
         */
        x = border.getX();
        y = border.getY();
        /*
         * The position for the shapes in the border is calculated
         * with the distance to the border.
         */
        for(DraggingObject shape : transformedShapes){
            
            int shapeX = shape.getX();
            int shapeY = shape.getY();
            
            double distanceToBorderX = shapeX-x;
            double distanceToBorderY = shapeY-y;
            double newDistanceX = (distance_x+distanceToBorderX)-distanceToBorderX*scale;
            double newDistanceY = (distance_y+distanceToBorderY)-distanceToBorderY*scale;
  
            shape.setScale(scale, newDistanceX, newDistanceY);
        }
        
    }

    /**
     * Updates the scale of the border and the dragging shapes,
     * which means their original shapes are overwritten in order
     * to do further scale operations.
     */
    public void scaleUpdate() {
        ITransformationBorder border = smi.getShapeBorder();
        border.scaleUpdate();
        
        for(DraggingObject shape : transformedShapes){
            shape.scaleUpdate();
        }
    }

    /**
     * Sets the scale of a specific shape.
     * 
     * @param id The id of the shape.
     * @param scale The new scale.
     */
    public void setScale(long id, double scale) {
        ShapeObject shape = smi.getShape(id);
        shape.setScale(scale);
    }

    /**
     * Calculates the rotation angle.
     * 
     * @param center The rotation center.
     * @param p A point.
     * @return The angle in double.
     */
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
