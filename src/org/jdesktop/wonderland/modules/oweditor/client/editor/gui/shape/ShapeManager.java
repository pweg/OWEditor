package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.TranslatedObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUIController;
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
    private ArrayList<ShapeObject> avatarShapes = null;
        
    private ShapeObjectSelectionRect selectionRectangle = null;
    private boolean showDraggingShapes = false;
    private ShapeTranslationManager stm = null;
    
    private static final Logger LOGGER =
            Logger.getLogger(WorldBuilder.class.getName());
    private InternalShapeMediatorInterface smi = null;
    
    
    /**
     * Creates a new ShapeManager instance.
     */
    public ShapeManager(InternalShapeMediatorInterface smi){
        factory = new ShapeFactory();
        
        shapes = new ArrayList<ShapeObject>();
        avatarShapes = new ArrayList<ShapeObject>();
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
        ShapeObject s = null;
        
        s = getNormalShape(id);
        if(s == null)
            s = getAvatarShape(id);
        
        return s;
    }
    
    /**
     * Gets a specific shape from the normal shape pool.
     * 
     * @param id the shape id.
     * @return ShapeObject, or null if id is not found.
     */
    public ShapeObject getNormalShape(long id){
        for(ShapeObject shape : shapes){
            if(shape.getID() == id)
                return shape;
        }
        return null;
    }
    
    /**
     * Gets a specific avatar shape from the avatar shape pool.
     * 
     * @param id the shape id.
     * @return ShapeObject, or null if id is not found.
     */
    private ShapeObject getAvatarShape(long id){
        for(ShapeObject shape : avatarShapes){
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
            shape.paintOriginal(g2, at);
        }
        
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
        
        for(ShapeObject shape : shapes){  
            shape.paintName(g2, at, scale);
        }
        
        for(ShapeObject shape : smi.getDraggingShapes()){  
            shape.paintOriginal(g2, at);
            
        }

        if(selectionRectangle != null)
            selectionRectangle.paintOriginal(g2);
    }
    
    public ShapeFactory getFactory(){
        return factory;
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
    public void getDataUpdate(TranslatedObjectInterface dataObject) {
        
        if(dataObject == null)
            return;
        
        ShapeObject shape = null;
        long id = dataObject.getID();
        
        if(dataObject.getType() == DataObjectInterface.AVATAR){
            shape = getAvatarShape(id);
        }else{
            shape = getNormalShape(id);
        }
        
        if(shape == null){
            getCreateUpdate(dataObject);
            return;
        }
        
        shape.setName(dataObject.getName());
        shape.setLocation(dataObject.getX(), dataObject.getY());
        shape.setRotation(dataObject.getRotation());
    }
    
    public void translateShape(long id, int x, int y){
        ShapeObject shape = getShape(id);
        
        shape.setLocation(x, y);
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
    public void getCreateUpdate(TranslatedObjectInterface dataObject){
        
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
     * Creates the dragging shapes for a given shape object.
     * 
     * @param shape a shape object, which is dragged.
     */
    public ShapeObject builtDraggingShape(ShapeObject shape) {
                    
        int x = shape.getX();
        int y = shape.getY();
        int width = shape.getWidth();
        int height = shape.getHeight();
        long id = shape.getID();
        String name = shape.getName();
        double rotation = shape.getRotation();
             
        ShapeObject newShape = null;
        
        if(shape instanceof ShapeObjectRectangle){
            newShape = factory.createShapeObject(ShapeFactory.DRAGGINGRECTANGLE, x,y,
                    width,height, id, name, rotation);
        }else if(shape instanceof ShapeObjectEllipse){
            newShape = factory.createShapeObject(ShapeFactory.DRAGGINGRELLIPSE, x,y,
                    width,height, id, name, rotation);
        }
        return newShape;
    }
    
    public ArrayList<ShapeObject> builtDraggingShapes(ArrayList<ShapeObject> shapes){
        ArrayList<ShapeObject> list = new ArrayList<ShapeObject>();
        
        for(ShapeObject shape : shapes){
            list.add(builtDraggingShape(shape));
        }
        return list;
    }
    
}
