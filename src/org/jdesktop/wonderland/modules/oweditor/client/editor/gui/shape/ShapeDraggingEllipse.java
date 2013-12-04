package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUISettings;

/**
 * Derived from ShapeObject. This is a shape type, which is used
 * for the implementation of the dragging shapes feature. It is
 * usually a copy of another shape and should be viewed as a 
 * short lived object, which only exists while the user is dragging
 * selected shapes.
 * 
 * This shape only draws the shape outline and does not fill it.
 * 
 * @author Patrick
 *
 */
public class ShapeDraggingEllipse extends ShapeDraggingObject{
    
    private Ellipse2D originalShape = null;
    private Shape transformedShape = null;
    private Paint color = GUISettings.DRAGGINGCOLOR;
    private long id;
    private double rotation = 0;
    private double scale = 0;
    
    /**
     * Creates a new dragginRect shape object.
     * 
     * @param x the x coordinate of the shape.
     * @param y the y coordinate of the shape.
     * @param width the width of the shape.
     * @param height the height of the shape.
     * @param id the id of the shape. Usually this is the id of the shape which
     *               is copied.
     */
    public ShapeDraggingEllipse(int x, int y, int width, int height, long id,
            double rotation){
        originalShape = new Ellipse2D.Double(x, y, width, height);
        this.id = id;
        this.rotation = rotation;
    }

    @Override
    public long getID() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setState(stateDraggingShape state) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public stateDraggingShape getState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setCollision(boolean col) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setRotation(double rotation, Point p) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public double getRotation() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setRotationCenterUpdate() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setScale(double scale, double distanceX, double distanceY) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void scaleUpdate() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public double getScale() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Shape getShape() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Shape getTransformedShape() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void paintOriginal(Graphics2D g, AffineTransform at) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setLocation(int x, int y) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setTranslation(double distance_x, double distance_y) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getX() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getY() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getWidth() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getHeight() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void set(int x, int y, int width, int height) {
        // TODO Auto-generated method stub
        
    }
}
