package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.ITransformedObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUISettings;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.DraggingObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.IToolTip;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.ITransformationBorder;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.ShapeEllipse;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.ShapeObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.ShapeRectangle;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.SimpleShapeObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.stateDraggingShape;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.stateTransformedEdge;

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
    
    //shapes in the foreground
    private ArrayList<ShapeObject> shapes = null;
    //shapes in the background
    private ArrayList<ShapeObject> background = null;
    
    private ArrayList<ShapeObject> avatarShapes = null;
    private ArrayList<DraggingObject> draggingShapes = null;
        
    private SimpleShapeObject selectionRectangle = null;
    private ITransformationBorder border = null;
    
    private AffineTransform at = null;
    
    
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();
    
    private IToolTip nameToolTip = null;

    /**
     * Creates a new ShapeManager instance.
     * @param smi
     */
    public ShapeManager(IInternalMediator smi){
        factory = new ShapeFactory(smi);
        
        shapes = new ArrayList<ShapeObject>();
        background = new ArrayList<ShapeObject>();
        avatarShapes = new ArrayList<ShapeObject>();
        draggingShapes = new ArrayList<DraggingObject>();
    }
    
    /**
     * Returns all created shapes in an ArrayList.
     * 
     * @return Arraylist of shapes.
     */
    public ArrayList<ShapeObject> getShapes(){
        return shapes;
    }
    
    /**
     * Returns all background shapes.
     * 
     * @return Arraylist of shapes.
     */
    public ArrayList<ShapeObject> getBGShapes(){
        return background;
    }
    
    /**
     * Gets a specific shape from every shape pool.
     * 
     * @param id the shape id.
     * @return ShapeObject, or null if id is not found.
     */
    public ShapeObject getShape(long id){
        
        ShapeObject object = null;
        
        readLock.lock();
        try {
            for(ShapeObject shape : shapes){
                if(shape.getID() == id){
                    object = shape;
                    break;
                }
            }
        } finally {
            readLock.unlock();
        }
        
        if(object != null)
            return object;
        
        readLock.lock();
        try {
            for(ShapeObject shape : avatarShapes){
                if(shape.getID() == id){
                    object = shape;
                    break;
                }
            }
        } finally {
            readLock.unlock();
        }
        
        if(object != null)
            return object;
        
        readLock.lock();
        try {
            for(ShapeObject shape : background){
                if(shape.getID() == id){
                    object = shape;
                    break;
                }
            }
        } finally {
            readLock.unlock();
        }
        return object;
    }
    
    /**
     * Creates the selection rectangle object.
     * 
     * @param x x coordinate.
     * @param y y coordinate.
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     */
    public void createSelectionRect(int x, int y, int width, int height){
        selectionRectangle = factory.createSimpleShapeObject(ShapeFactory.RECTANGLE,
                x, y, width, height); 
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
        
        ShapeObject object = null;
        
        readLock.lock();
        try {
            ListIterator<ShapeObject> li = shapes.listIterator(shapes.size());
            
            while(li.hasPrevious()){
                ShapeObject shape_obj = li.previous();
                Shape shape =  shape_obj.getTransformedShape();
                
                if(shape != null && shape.contains(p)) {
                    object = shape_obj;
                    break;
                }
            }
        } finally {
            readLock.unlock();
        }
        
        return object; 
    }
    
    /**
     * Searches for a background shape object, which surrounds a given point.
     * 
     * @param p the point in question.
     * @return ShapeObject, if a shape was found, otherwise null. 
     */
    public ShapeObject getBGShapeSurroundingPoint(Point p){
        
        ShapeObject object = null;
        
        readLock.lock();
        try {
            ListIterator<ShapeObject> li = background.listIterator(background.size());
            
            while(li.hasPrevious()){
                ShapeObject shape_obj = li.previous();
                Shape shape =  shape_obj.getTransformedShape();
                
                if(shape != null && shape.contains(p)) {
                    object = shape_obj;
                    break;
                }
            }
        } finally {
            readLock.unlock();
        }
        
        return object; 
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
    public void drawShapes(Graphics2D g2, AffineTransform at) {
        
        this.at = at;
                      
        ArrayList<ShapeObject> selected = new ArrayList<ShapeObject>();
        
        readLock.lock();
        try {
            for(ShapeObject shape : background){
                shape.paintOriginal(g2, at);
            }
            
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
        } finally {
            readLock.unlock();
        }
           
        for(DraggingObject shape : draggingShapes){  
            shape.paintOriginal(g2, at);
            
        }
        
        if(border != null){            
            border.paintOriginal(g2, at);
        }

        if(selectionRectangle != null)
            selectionRectangle.paintOriginal(g2, at);
        
        if(nameToolTip != null)
            nameToolTip.paint(g2, at);
    }
    
    /**
     * Removes a shape.
     * 
     * @param id the id of the shape which should be removed.
     */
    public void removeShape(long id){
        
        boolean found = false;
        
        writeLock.lock();
        try {
            for(ShapeObject s : shapes){
                if(s.getID() == id){
                    shapes.remove(s);
                    found = true;
                    break;
                }
            }
        } finally {
            writeLock.unlock();
        }
        
        if(!found){
            writeLock.lock();
            try {
                for(ShapeObject s : background){
                    if(s.getID() == id){
                        background.remove(s);
                        break;
                    }
                }
            } finally {
                writeLock.unlock();
            }
        }
    }

    /**
     * Gets an update for a shape from the data package for the given data 
     * object.
     * 
     * @param dataObject which is the updated object.
     */
    public void setDataUpdate(ITransformedObject dataObject) {
        
        if(dataObject == null)
            return;
        
        long id = dataObject.getID();
        
        ShapeObject shape = getShape(id);
        
        if(shape == null){
            createShape(dataObject);
            return;
        }
        
        shape.setName(dataObject.getName());
        shape.setLocation(dataObject.getX(), dataObject.getY(), dataObject.getZf());
        shape.setRotation(dataObject.getRotation());
        shape.setScale(dataObject.getScale());
    }
    
    /**
     * Renames a given shape.
     * 
     * @param id The id of the shape.
     * @param name The new name.
     */
    public void renameShape(long id, String name){
        ShapeObject shape = getShape(id);
        shape.setName(name);
    }
    
    /**
     * Creates a new shape from the data package.
     * 
     * @param dataObject which needs to be created.
     */
    private void createShape(ITransformedObject dataObject){
        
        long id = dataObject.getID();
        int x = dataObject.getX();
        int y = dataObject.getY();
        double z = dataObject.getZf();
        int width = dataObject.getWidth();
        int height = dataObject.getHeight();
        String name = dataObject.getName();
        double rotation = dataObject.getRotation();
        double scale = dataObject.getScale();
        String imgName = dataObject.getImgClass().getName();
        String imgDir = dataObject.getImgClass().getDir();

        if(dataObject.getType() == IDataObject.AVATAR){
            ShapeObject shape = factory.createShapeObject(ShapeFactory.AVATAR, 
                    x, y, z, width, height, id, name, rotation, scale, imgName,
                    imgDir);
            writeLock.lock();
            try {             
                avatarShapes.add(shape);
            } finally {
                writeLock.unlock();
            }
        }else if (dataObject.getType() == IDataObject.RECTANGLE){
            ShapeObject shape = factory.createShapeObject(ShapeFactory.RECTANGLE, 
                    x, y, z, width, height, id, name, rotation, scale, imgName,
                    imgDir);
            addShape(shape);
        }else if (dataObject.getType() == IDataObject.CIRCLE){
            ShapeObject shape = factory.createShapeObject(ShapeFactory.CIRCLE, 
                x, y, z, width, height, id, name, rotation, scale, imgName,
                imgDir);
            addShape(shape);
        }
    }
    
    /**
     * Adds the shape in z order to the shapes list.
     * 
     * @param shape The shape to add.
     */
    public void addShape(ShapeObject shape){
        double z = shape.getZ();
        
        writeLock.lock();
        try {
            boolean found = false;

            //This is for an initial z sorted list.
            //Could be done with a better algorithm though.
            for(int i=0; i<shapes.size();i++){
                if(shapes.get(i).getZ() >= z){
                    shapes.add(i, shape);
                    found = true;
                    break;                        
                }
            }
            if(!found)
                shapes.add(shape);
        } finally {
            writeLock.unlock();
        }
    }
    
    /**
     * Creates a dragging shapes for a given shape object.
     * 
     * @param shape a shape object, which is dragged.
     */
    private DraggingObject createDraggingShape(ShapeObject shape) {
                    
        int x = shape.getX();
        int y = shape.getY();
        int width = shape.getWidth();
        int height = shape.getHeight();
        long id = shape.getID();
        String name = shape.getName();
        double rotation = shape.getRotation();
        double scale = shape.getScale();
             
        DraggingObject newShape = null;
        
        if(shape instanceof ShapeRectangle){
            newShape = factory.createDraggingShapeObject(ShapeFactory.RECTANGLE, x,y,
                    width,height, id, name, rotation, scale, at);
        }else if(shape instanceof ShapeEllipse){
            newShape = factory.createDraggingShapeObject(ShapeFactory.CIRCLE, x,y,
                    width,height, id, name, rotation, scale, at);
        }
        return newShape;
    }
    
    /**
     * Creates a dragging rectangle.
     * 
     * @param width The width.
     * @param height The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param rotation The rotation.
     * @param scale The scale.
     */
    public void createDraggingRect(int width, int height, int x, int y,
            double rotation, double scale){
        
            DraggingObject shape = factory.createDraggingShapeObject(
                    ShapeFactory.RECTANGLE, x, y, 
                    width, height, -1, "", rotation, scale, at);
            draggingShapes.add(shape);
    }
    
    /**
     * Creates a dragging ellipse.
     * 
     * @param width The width.
     * @param height The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param rotation The rotation.
     * @param scale The scale.
     */
    public void createDraggingEllipse(int width, int height, int x, int y,
            double rotation, double scale){
        
            DraggingObject shape = factory.createDraggingShapeObject(ShapeFactory.CIRCLE, x, y, 
                    width, height, -1, "", rotation, scale, at);
            draggingShapes.add(shape);
    }
    
    /**
     * Creates multiple dragging shapes from a given list of 
     * shapes.
     * 
     * @param shapes an arraylist which holds all shapes from 
     * which the dragging shapes will be built
     * 
     */
    public void createDraggingShapes(ArrayList<ShapeObject> shapes){
        draggingShapes.clear();
        
        for(ShapeObject shape : shapes){
            draggingShapes.add(createDraggingShape(shape));
        }
    }
    
    /**
     * Returns all dragging shapes currently available.
     * 
     * @return Arraylist of dragging shapes.
     */
    public ArrayList<DraggingObject> getDraggingShapes(){
        return draggingShapes;
    }

    /**
     * Removes all dragging shapes.
     */
    public void clearDraggingShapes() {
        draggingShapes.clear();
    }
    
    /**
     * Returns the selection rectangle.
     * 
     * @return The rectangle.
     */
    public SimpleShapeObject getSelectionRectangle(){
        return selectionRectangle;
    }
    
    /**
     * Removes the transformation border.
     */
    public void removeBorder(){
        border = null;
    }
    
    /**
     * Creates the transformation border.
     * 
     * @param shapes The shapes, which the border should sorround.
     * @param mode The mode of the border.
     */
    public void createShapeBorder(ArrayList<ShapeObject> shapes, 
            byte mode){
                
        int min_x = Integer.MAX_VALUE;
        int min_y = Integer.MAX_VALUE;
        int max_x = Integer.MIN_VALUE;
        int max_y = Integer.MIN_VALUE;
        
        /*
         * Get the original coordinates and the needed width/height.
         * The max_x/y have to be done via transformed shape, because
         * of rotation and scaling, to get the right size of the objects.
         * The min_x/y are done with original coordinates, because the 
         * border is later translated/scaled into the right place.
         */
        for(DraggingObject shape : draggingShapes){
            
            Rectangle r = shape.getTransformedShape().getBounds();
            stateDraggingShape state = shape.getState();
            
            if(!(state instanceof stateTransformedEdge))
                shape.setState(new stateTransformedEdge());
            
            int s_x = shape.getX();
            int s_y = shape.getY();
            
            if(!(state instanceof stateTransformedEdge))
                shape.setState(state);
            

            if(s_x < min_x)
                min_x = s_x;
            if(s_y < min_y)
                min_y = s_y;
            if(max_x < s_x+r.width)
                max_x = s_x+r.width;
            if(max_y < s_y+r.height)
                max_y = s_y+r.height;
            
        }
        int border_x = min_x;
        int border_y = min_y;
        int width = max_x-min_x;
        int height = max_y-min_y;

        border = factory.createTransformBorder(border_x, border_y, width, height, at,
                mode);
    }
    
    /**
     * Searches whether a point is in the transformation border or not.
     * 
     * @param p The point in question.
     * @return True, if it is, false otherwise.
     */
    public byte isInBorderShapes(Point p){
        return border.checkShapes(p);
    }
    
    /**
     * Returns the transformation border.
     * 
     * @return The border.
     */
    public ITransformationBorder getShapeBorder(){
        return border;
    }
    
    /**
     * Changes the state of the draggingshapes. The states are
     * used for coordinate calculation.
     * 
     * @param state The state which all shapes have to be set.
     */
    public void setShapeStates(stateDraggingShape state){
        for(DraggingObject shape : draggingShapes){
            shape.setState(state);
        }
    }

    /**
     * Sets the tooltip for the shape names, which is shown
     * when a name is abbreviated.
     * 
     * @param p The point for the tooltip to appear.
     * @param name The name of the tooltip.
     * @return true, if the tooltip has changed and a repaint
     * has to be made, false otherwise.
     */
    public boolean setNameToolTip(Point p, String name) {
        
        if(name == null && nameToolTip != null){
            nameToolTip = null;
            return true;
        }else if(name == null && nameToolTip == null){
            return false;
        }
        
        if(nameToolTip == null)
            nameToolTip = factory.createToolTip(p, name);
        else if(nameToolTip.getText().equals(name)){
            nameToolTip.setCoordinates(p);
        }else{
            nameToolTip.set(p, name);
        }
        return true;
    }

    /**
     * Removes the name tooltip.
     * 
     * @return true, if the tooltip has been removed, 
     * false if there was no tooltip to remove.
     */
    public boolean removeNameTooltip() {
        if(nameToolTip == null)
            return false;
        else{
            nameToolTip = null;
            return true;
        }
    }
    
    /**
     * Removes an object from the background.
     * 
     * @param id The id of the object.
     */
    private void removeBackground(long id){
        
        for(int i = 0;i < background.size(); i++){
            if(background.get(i).getID() == id){
                background.remove(i);
                return;
            }
        }
    }
    
    private void addBackground(ShapeObject shape){
        double z = shape.getZ();
        
        int i = 0;
        for(ShapeObject s : background){
            if(s.getZ() > z){
                break;
            }
            i++;
        }
        background.add(i, shape);
    }
    
    /**
     * Sends a shape to the background or the foreground.
     * 
     * @param id The id of the shape.
     * @param b If true, the shape becomes background,
     * if false, it becomes foreground.
     */
    public void setBackground(long id, boolean b){
        
        if(b){
            ShapeObject shape = getShape(id);
            
            if(shape == null)
                return;

            shape.setColor(GUISettings.BGOBJECTCOLOR);
            removeShape(id);
            addBackground(shape);
        }else{
            ShapeObject shape = getShape(id);
            
            if(shape == null)
                return;

            shape.setColor(GUISettings.OBJECTCOLOR);
            removeBackground(id);
            addShape(shape);
        }
    }
}
