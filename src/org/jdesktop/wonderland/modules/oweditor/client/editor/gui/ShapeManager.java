package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.WorldBuilder;

/**
 * This class is used for creating and managing all shapes.
 * It stores all shapes.
 * 
 * @author Patrick
 *
 */
public class ShapeManager {
    
    private ShapeFactory factory = null;
    
    private ArrayList<ShapeObject> shapes = null;
    private ArrayList<ShapeObject> draggingShapes = null;
    private ArrayList<ShapeObject> avatarShapes = null;
    
    //These are shapes for updates.
    private ArrayList<ShapeObject> updateShapes = null;
    
    private ShapeObjectSelectionRect selectionRectangle = null;
    private boolean showDraggingShapes = false;
    
    private static final Logger LOGGER =
            Logger.getLogger(WorldBuilder.class.getName());
    
    private sDraggingShapeStrategy strategy = null;
    
    /**
     * Creates a new ShapeManager instance.
     */
    public ShapeManager(){
        factory = new ShapeFactory();
        
        shapes = new ArrayList<ShapeObject>();
        updateShapes = new ArrayList<ShapeObject>();
        draggingShapes = new ArrayList<ShapeObject>();
        avatarShapes = new ArrayList<ShapeObject>();
    }
    
    /**
     * Returns all created shapes in an ArrayList.
     */
    public ArrayList<ShapeObject> getShapes(){
        return shapes;
    }
    
    /**
     * Gets a specific shape.
     * 
     * @param id the shape id.
     * @return ShapeObject, or null if id is not found.
     */
    public ShapeObject getShape(long id){
        for(ShapeObject shape : shapes){
            if(shape.getID() == id)
                return shape;
        }
        return null;
    }
    
    /**
     * Creates the selection rectangle object, if it is null
     * and sets new coordinates/width&height for it otherwise.
     * 
     * @param x x coordinate.
     * @param y y coordinate.
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     */
    public void setSelectionRect(int x, int y, int width, int height){
        if (selectionRectangle == null)
            selectionRectangle = new ShapeObjectSelectionRect(x,y,width,height); 
        else
            selectionRectangle.set(x, y, width, height);
    }
    
    /**
     * Clears the selection rectangle. This is used, when the mouse
     * button is released.
     */
    public void removeSelectionRect(){
        selectionRectangle = null;
    }
    
    /**
     * Searches for a shape object, which surrounds a given point.
     * 
     * @param p the point in question.
     * @return ShapeObject, if a shape was found, otherwise null. 
     */
    public ShapeObject getShapeSuroundingPoint(Point p){
        
        for(ShapeObject shape_obj : shapes){
            
            Shape shape = shape_obj.getTransformedShape();
            
            if(shape.contains(p)) {
                return shape_obj;
            }
        }
        return null; 
    }
    

