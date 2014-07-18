package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics;

import java.awt.Point;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.DraggingObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.ITransformationBorder;

/**
 * This class is used for transforming dragging shapes,
 * which is usually done by user input from the input
 * package.  
 *   
 * @author Patrick
 *
 */
public class EditorTransformationManager {

    private IInternalMediator med = null;
    private sCollisionStrategy strategy = null;
    
    public EditorTransformationManager(IInternalMediator med){
        this.med = med;
    }
    
    /**
     * Moves the dragging shapes, which are shown, when shapes are dragged. 
     * 
     * @param end The end point to where the shape has been dragged.
     * @param start the start point from where the shape has been dragged.
     * @param strategy The collision strategy.
     */
    public void translate(Point end, Point start, sCollisionStrategy strategy){
        double distance_x = end.x - start.x;
        double distance_y = end.y - start.y;

        translateDraggingShapes(distance_x, distance_y);
        checkForCollision(strategy);
          
        med.repaint();
    }

    /**
     * Translates the dragging shapes(DraggingRectangles), which 
     * are seen, when trying to move objects.
     * 
     * @param distance_x the x distance between old and new point.
     * @param distance_y the y distance between old and new point.
     */
    public void translateDraggingShapes(double distance_x, double distance_y){
        
        for(DraggingObject shape : med.getDraggingShapes()){
            shape.setTranslation(distance_x, distance_y);
        }
    }
    
    /**
     * Rotates the shapes in its list.
     * The point is used to calculate the angle.
     * 
     * @param p The  point, which is used to calculate
     * the rotation angle.
     */
    public void rotate(Point p, sCollisionStrategy strategy){
        ITransformationBorder border = med.getShapeBorder();
        
        Point rot_center = border.getOriginalCenter();
        
        double rotation = getAngle(rot_center, p);
        
        Point edge = border.getEdge();        
        double edge_rotation = getAngle(border.getOriginalCenter(), edge);
                
        border.setRotation(rotation-edge_rotation);
        
        for(DraggingObject shape : med.getDraggingShapes()){
            shape.setRotation(rotation-edge_rotation, border.getOriginalCenter());
        }

        checkForCollision(strategy);
        
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

        for(DraggingObject shape : med.getDraggingShapes()){
            shape.setRotationCenterUpdate();
        }
    } 
    
    /**
     * Scales the shapes in its list.
     * 
     * @param p The point, which is used to calculate the 
     * scale.
     */
    public void scale(Point p, sCollisionStrategy strategy){
        ITransformationBorder border = med.getShapeBorder();

        byte clicked = border.getCurrentClicked();
        
        double x = border.getX();
        double y = border.getY();
        
        double width = border.getWidth();
        double height = border.getHeight();
        
        double new_width;
        double new_height;
        
        double scale_x;
        double scale_y;

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
        
        double scale = Math.max(Math.abs(scale_x), Math.abs(scale_y));
        
        if(scale < 0.1)
            return;
        
        double distance_x;
        double distance_y;
             
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
        for(DraggingObject shape : med.getDraggingShapes()){
            
            int shapeX = shape.getX();
            int shapeY = shape.getY();
            
            double distanceToBorderX = shapeX-x;
            double distanceToBorderY = shapeY-y;
            double newDistanceX = (distance_x+distanceToBorderX)-distanceToBorderX*scale;
            double newDistanceY = (distance_y+distanceToBorderY)-distanceToBorderY*scale;
  
            shape.setScale(scale, newDistanceX, newDistanceY);
        }
        checkForCollision(strategy);
        
    }
    
    /**
     * Updates the scale of the border and the dragging shapes,
     * which means their original shapes are overwritten in order
     * to do further scale operations.
     */
    public void scaleUpdate() {
        ITransformationBorder border = med.getShapeBorder();
        border.scaleUpdate();
        
        for(DraggingObject shape : med.getDraggingShapes()){
            shape.scaleUpdate();
        }
    }
    
    /**
     * Checks for collision when dragging shapes, using the last 
     * strategy set in a previous check.
     *  
     * @return returns true, if a collision is detected and false otherwise.
     */
    public boolean checkForCollision(){
        return checkForCollision(null);
    }

    /**
     * Checks for collision when dragging shapes.
     * 
     * @param strategy The strategy which should be used. If it is null
     * the last known strategy will be used.
     * 
     * @return returns true, if a collision is detected and false otherwise.
     */
    private boolean checkForCollision(sCollisionStrategy strategy) {
        if(strategy == null && this.strategy == null)
            return false;
        if(strategy == null)
            strategy = this.strategy;
        else
            this.strategy = strategy;
        
        return strategy.checkForCollision(med.getAllShapes(), med.getDraggingShapes());
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
        
        int deltaX = p.x - x;
        int deltaY = p.y - y;
        
        double rotation = -Math.atan2(deltaX, deltaY);
        rotation = Math.toDegrees(rotation)+180;
        
        return rotation;
    }


}
