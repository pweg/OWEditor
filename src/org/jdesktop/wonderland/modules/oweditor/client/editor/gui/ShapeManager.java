package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class ShapeManager {
    
    ArrayList<ShapeObject> shapes = null;
    
    private ShapeObjectSelectionRect selectionRectangle = null;
    
    public ShapeManager(){
        shapes = new ArrayList<ShapeObject>();
        initShapes();
    }
    
    
    
    public ShapeObject createRectangle(int x, int y, int width, int height, int id){
         ShapeObject shape = new ShapeObjectRectangle(x,y,width, height, id);
         
         shapes.add(shape);
         return shape;
    }
    
    public void createSelectionRect(int x, int y,  int width, int height){
        selectionRectangle = new ShapeObjectSelectionRect(x, y,width,height);
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
    
    private void initShapes() {  
        
        
        ShapeObjectRectangle rec = new ShapeObjectRectangle (160, 160, 70, 70, 0);
        shapes.add( rec );  
        //shapes[1] = new Line2D.Double(w/16, h*15/16, w*15/16, h/16);  
        shapes.add(new ShapeObjectRectangle (400, 400, 200, 200,1));  
    }
    
    public boolean selectionRectExists(){
        
        if(selectionRectangle == null)
            return false;
        else
            return true;
    }
    
    public void setSelectionRect(int x, int y, int width, int height){
        if (selectionRectangle == null)
            return;
        
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

        if(selectionRectangle != null)
            selectionRectangle.paintOriginal(g2);
        
    }
    
}
