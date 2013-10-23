package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Graphics2D;
import java.awt.Paint;
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
public class CopyOfShapeObjectDraggingRect extends ShapeObject{
    
    private Rectangle originalShape = null;
    private Shape transformedShape = null;
    private boolean isSelected = false;
    private Paint color = GUISettings.draggingColor;
    private long id;
    private double rotation;
    
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
    public CopyOfShapeObjectDraggingRect(int x, int y, int width, int height, long id, double rotation){
        originalShape = new Rectangle (x, y, width, height);
        this.id = id;
        this.rotation = rotation;
        
        rotateShape();    
    }
    
    @Override
    public Shape getTransformedShape() {
        return transformedShape;
    }

    @Override
    public Rectangle getShape() {
        return originalShape;
    }

    @Override
    public long getID() {
        return id;
    }

    @Override
    public void paintOriginal(Graphics2D g, AffineTransform at) {
        g.setPaint(color);
        
        rotateShape();
        Shape s = at.createTransformedShape(transformedShape);
        g.draw(s); 
    }
    
    private void rotateShape(){
        AffineTransform transform = new AffineTransform();
        
        Shape s = null;
        if(transformedShape == null){
            s = originalShape;
            System.out.println("orig");
        }
        else{
            s = transformedShape;
            System.out.println("trans");
        }
            
        System.out.println(s.getBounds().x);
        transform.rotate(Math.toRadians(rotation), 
                s.getBounds().getCenterX(), s.getBounds().getCenterY());
        transformedShape = transform.createTransformedShape(s);  
        
    }

    @Override
    public void paintName(Graphics2D g, AffineTransform at, double scale) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setSelected(boolean select) {
        isSelected = select;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void switchSelection() {
        isSelected = !isSelected;
    }
    
    @Override
    public void setLocation(int x, int y) {
        originalShape.setLocation(x, y);
    }

    @Override
    public void setTranslation(double distance_x, double distance_y) {
        int new_x = (int) Math.round(transformedShape.getBounds().x-distance_x);
        int new_y = (int) Math.round(transformedShape.getBounds().y-distance_y);
        System.out.println(distance_x  + " xxx");
        
        AffineTransform transform = new AffineTransform();
        transform.translate(distance_x*(-1), distance_y*(-1));  
        System.out.println(transformedShape.getBounds().x + " xxxx");
        transformedShape = transform.createTransformedShape(transformedShape);
        System.out.println(transformedShape.getBounds().x + " xxxx");
        
    }
    
    /**
     * Sets up a new rectangle
     * 
     * @param x the new x coordinate.
     * @param y the new y coordinate.
     * @param width the new width.
     * @param height the new height.
     */
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
        return originalShape.x;
    }

    @Override
    public int getY() {
        return originalShape.y;
    }

    @Override
    public int getWidth() {
        return originalShape.width;
    }

    @Override
    public int getHeight() {
        return originalShape.height;
    }

    @Override
    public String getName() {
        return "DraggingShape"+id;
    }

    @Override
    public void setName(String name) {
    }

    @Override
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    @Override
    public double getRotation() {
        return rotation;
    }

}
