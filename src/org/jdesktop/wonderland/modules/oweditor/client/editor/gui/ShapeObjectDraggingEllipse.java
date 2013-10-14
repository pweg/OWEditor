package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

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
public class ShapeObjectDraggingEllipse extends ShapeObject{
    
    private Ellipse2D originalShape = null;
    private Shape transformedShape = null;
    private boolean isSelected = false;
    private Paint color = GUISettings.draggingColor;
    private long id;
    
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
    public ShapeObjectDraggingEllipse(int x, int y, int width, int height, long id){
        originalShape = new Ellipse2D.Double(x, y, width, height);
        this.id = id;
    }
    
    @Override
    public Shape getTransformedShape() {
        return transformedShape;
    }

    @Override
    public Ellipse2D getShape() {
        return originalShape;
    }

    @Override
    public long getID() {
        return id;
    }

    @Override
    public void paintOriginal(Graphics2D g, AffineTransform at, double scale) {
        g.setPaint(color);
        
        transformedShape = at.createTransformedShape(originalShape);
        g.draw(at.createTransformedShape(originalShape)); 
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
        originalShape.setFrame(x, y, originalShape.getWidth(), originalShape.getHeight());
    }

    @Override
    public void setTranslation(double distance_x, double distance_y) {
        int new_x = (int) Math.round(originalShape.getX()-distance_x);
        int new_y = (int) Math.round(originalShape.getY()-distance_y);
        originalShape.setFrame(new_x, new_y, originalShape.getWidth(), originalShape.getHeight());
        
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
        originalShape.setFrame(x, y, width, height);
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
        return (int) originalShape.getX();
    }

    @Override
    public int getY() {
        return (int)originalShape.getY();
    }

    @Override
    public int getWidth() {
        return (int) originalShape.getBounds2D().getWidth();
    }

    @Override
    public int getHeight() {
        return (int) originalShape.getBounds2D().getHeight();
    }

    @Override
    public String getName() {
        return "DraggingShape"+id;
    }

    @Override
    public void setName(String name) {
    }

}
