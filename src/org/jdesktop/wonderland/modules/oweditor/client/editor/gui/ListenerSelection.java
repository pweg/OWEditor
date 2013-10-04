package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.event.MouseInputAdapter;

public class ListenerSelection extends MouseInputAdapter implements KeyListener{
    
    public static final byte NOSELSPAN = 0;
    public static final byte SELFIRST = 1;
    public static final byte SELSPAN = 2;
    
    private GUIControler controler = null;
    private boolean shiftPressed = false;
    private byte selectionRect = NOSELSPAN;
    
    private Point start = new Point();
    
    
    public ListenerSelection(GUIControler contr){
        controler = contr;
    }


   
    public void mousePressed(MouseEvent e) {
        if(shiftPressed){

            Point p = e.getPoint();
            if(e.getButton() ==  MouseEvent.BUTTON1){
                ArrayList<ShapeObject> shapes = controler.sm.getShapes();
                
                for(ShapeObject shape_obj : shapes){
                    Shape shape = shape_obj.getTransformedShape();
                    
                    if(shape.contains(p)) {
                        controler.getSelectManager().switchSelection(shape_obj);
                        controler.drawingPan.repaint();
                    }
                }
            }
        }
        else{
            if(e.getButton() ==  MouseEvent.BUTTON1){
                Point p = e.getPoint();
                ArrayList<ShapeObject> shapes = controler.sm.getShapes();
                
                boolean isInside = false;
                for(ShapeObject shape_obj : shapes){
                    
                    Shape shape = shape_obj.getTransformedShape();
                    
                    if(shape.contains(p)) {
                         isInside = true;
                         if(!shape_obj.isSelected()){
                             controler.getSelectManager().removeCurSelection();
                             controler.getSelectManager().setSelected(shape_obj, true);
                             controler.drawingPan.repaint();
                         }
                        
                    }
                }
                
                if(!isInside){
                    controler.getSelectManager().removeCurSelection();
                    
                    start.x = e.getX();
                    start.y = e.getY();
                    selectionRect = SELFIRST;
                    
                    controler.drawingPan.repaint();
                }
            }
        }
        
    }


    public void mouseDragged(MouseEvent e) {
        
        if(selectionRect != NOSELSPAN){
            
            Point end = new Point(e.getX(), e.getY());
            
           
            controler.getSelectManager().resizeSelectionRect(start, end);
          
            controler.drawingPan.repaint();
        }
    }
   
    public void mouseReleased(MouseEvent arg0) {
        
       if(selectionRect != NOSELSPAN){
           selectionRect = NOSELSPAN;
           
           controler.getSelectManager().selectionPressReleased();
           

           controler.sm.removeSelectionRect();
           controler.drawingPan.repaint();
           
       }

    }
    

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            shiftPressed = true;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            shiftPressed = false;
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
}
