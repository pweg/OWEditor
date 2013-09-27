package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;

public class GUISelectAndMoveManager {
    
    private ArrayList<ShapeObject> selectedShapes = null;
    private GUIControler controler = null;
    
    public GUISelectAndMoveManager(GUIControler contr){
        selectedShapes = new ArrayList<ShapeObject>();
        
        controler = contr;
    }
    
    public void setSelected(ShapeObject shape, boolean selected){
        shape.setSelected(selected);
        
        if(selected){
            selectedShapes.add(shape);
        }else{
            selectedShapes.remove(shape);
        }
    }
    
    public void switchSelection(ShapeObject shape){
        if(shape.isSelected()){
            selectedShapes.remove(shape);           
        }else{
            selectedShapes.add(shape);
        }
        shape.switchSelection();
    }
    
    public void removeCurSelection(){
        
        for(ShapeObject shape : selectedShapes){
            shape.setSelected(false);
        }
        selectedShapes.clear();
    }
    
    public boolean isSomethingSelected(){
        if(selectedShapes.isEmpty())
            return false;
        else
            return true;
    }
    
    
    public void translateShape(int id, int x2, int y2, Point start){
        if(id == -1)
            return;
        
        ShapeObject shape = controler.getShapeManager().getShape(id);
        double scale = controler.getDrawingPanel().getScale();
        
        if(shape == null)
            return;
   

        
        if(shape instanceof ShapeObjectRectangle){
            Rectangle r = (Rectangle) shape.getShape();
            /*System.out.println("scale : " + scale);
            System.out.println("new mouse point: " + x2 + " " + y2);
            System.out.println("coordinats: " + r.x + " " + r.y);
            System.out.println("Distance: "+ distance_x + " " + distance_y +"__ " + distance_x/scale + " "+distance_y/scale);
            System.out.println("New coords: "+ new_x + " " + new_y);
            System.out.println(r.height + " " + r.width);
            System.out.println("-------" );
            */

            int distance_x = start.x - x2;
            int distance_y = start.y - y2;
            
            double distance = start.distance(x2, y2);
            
            distance = distance / scale;

            //int new_x = (int) Math.round(r.x - (distance_x)/scale);
            //int new_y = (int) Math.round(r.y - ;
            
            for(ShapeObject selShape : selectedShapes){
                selShape.setTranslation((distance_x/scale), (distance_y/scale));
            }

            
            
            controler.getDrawingPanel().repaint();
        }
    }

}
