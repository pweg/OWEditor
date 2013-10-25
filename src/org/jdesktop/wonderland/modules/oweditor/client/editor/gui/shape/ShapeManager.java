package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.TranslatedObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.WorldBuilder;

/**
 * This class is used for creating and managing all shapes that 
 * are later painted.
 * It stores all shapes, like shape objects, avatar shapes,
 * the selection rectangle and dragging shapes.
 * 
 * @author Patrick
 *
 */
public class ShapeManager {
    
    private ShapeFactory factory = null;
    
    private ArrayList<ShapeObject> shapes = null;
    private ArrayList<ShapeObject> avatarShapes = null;
    private ArrayList<ShapeDraggingObject> draggingShapes = null;
        
    private ShapeObjectSelectionRect selectionRectangle = null;
    private ShapeObjectBorder border = null;
    
    private AffineTransform at = null;
    
    private static final Logger LOGGER =
            Logger.getLogger(WorldBuilder.class.getName());
    private InternalShapeMediatorInterface smi = null;
    
    
    /**
     * Creates a new ShapeManager instance.
     */
    public ShapeManager(InternalShapeMediatorInterface smi){
        factory = new ShapeFactory(smi);
        
        shapes = new ArrayList<ShapeObject>();
        avatarShapes = new ArrayList<ShapeObject>();
        draggingShapes = new ArrayList<ShapeDraggingObject>();
        
        this.smi = smi;
    }
    
    /**
     * Returns all created shapes in an ArrayList.
     */
    public ArrayList<ShapeObject> getShapes(){
        return shapes;
    }
    
    /**
     * Gets a specific shape from every shape pool.
     * 
     * @param id the shape id.
     * @return ShapeObject, or null if id is not found.
     */
    public ShapeObject getShape(long id){
        
        for(ShapeObject shape : shapes){
            if(shape.getID() == id)
                return shape;
        }
        
        for(ShapeObject shape : avatarShapes){
            if(shape.getID() == id)
                return shape;
        }
        
        return null;
    }
    
