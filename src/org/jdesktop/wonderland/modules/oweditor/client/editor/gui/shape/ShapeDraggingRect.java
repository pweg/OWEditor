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
public class ShapeDraggingRect extends ShapeDraggingObject{
    
    private Shape originalShape = null;
    private Shape transformedShape = null;
    private Paint color = GUISettings.draggingColor;
    private long id = 0;
    
    private double rotation = 0;
    private double lastRotation = 0;
    private Point rotationPoint = null;
    private double initialRotation = 0;
    
    private double scale = 0;
    
    private int initialWidth = 0;
    private int initialHeight = 0;
    
    private AffineTransform at = null;
    
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
     * @param scale 
     */
    public ShapeDraggingRect(int x, int y, int width, int height, long id, double rotation,
            double scale, AffineTransform at){
        originalShape = new Rectangle (x, y, width, height);
        this.id = id;
        this.initialRotation = rotation;
        this.at = at;
        this.scale = scale;
        initialWidth = width;
        initialHeight = height;
        
        originalShape = at.createTransformedShape(originalShape);
        
        //This is needed for copy shapes, in order to check for
        //collisions correctly.
        rotateInitialShape();    
    }
    
    @Override
    public Shape getTransformedShape() {
        return transformedShape;
    }

    @Override
    public Shape getShape() {
        return originalShape;
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
        
        //transformedShape = at.createTransformedShape(transformedShape);

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
                originalShape.getBounds().getCenterX(), 
                originalShape.getBounds().getCenterY());
        transformedShape = transform.createTransformedShape(originalShape);  
    }

    @Override
    public void setLocation(int x, int y) {
        originalShape.getBounds().setLocation(x, y);
    }

    @Override
    public void setTranslation(double distance_x, double distance_y) {        
        AffineTransform transform = new AffineTransform();
        transform.translate(distance_x, distance_y);
        originalShape = transform.createTransformedShape(originalShape);
        rotateInitialShape();
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

    }
    
    /**
     * Changes color of the shape, when collision is
     * detected or not.
     * 
     * @param col true, when collision is detected,
     *         false otherwise.
     */
    @Override
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

        return state.getX(this, at);
    }

    @Override
    public int getY() {
        if(state == null){
            throw new NullPointerException(
                    "Dragging shape state is null!");
        }
        return state.getY(this, at);
    }

    @Override
    public int getWidth() {
        return initialWidth;
    }

    @Override
    public int getHeight() {
        return initialHeight;
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
        return initialRotation+lastRotation;
    }

    @Override
    public void setState(stateDraggingShape state) {
        this.state = state;
    }

    @Override
    public void setRotationCenterUpdate() {
        if(rotationPoint == null)
            return;
        
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rotation), 
                rotationPoint.x, rotationPoint.y);
        
        originalShape = transform.createTransformedShape(originalShape);
        lastRotation += rotation;
        rotation = 0;
        
    }

    @Override
    public void setScale(double scale, Point scalePoint) {
        this.scale = scale;
    }

    @Override
    public double getScale() {
        return scale;
    }

}
