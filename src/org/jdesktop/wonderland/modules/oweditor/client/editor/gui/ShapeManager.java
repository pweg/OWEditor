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
	
    private ArrayList<ShapeObject> shapes = null;
    private ArrayList<ShapeObject> updateShapes = null;
    private ArrayList<ShapeObject> movingShapes = null;
    private ArrayList<ShapeObject> avatarShapes = null;
    
    private ShapeObjectSelectionRect selectionRectangle = null;
    private boolean showDraggingShapes = false;
    private static final Logger LOGGER =
            Logger.getLogger(WorldBuilder.class.getName());
    
    /**
     * Creates a new ShapeManager instance.
     */
    public ShapeManager(){
        shapes = new ArrayList<ShapeObject>();
        updateShapes = new ArrayList<ShapeObject>();
        movingShapes = new ArrayList<ShapeObject>();
        avatarShapes = new ArrayList<ShapeObject>();
    }
    
    /**
     * Creates a new Rectangle ShapeObject.
     * 
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     * @param id the id for the rectangle, which has to be the same id as in the virtual world.
     */
    public ShapeObject createRectangle(int x, int y, int width, int height, long id, String name){
         ShapeObject shape = new ShapeObjectRectangle(x,y,width, height, id, name);
         
         shapes.add(shape);
         return shape;
    }
    
    public SimpleShapeObject createAvatar(int x, int y, int width, int height, long id){
    	ShapeObject shape = new ShapeObjectAvatar(id, x,y,width, height);
    	
    	avatarShapes.add(shape);
    	return shape;
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
    
    /**
     * Searches for shape objects which are enclosed by the 
     * selection rectangle. 
     * 
     * @return an arraylist with shape objects. If
     * no object is in the selection rectangle, it returns an empty list.
     */
    public ArrayList<ShapeObject> getShapesInSelection(){
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
            for(ShapeObject shape : movingShapes){  
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
    public void translateDraggingShapes(ArrayList<ShapeObject> selectedShapes, double distance_x, double distance_y){
        showDraggingShapes = true;
        
        if(movingShapes.isEmpty()){
            for(ShapeObject shape: selectedShapes){
                createDraggingShape(shape);
            }
        }
        
        for(ShapeObject selShape : movingShapes){
            selShape.setTranslation(distance_x, distance_y);
        }
    }

    /**
     * Creates the dragging shapes for a given shape object.
     * 
     * @param shape a shape object, which is dragged.
     */
    private void createDraggingShape(ShapeObject shape) {
                    
        int x = shape.getX();
        int y = shape.getY();
        int width = shape.getWidth();
        int height = shape.getHeight();
        long id = shape.getID();
        
        if(shape instanceof ShapeObjectRectangle){
            ShapeObjectDraggingRect newShape = new ShapeObjectDraggingRect(x,y,width,height, id);
            movingShapes.add(newShape); 
        }
    }

    /**
     * Removes all dragging shapes.
     */
    public void clearDraggingShapes() {
        movingShapes.clear();
        showDraggingShapes = false;
    }

    /**
     * Saves all dragging shapes for future update.
     * This is done, if the moving was successful.
     */
    public void saveDraggingShapes() {
        for(ShapeObject shape : movingShapes){
            updateShapes.add(shape);
        }
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
     * Gets an update for a shape from the data package for the given id.
     * 
     * @param dataObject which is the updated object.
     */
    public void setDataUpdate(DataObjectInterface dataObject) {
        
        if(dataObject == null)
        	return;
        
        ShapeObject shape = null;
        long id = dataObject.getID();
        
        if(dataObject.isAvatar()){
            for(ShapeObject s : avatarShapes){
                 if(s.getID() == id){
                    shape = s;
                    break;
                }
            }
            if(shape == null){
                int x = dataObject.getX();
                int y = dataObject.getY();
                int width = dataObject.getWidth();
                int height = dataObject.getHeight();
        	createAvatar(x, y, width, height, id);
                return;
            }
        }else{
            for(ShapeObject s : shapes){
                if(s.getID() == id){
                    shape = s;
                    break;
                }
            }
            if(shape == null){
                getCreateUpdate(dataObject);
                return;
            }
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
        if(dataObject.isAvatar())
        	createAvatar(x, y, width, height, id);
        else
        	createRectangle( x,  y,  width,  height,  id, name);
    }

    /**
     * Checks for collision when dragging shapes.
     * 
     * @return returns true, if a collision is detected and false otherwise.
     */
    public boolean checkForCollision() {
        
        ArrayList<ShapeObject> shapes2 = new ArrayList<ShapeObject>();
        
        
        for(ShapeObject shape : shapes){
            boolean isMoving = false;
            for(ShapeObject selected : movingShapes){
                if(selected.getID() == shape.getID()){
                    isMoving = true;
                    break;
                }
            }
            if(!isMoving){
                shapes2.add(shape);
            }
        }

        boolean is_collision = false;
        for(ShapeObject selected : movingShapes){
            for(ShapeObject shape : shapes2){
                Area areaA = new Area(shape.getShape());
                areaA.intersect(new Area(selected.getShape()));

                if(selected instanceof ShapeObjectDraggingRect){
                	ShapeObjectDraggingRect r = (ShapeObjectDraggingRect) selected;
	                if(!areaA.isEmpty()){
	                    is_collision = true;
	                    r.setCollision(true);
	                    break;
	                }else{
	                    r.setCollision(false);
	                }
                }
            }
        }
        return is_collision;
    }
    
}