    /**
     * Creates the selection rectangle object if it is null,
     * or sets new coordinates/width/height for it otherwise.
     * 
     * @param x x coordinate.
     * @param y y coordinate.
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     */
    public void createSelectionRect(int x, int y, int width, int height){
        selectionRectangle = new ShapeObjectSelectionRect(x,y,width,height); 
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
        
        this.at = at;
                      
        ArrayList<ShapeObject> selected = new ArrayList<ShapeObject>();
        
        for(ShapeObject shape : shapes){  
            if(shape.isSelected())
                selected.add(shape);
            else
                shape.paintOriginal(g2, at);
        }
        
        for(ShapeObject shape : selected){
            shape.paintOriginal(g2, at);
        }
        
        for(ShapeObject shape : avatarShapes){
            shape.paintOriginal(g2, at);
        }
        
        for(ShapeObject shape : shapes){  
            shape.paintName(g2, at, scale);
        }
                
        for(ShapeDraggingObject shape : draggingShapes){  
            shape.paintOriginal(g2, at);
            
        }
        
        if(border != null){            
            border.paintOriginal(g2, at);
        }

        if(selectionRectangle != null)
            selectionRectangle.paintOriginal(g2);
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
     * Gets an update for a shape from the data package for the given data 
     * object.
     * 
     * @param dataObject which is the updated object.
     */
    public void setDataUpdate(TranslatedObjectInterface dataObject) {
        
        if(dataObject == null)
            return;
        
        long id = dataObject.getID();
        
        ShapeObject shape = getShape(id);
        
        if(shape == null){
            createShape(dataObject);
            return;
        }
        
        shape.setName(dataObject.getName());
        shape.setLocation(dataObject.getX(), dataObject.getY());
        shape.setRotation(dataObject.getRotation());
    }
    
    public void changeShape(long id, int x, int y, String name){
        ShapeObject shape = getShape(id);
        
        shape.setName(name);
        shape.setLocation(x, y);
    }
    
    /**
     * Creates a new shape from the data package.
     * 
     * @param dataObject which needs to be created.
     */
    private void createShape(TranslatedObjectInterface dataObject){
        
        long id = dataObject.getID();
        int x = dataObject.getX();
        int y = dataObject.getY();
        int width = dataObject.getWidth();
        int height = dataObject.getHeight();
        String name = dataObject.getName();
        double rotation = dataObject.getRotation();
        
        if(name.length() > 20){
            name = name.substring(0, 18)+ "...";
        }
        if(dataObject.getType() == DataObjectInterface.AVATAR){
            ShapeObject shape = factory.createShapeObject(ShapeFactory.AVATAR, 
                    x, y, width, height, id, name, rotation);
            avatarShapes.add(shape);
        }else{
            ShapeObject shape = factory.createShapeObject(ShapeFactory.RECTANGLE, 
                    x, y, width, height, id, name, rotation);
            shapes.add(shape);
        }
    }
    
    /**
     * Creates a dragging shapes for a given shape object.
     * 
     * @param shape a shape object, which is dragged.
     */
    private ShapeDraggingObject createDraggingShape(ShapeObject shape) {
                    
        int x = shape.getX();
        int y = shape.getY();
        int width = shape.getWidth();
        int height = shape.getHeight();
        long id = shape.getID();
        String name = shape.getName();
        double rotation = shape.getRotation();
             
        ShapeDraggingObject newShape = null;
        
        if(shape instanceof ShapeObjectRectangle){
            newShape = factory.createDraggingShapeObject(ShapeFactory.RECTANGLE, x,y,
                    width,height, id, name, rotation, at);
        }else if(shape instanceof ShapeObjectEllipse){
            newShape = factory.createDraggingShapeObject(ShapeFactory.CIRCLE, x,y,
                    width,height, id, name, rotation, at);
        }
        return newShape;
    }
    
    /**
     * Creates multiple dragging shapes from a given list of 
     * shapes.
     * 
     * @param shapes an arraylist which holds all shapes from 
     * which the dragging shapes will be built
     * @return an arraylist of draggingshapes, which werer built
     */
    public void createDraggingShapes(ArrayList<ShapeObject> shapes){
        draggingShapes.clear();
        
        for(ShapeObject shape : shapes){
            draggingShapes.add(createDraggingShape(shape));
        }
    }
    
    public ArrayList<ShapeDraggingObject> getDraggingShapes(){
        return draggingShapes;
    }

    /**
     * Removes all dragging shapes.
     */
    public void clearDraggingShapes() {
        draggingShapes.clear();
    }
    
    public SimpleShapeObject getSelectionRectangle(){
        return selectionRectangle;
    }
    
    public void removeBorder(){
        border = null;
    }
    
    public void createShapeBorder(double scale, Point coordinates, 
            ArrayList<ShapeObject> shapes){

        int min_x = Integer.MAX_VALUE;
        int max_x = Integer.MIN_VALUE;
        int min_y = Integer.MAX_VALUE;
        int max_y = Integer.MIN_VALUE;
                
        /*
         * Gets the width and heihgt of the selection
         */
        for(ShapeObject shape : shapes){
            Rectangle r = shape.getTransformedShape().getBounds();
            
            int x = (int) Math.round(r.x/scale);
            int y = (int) Math.round(r.y/scale);
            int width = (int) Math.round(r.width/scale);
            int height = (int) Math.round(r.height/scale);

            if(min_x > x)
                min_x = x;
            if(max_x < x+width)
                max_x = x+width;
            if(min_y > y)
                min_y = y;
            if(max_y < y+height)
                max_y = y+height;
        }
        
        int width = max_x-min_x;
        int height = max_y-min_y;

        int x = (int) Math.round(coordinates.x/scale);
        int y = (int) Math.round(coordinates.y/scale);
        border = new ShapeObjectBorder(x, y, width, height, at,
                ShapeObjectBorder.MODECENTER);
    }
    
    public byte isInBorderShapes(Point p){
        return border.checkShapes(p);
    }
    
    public ShapeObjectBorder getShapeBorder(){
        return border;
    }
    
    public void setShapeStates(stateDraggingShape state){
        for(ShapeDraggingObject shape : draggingShapes){
            shape.setState(state);
        }
    }
    
}
