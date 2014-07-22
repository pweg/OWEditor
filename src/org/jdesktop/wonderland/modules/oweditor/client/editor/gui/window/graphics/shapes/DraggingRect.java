package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes;

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
public class DraggingRect extends DraggingObject{
    
    private Shape originalShape = null;
    private Shape scaledShape = null;
    private Shape transformedShape = null;
    private Shape printShape = null;
    private Paint color = GUISettings.DRAGGINGCOLOR;
    private long id = 0;
    
    private double rotation = 0;
    private double lastRotation = 0;
    private Point rotationCenter = null;
    private double initialRotation = 0;

    private double initialScale = 1;
    private double realScale = 1;
    private double workingScale = 1;
    
    private int initialWidth = 0;
    private int initialHeight = 0;
    
    private boolean first = true;
    
    private stateDraggingShape state = null;

    private double scaleTranslationX = 0;
    private double scaleTranslationY = 0;
    
    
    /**
     * Creates a new dragginRect shape object.
     * 
     * @param x The x coordinate of the shape.
     * @param y The y coordinate of the shape.
     * @param width The width of the shape.
     * @param height The height of the shape.
     * @param id The id of the shape. Usually this is the id of the shape which
     *               is copied.
     * @param rotation The rotation of the shape.
     * @param scale  The scale of the shape.
     * @param at     The affine transformation, which was used in the last 
     * draw cycle. 
     */
    public DraggingRect(int x, int y, int width, int height, long id, double rotation,
            double scale, AffineTransform at){
        originalShape = new Rectangle (x, y, width, height);
        this.id = id;
        this.initialRotation = rotation;
        this.initialScale = scale;
        workingScale = scale;
        realScale = scale;
        initialWidth = width;
        initialHeight = height;
        //originalShape = at.createTransformedShape(originalShape);
        
        //This is needed for creating the border around dragging shapes
        //When the border is created, the shapes still have not been drawn.
        scaledShape = ShapeUtilities.scaleShape(originalShape, initialScale,0,0);
        transformedShape = ShapeUtilities.rotateShape(scaledShape, initialRotation);  
        printShape = at.createTransformedShape(transformedShape);
    }

    @Override
    public Shape getShape() {
        return originalShape;
    }
    
    @Override
    public Shape getTransformedShape() {
        return transformedShape;
    }

    @Override
    public void paintOriginal(Graphics2D g, AffineTransform at) {
        
        //Be advised, the following order of this section should not
        //be disturbed. Otherwise the objects will be all over the place.
        g.setPaint(color);
        
        //AffineTransform af = AffineTransform.getScaleInstance(scale, scale);
        scaledShape = ShapeUtilities.scaleShape(originalShape, workingScale,
                scaleTranslationX,scaleTranslationY);
        transformedShape = ShapeUtilities.rotateShape(scaledShape, initialRotation);

        //This is the rotation, when using the rotate operation.
        if(rotationCenter != null){
            AffineTransform transform = new AffineTransform();
            transform.rotate(Math.toRadians(rotation), 
                    rotationCenter.getX(), 
                    rotationCenter.getY());
            transformedShape = transform.createTransformedShape(transformedShape);
        }

        printShape = at.createTransformedShape(transformedShape);
        
        g.draw(printShape); 
    }

    /**
     * NOT IMPLEMENTED for dragging shapes.
     */
    @Override
    public void setLocation(int x, int y, double z) {
        
    }

    @Override
    public void setTranslation(double distance_x, double distance_y) {       
        originalShape = ShapeUtilities.translateShape(originalShape,
                distance_x,distance_y);
        
        /*
         * This needs to be done in order for translation affect immediately.
         * Otherwise all usage of the transformed shape will be from the last
         * repaint. So, collision detection would be off, for example.
         */
        scaledShape = ShapeUtilities.scaleShape(originalShape, workingScale,
                scaleTranslationX,scaleTranslationY);
        transformedShape = ShapeUtilities.rotateShape(scaledShape, initialRotation);
    }

    @Override
    public int getX() {
        if(state == null)
            return originalShape.getBounds().x;
        else
            return state.getX(this);
    }
    

    @Override
    public int getY() {
        if(state == null)
            return originalShape.getBounds().y;
        else
            return state.getY(this);
    }

    @Override
    public int getWidth() {
        return initialWidth;
    }

    @Override
    public int getHeight() {
        return initialHeight;
    }
    
    /**
     * NOT IMPLEMENTED for dragging shapes.
     */
    @Override
    public void set(int x, int y, int width, int height){
    }

    @Override
    public long getID() {
        return id;
    }

    @Override
    public void setState(stateDraggingShape state) {
        this.state = state;
    }

    @Override
    public stateDraggingShape getState() {
        return state;
    }
    
    @Override
    public void setCollision(boolean col){
        if(!col)
            color = GUISettings.DRAGGINGCOLOR;
        else
            color = GUISettings.COLLISIONCOLOR;
    }

    @Override
    public void setRotation(double rotation, Point rotationCenter) {
        this.rotation = rotation;
        this.rotationCenter = rotationCenter;
    }

    @Override
    public double getRotation() {
        return initialRotation+lastRotation;
    }

    @Override
    public void setRotationCenterUpdate() {
        if(first){
            realScale = 1;
            first = false;
        }
        
        if(rotationCenter == null)
            return;

        originalShape = ShapeUtilities.rotateShape(scaledShape, rotation, rotationCenter);
        lastRotation += rotation;
        rotation = 0;
        realScale = (workingScale)*realScale;
        workingScale = 1;
        initialScale = 1;
        
    }

    @Override
    public void setScale(double scale, double distanceX, double distanceY) {
        this.workingScale = initialScale*scale;
        this.scaleTranslationX = distanceX;
        this.scaleTranslationY = distanceY; 
    }

    @Override
    public void scaleUpdate() {
        if(first){
            realScale = 1;
            first = false;
        }

        originalShape = ShapeUtilities.scaleShape(originalShape, workingScale, 
                scaleTranslationX, scaleTranslationY);
        realScale = (workingScale)*realScale;
        workingScale = 1;
        initialScale = 1;
        scaleTranslationX = 0;
        scaleTranslationY = 0;
    }

    @Override
    public double getScale() {
        return realScale;
    }

    @Override
    public int getTransformedX() {
        return transformedShape.getBounds().x;
    }

    @Override
    public int getTransformedY() {
        return transformedShape.getBounds().y;
    }

    @Override
    public int getTransformedWidth() {
        return transformedShape.getBounds().width;
    }

    @Override
    public int getTransformedHeight() {
        return transformedShape.getBounds().height;
    }

}