    public boolean isMouseInObject(Point p){

        for(ShapeObject shape_obj : shapes){
            
            Shape shape = shape_obj.getTransformedShape();
            
            if(shape.contains(p)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Searches for shape objects which are enclosed by the 
     * selection rectangle. 
     * 
     * @return an arraylist with shape objects. If
     * no object is in the selection rectangle, it returns an empty list.
     */
    public ArrayList<ShapeObject> getShapesInSelectionRect(){
        ArrayList<ShapeObject> list = new ArrayList<ShapeObject>();
        
        if(selectionRectangle == null)
            return list;
        
        for(ShapeObject shape : shapes){
            Rectangle bounds = shape.getTransformedShape().getBounds();
            
            if(selectionRectangle.getShape().contains(bounds)){
                list.add(shape);
            }
            
        }
        return list;
    }
    
    /**
     * Draws all shapes saved in the ShapeManager.
     * These shapes are normal shapes, the selection rectangle and
     * shapes, which are moved while pressing and holding the left mouse
     * button.
     * 
     * @param g2 a Graphics2D class.
     * @param at an AffineTransform class, used to draw zoomed shapes correctly.
     */
    public void drawShapes(Graphics2D g2, AffineTransform at, double scale){
                
        for(ShapeObject shape : avatarShapes){
            shape.paintOriginal(g2, at, scale);
        }
        
        for(ShapeObject shape : shapes){  
            shape.paintOriginal(g2, at, scale);
        }
        
        if(showDraggingShapes){
            for(ShapeObject shape : draggingShapes){  
                shape.paintOriginal(g2, at, scale);
            }
        }

        if(selectionRectangle != null)
            selectionRectangle.paintOriginal(g2);
    }
    
    /**
     * Translates the dragging shapes(DraggingRectangles), which 
     * are seen, when trying to move objects.
     * 
     * @param selectedShapes all shapes which are selected and should be moved.
     * @param distance_x the x distance between old and new point.
     * @param distance_y the y distance between old and new point.
     */
    public void translateDraggingShapes( double distance_x, double distance_y){
        showDraggingShapes = true;
        
        for(ShapeObject selShape : draggingShapes){
            selShape.setTranslation(distance_x, distance_y);
        }
    }
    
    public void createDraggingShapes(ArrayList<ShapeObject> selectedShapes){
        strategy.createDraggingShapes(selectedShapes);
    }
    
    public boolean isDraggingShapesEmpty(){
        
        if(draggingShapes.size() > 0)
            return false;
        return true;
    }
    
    public void copyToDraggingShapes(ArrayList<ShapeObject> copyShapes){
        for(ShapeObject shape : copyShapes){
            createDraggingShape(shape);
        }
    }

    /**
     * Creates the dragging shapes for a given shape object.
     * 
     * @param shape a shape object, which is dragged.
     */
    protected void createDraggingShape(ShapeObject shape) {
                    
        int x = shape.getX();
        int y = shape.getY();
        int width = shape.getWidth();
        int height = shape.getHeight();
        long id = shape.getID();
        String name = shape.getName();
        
        if(shape instanceof ShapeObjectRectangle){
            ShapeObject newShape = factory.createShapeObject(ShapeFactory.DRAGGINGRECTANGLE, x,y,
                    width,height, id, name);
            draggingShapes.add(newShape); 
        }else if(shape instanceof ShapeObjectEllipse){
            ShapeObject newShape = factory.createShapeObject(ShapeFactory.DRAGGINGRELLIPSE, x,y,
                    width,height, id, name);
            draggingShapes.add(newShape); 
        }
    }

    /**
     * Removes all dragging shapes.
     */
    public void clearDraggingShapes() {
        draggingShapes.clear();
        showDraggingShapes = false;
    }

    /**
     * Saves all dragging shapes for future update.
     * This is done, if the moving was successful.
     */
    public void saveDraggingShapes() {
        for(ShapeObject shape : draggingShapes){
            updateShapes.add(shape);
        }
    }
    
    public ArrayList<ShapeObject> getDraggingShapes(){
        return draggingShapes;
    }
    
    
    /**
     * Returns all shapes, which need to be updated.
     * 
     * @return ArrayList which contains all shapes for future update.
     */
    public ArrayList<ShapeObject> getUpdateShapes() {
        ArrayList<ShapeObject> list = new ArrayList<ShapeObject>();
        list.addAll(updateShapes);
        updateShapes.clear();
        return list;
    }
    
    /**
     * Removes a shape.
     * 
     * @param id the id of the shape which should be removed.
     */
    public void removeShape(long id){
        
        for(ShapeObject s : shapes){
            if(s.getID() == id){
                shapes.remove(s);
                break;
            }
        }
    }

    /**
     * Gets an update for a shape from the data package for the given id.
     * 
     * @param dataObject which is the updated object.
     */
    public void getDataUpdate(DataObjectInterface dataObject) {
        
        if(dataObject == null)
            return;
        
        ShapeObject shape = null;
        long id = dataObject.getID();
        
        if(dataObject.getType() == DataObjectInterface.AVATAR){
            for(ShapeObject s : avatarShapes){
                 if(s.getID() == id){
                    shape = s;
                    break;
                }
            }
        }else{
            for(ShapeObject s : shapes){
                if(s.getID() == id){
                    shape = s;
                    break;
                }
            }
        }
        
        if(shape == null){
            getCreateUpdate(dataObject);
            return;
        }
        
        shape.setName(dataObject.getName());
        shape.setLocation(dataObject.getX(), dataObject.getY());
    }
    
    /**
     * Creates a new shape from the data package.
     * 
     * @param dataObject which needs to be created.
     */
    public void getCreateUpdate(DataObjectInterface dataObject){
        
        long id = dataObject.getID();
        int x = dataObject.getX();
        int y = dataObject.getY();
        int width = dataObject.getWidth();
        int height = dataObject.getHeight();
        String name = dataObject.getName();
        
        if(name.length() > 20){
            name = name.substring(0, 18)+ "...";
        }
        if(dataObject.getType() == DataObjectInterface.AVATAR){
            ShapeObject shape = factory.createShapeObject(ShapeFactory.AVATAR, 
                    x, y, width, height, id, name);
            avatarShapes.add(shape);
        }else{
            ShapeObject shape = factory.createShapeObject(ShapeFactory.RECTANGLE, 
                    x, y, width, height, id, name);
            shapes.add(shape);
        }
    }

    /**
     * Checks for collision when dragging shapes.
     * 
     * @return returns true, if a collision is detected and false otherwise.
     */
    public boolean checkForCollision() {
        if(strategy == null)
            return true;
        
        return strategy.checkForCollision(shapes, draggingShapes);
    }
    
    public void setStrategy(sDraggingShapeStrategy strategy){
        this.strategy = strategy;
    }
    
}
