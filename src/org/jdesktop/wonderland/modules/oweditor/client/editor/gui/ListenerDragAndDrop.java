package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.event.MouseInputAdapter;


public class ListenerDragAndDrop extends MouseInputAdapter{

    private GUIControler controler;
    private Point start = new Point();
    private boolean dragging = false;
    private int curShape = -1;
 
    public ListenerDragAndDrop(GUIControler contr) {
        controler = contr;
    }
    
 
    public void mousePressed(MouseEvent e) {
        if(e.getButton() ==  MouseEvent.BUTTON1){
            Point p = e.getPoint();
            ArrayList<ShapeObject> shapes = controler.getShapeManager().getShapes();
            
            int i =0;
            for(ShapeObject shape_obj : shapes){
                
                Shape shape = shape_obj.getTransformedShape();
                
                if(shape.contains(p)) {
                    start.x = p.x;
                    start.y = p.y;
                    dragging = true;
                    curShape = i;
                }
                i++;
            }
        }
    }
 
    public void mouseReleased(MouseEvent e) {
        dragging = false;
    }
 
    public void mouseDragged(MouseEvent e) {
        if(dragging) {
            controler.getSelectManager().translateShape(curShape, e.getX(), e.getY(), start);
            start.x = e.getX();
            start.y = e.getY();
        }
    }
}
