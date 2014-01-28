package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes;

import java.awt.Point;


/**
 * Dragging objects are shapes, which appear, when an operation is 
 * made on normal shapes. They should be used for rotation, translation,
 * scaling etc. This is done to ensure that the operation is not disturbed
 * by another user, changing also the object.
 * 
 * @author Patrick
 *
 */
public abstract class DraggingObject extends SimpleShapeObject{

    /**
     * Returns the id of the object to which this dragging shape
     * belongs.
     * 
     * @return The id.
     */
    public abstract long getID();
    
    /**
     * Sets the state of the dragging shape, which is used
     * to determine the shapes coordinates, after translation/
     * transformation has finished.
     * 
     * @param state
     */
    public abstract void setState(stateDraggingShape state);
    
    /**
     * Returns the current state of the shape.
     * 
     * @return The state, or null, if no state was set.
     */
    public abstract stateDraggingShape getState();

    /**
     * Changes color of the shape, when collision is
     * true or not.
     * 
     * @param col true, when collision is detected,
     *         false otherwise.
     */
    public abstract void setCollision(boolean col);
    
    /**
     * Sets a rotation around a given point.
     * 
     * @param rotation The rotation value.
     * @param rotationCenter The rotation center.
     */
    public abstract void setRotation(double rotation, Point rotationCenter);
    
    /**
     * Returns the current rotation.
     * 
     * @return The rotation.
     */
    public abstract double getRotation();
    
    /**
     * Updates the rotation, which means the original shape is
     * overwritten, in order to allow another rotation.
     */
    public abstract void setRotationCenterUpdate();

    /**
     * Sets the scale for the shape.
     * 
     * @param scale The new scale.
     * @param distanceX The distance on the x axis, which the scaled shape
     * has to traverse, in order to reach the needed position.
     * @param distanceY The distance on the y axis, which the scaled shape
     * has to traverse, in order to reach the needed position.
     */
    public abstract void setScale(double scale, double distanceX, double distanceY);
    
    /**
     * Updates the scale, which means the original shape is 
     * overwritten, to allow further scales.
     * (The working scale is also set to 1)
     */
    public abstract void scaleUpdate();
        
    /**
     * Returns the real scale, which the shape has, not 
     * the currently working scale.
     * 
     * @return The scale.
     */
    public abstract double getScale();
    
    /**
     * Returns the x value of the transformed shape.
     * 
     * @return The x coordinate.
     */
    public abstract int getTransformedX();
    
    /**
     * Returns the y value of the transformed shape.
     * 
     * @return The y coordinate.
     */
    public abstract int getTransformedY();
    
    /**
     * Returns the width of the transformed shape.
     * 
     * @return The width coordinate.
     */
    public abstract int getTransformedWidth();
    
    /**
     * Returns the height value of the transformed shape.
     * 
     * @return The height coordinate.
     */
    public abstract int getTransformedHeight();
}
