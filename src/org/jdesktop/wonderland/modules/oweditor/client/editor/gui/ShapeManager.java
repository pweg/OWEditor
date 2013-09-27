package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.util.ArrayList;

public class ShapeManager {
    
    ArrayList<ShapeObject> shapes = null;
    
    public ShapeManager(){
        shapes = new ArrayList<ShapeObject>();
        initShapes();
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
    
    private void initShapes() {  
        
        
        ShapeObjectRectangle rec = new ShapeObjectRectangle (160, 160, 70, 70, 0);
        shapes.add( rec );  
        //shapes[1] = new Line2D.Double(w/16, h*15/16, w*15/16, h/16);  
        shapes.add(new ShapeObjectRectangle (400, 400, 200, 200,1));  
    }

}
