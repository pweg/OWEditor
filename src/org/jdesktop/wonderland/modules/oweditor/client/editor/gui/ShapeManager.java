package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collection;

import org.jdesktop.wonderland.modules.oweditor.client.data.DataManagerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.data.DataObjectInterface;

public class ShapeManager {

    ArrayList<ShapeObject> shapes = null;
    ArrayList<ShapeObject> updateShapes = null;
    ArrayList<ShapeObjectDraggingRect> movingShapes = null;
    
    private ShapeObjectSelectionRect selectionRectangle = null;
    private boolean showDraggingShapes = false;
    private DataManagerInterface dm = null;
    
    public ShapeManager(){
        shapes = new ArrayList<ShapeObject>();
        updateShapes = new ArrayList<ShapeObject>();
        movingShapes = new ArrayList<ShapeObjectDraggingRect>();
    }
    
    
    
    public ShapeObject createRectangle(int x, int y, int width, int height, int id){
         ShapeObject shape = new ShapeObjectRectangle(x,y,width, height, id);
         
         shapes.add(shape);
         return shape;
    }
    
    
    public ArrayList<ShapeObject> getShapes(){
        return shapes;
    }
    
    public ShapeObject getShape(int id){
        for(ShapeObject shape : shapes){
            if(shape.getID() == id)
                return shape;
        }
        return null;
    }
    
    
    public void setSelectionRect(int x, int y, int width, int height){
        if (selectionRectangle == null)
            selectionRectangle = new ShapeObjectSelectionRect(x,y,width,height);        
        selectionRectangle.set(x, y, width, height);
    }
    

    public void removeSelectionRect(){
        selectionRectangle = null;
    }
    
    public ShapeObject getShapeSuroundingPoint(Point p){
        
        for(ShapeObject shape_obj : shapes){
            
            Shape shape = shape_obj.getTransformedShape();
            
            if(shape.contains(p)) {
                return shape_obj;
            }
        }
        return null;
        
    }
    
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
    
    public void drawShapes(Graphics2D g2, AffineTransform at){
                
        for(ShapeObject shape : shapes){  
            shape.paintOriginal(g2, at);
        }
        
        if(showDraggingShapes){
            for(ShapeObject shape : movingShapes){  
                shape.paintOriginal(g2, at);
            }
        }

        if(selectionRectangle != null)
            selectionRectangle.paintOriginal(g2);
        
    }
    
    public void translateMovingShapes(ArrayList<ShapeObject> selectedShapes, double distance_x, double distance_y){
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



    private void createDraggingShape(ShapeObject shape) {
                    
        int x = shape.getX();
        int y = shape.getY();
        int width = shape.getWidth();
        int height = shape.getHeight();
        int id = shape.getID();
        
        if(shape instanceof ShapeObjectRectangle){
            ShapeObjectDraggingRect newShape = new ShapeObjectDraggingRect(x,y,width,height, id);
            movingShapes.add(newShape); 
        }
    }
    
    /*
    public void removeDraggingShape(ShapeObject shape){
        System.out.println("remove");
        
        for(ShapeObject s : movingShapes){
            if(s.getID() == shape.getID())
                movingShapes.remove(s);
                break;
        }
    }*/



    public void clearMovingShapes() {
        movingShapes.clear();
        showDraggingShapes = false;
    }


    public void saveMovingShapes() {
        for(ShapeObject shape : movingShapes){
            updateShapes.add(shape);
        }
        
    }



    public ArrayList<ShapeObject> getUpdateShapes() {
        ArrayList<ShapeObject> list = new ArrayList<ShapeObject>();
        list.addAll(updateShapes);
        updateShapes.clear();
        return list;
    }



    public void getAdapterUpdate(int id) {

        if(dm == null)
            return;

        DataObjectInterface so = dm.getObject(id);
        
        ShapeObject shape = null;
        
        for(ShapeObject s : shapes){
            if(s.getID() == id){
                shape = s;
                break;
            }
        }

        if(shape == null){
            getCreateUpdate(id);
            return;
        }
        
        shape.setLocation(so.getX(), so.getY());
        
    }
    
    public void getCreateUpdate(int id){
        
        DataObjectInterface so = dm.getObject(id);
        int x = so.getX();
        int y = so.getY();
        int width = so.getWidth();
        int height = so.getHeight();
   
        createRectangle( x,  y,  width,  height,  id);
    }



    public void setDataManager(DataManagerInterface dm) {
        this.dm = dm;
        
    }
    
}
