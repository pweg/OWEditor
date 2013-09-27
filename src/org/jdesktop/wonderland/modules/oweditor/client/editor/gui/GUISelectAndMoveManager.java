package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;

public class GUISelectAndMoveManager {
    
    private ArrayList<ShapeObject> selectedShapes = null;
    private GUIControler controler = null;

    
    private ListenerDragAndDrop dragListener = null;
    private ListenerSelection selectionListener = null;
    
    public GUISelectAndMoveManager(GUIControler contr){
        selectedShapes = new ArrayList<ShapeObject>();        
        controler = contr;
        
        dragListener = new ListenerDragAndDrop(controler);
        selectionListener = new ListenerSelection(controler);
        
        WindowDrawingPanel drawingPan = controler.getDrawingPanel();
        
        drawingPan.addMouseListener(dragListener);
        drawingPan.addMouseMotionListener(dragListener);
        drawingPan.addMouseListener(selectionListener);
        drawingPan.addMouseMotionListener(selectionListener);
        controler.getFrame().addKeyListener(selectionListener);
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

    public void resizeSelectionRect(Point start, Point end, boolean createShape) {

        int sx = start.x;
        int sy = start.y;
        int ex = end.x;
        int ey = end.y;
        
        int width = 0;
        int height = 0;
        int x = 0;
        int y = 0;

        if(sx > ex){
            width = sx-ex;
            x=ex;
        }else{
            width = ex-sx;
            x=sx;
        }
        
        if(sy > ey){
            height = sy-ey;
            y=ey;
        }else{
            height = ey-sy;
            y=sy;
        }
          
        if(createShape)
            controler.getShapeManager().createSelectionRect(x, y, width, height);
        else
            controler.getShapeManager().setSelectionRect(x,y,width, height);
        
        
        
    }

    public void selectionPressReleased() {
        
        ArrayList<ShapeObject> list = controler.getShapeManager().getShapesInSelection();
        
        for(ShapeObject shape : list){
            setSelected(shape, true);
        }
        
    }
    

}
