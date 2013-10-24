package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

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
public class ShapeObjectDraggingRect extends ShapeDraggingObject{
    
    private Rectangle originalShape = null;
    private Shape transformedShape = null;
    private Shape scaledShape = null;
    private Paint color = GUISettings.draggingColor;
    private long id = 0;
    private double rotation = 0;
    private Point rotationPoint = null;
    private double initialRotation = 0;
    private double scale = 0;
    
    private stateDraggingShape state = null;
    
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
    public ShapeObjectDraggingRect(int x, int y, int width, int height, long id, double rotation,
            double scale){
        originalShape = new Rectangle (x, y, width, height);
        this.id = id;
        this.initialRotation = rotation;
        this.scale = scale;
        
        AffineTransform transform = new AffineTransform();
        transform.scale(scale, scale);
        scaledShape = transform.createTransformedShape(originalShape);
        
        //rotateInitialShape();    
    }
    
    @Override
    public Shape getTransformedShape() {
        return transformedShape;
    }

    @Override
    public Shape getShape() {
        return scaledShape;
    }

    @Override
    public long getID() {
        return id;
    }

    @Override
    public void paintOriginal(Graphics2D g, AffineTransform at) {
        
        //Be advised, the following order of this section should not
        //be disturbed. Otherwise the objects will be all over the place.
        g.setPaint(color);
        
        rotateInitialShape();
        
        transformedShape = at.createTransformedShape(transformedShape);

        //This is the rotation, when using the rotate opperation.
        if(rotationPoint != null){
            AffineTransform transform = new AffineTransform();
            
            transform.rotate(Math.toRadians(rotation), 
                    rotationPoint.getX(), 
                    rotationPoint.getY());
            transformedShape = transform.createTransformedShape(transformedShape);
        }
        
        g.draw(transformedShape); 
    }
    
    private void rotateInitialShape(){
        //This is the initial rotation, whether the object was rotated
        //before.
        AffineTransform transform = new AffineTransform();
        
        transform.rotate(Math.toRadians(initialRotation), 
                scaledShape.getBounds().getCenterX(), 
                scaledShape.getBounds().getCenterY());
        transformedShape = transform.createTransformedShape(scaledShape);  
    }

    @Override
    public void setLocation(int x, int y) {
        originalShape.setLocation(x, y);
    }

    @Override
    public void setTranslation(double distance_x, double distance_y) {        
        AffineTransform transform = new AffineTransform();
        transform.translate(distance_x, distance_y);
        scaledShape = transform.createTransformedShape(scaledShape);
    }
    
    /**
     * Sets up a new rectangle
     * 
     * @param x the new x coordinate.
     * @param y the new y coordinate.
     * @param width the new width.
     * @param height the new height.
     */
    @Override
    public void set(int x, int y, int width, int height){
        originalShape.setRect(x, y, width, height);
    }
    
    /**
     * Changes color of the shape, when collision is
     * detected or not.
     * 
     * @param col true, when collision is detected,
     *         false otherwise.
     */
    public void setCollision(boolean col){

        if(!col)
            color = GUISettings.draggingColor;
        else
            color = GUISettings.draggingCollisionColor;
    }

    @Override
    public int getX() {
        if(state == null){
            throw new NullPointerException(
                    "Dragging shape state is null!");
        }

        return state.getX(this, scale);
    }

    @Override
    public int getY() {
        if(state == null){
            throw new NullPointerException(
                    "Dragging shape state is null!");
        }
        return state.getY(this, scale);
    }

    @Override
    public int getWidth() {
        return originalShape.width;
    }

    @Override
    public int getHeight() {
        return originalShape.height;
    }

    /*
    @Override
    public String getName() {
        return "DraggingShape"+id;
    }*/

    @Override
    public void setRotation(double rotation, Point p) {
        this.rotation = rotation;
        rotationPoint = p;
    }

    @Override
    public double getRotation() {
        return initialRotation+rotation;
    }

    @Override
    public void setState(stateDraggingShape state) {
        this.state = state;
    }

    @Override
    public void setRotationCenterUpdate(int x, int y) {
        if(rotationPoint == null)
            return;
        
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rotation), 
                rotationPoint.x-x, rotationPoint.y-y);
        System.out.println(rotationPoint.x +" " +scaledShape.getBounds().x);
        
        scaledShape = transform.createTransformedShape(scaledShape);
        rotation = 0;
        
    }

}
