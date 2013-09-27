package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class ListenerSelection implements MouseListener, KeyListener{
    
    private GUIControler controler = null;
    private boolean shiftPressed = false;
    
    public ListenerSelection(GUIControler contr){
        controler = contr;
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(shiftPressed){

            Point p = e.getPoint();
            if(e.getButton() ==  MouseEvent.BUTTON1){
                ArrayList<ShapeObject> shapes = controler.getShapeManager().getShapes();
                
                for(ShapeObject shape_obj : shapes){
                    Shape shape = shape_obj.getTransformedShape();
                    
                    if(shape.contains(p)) {
                        controler.getSelectManager().switchSelection(shape_obj);
                        controler.getDrawingPanel().repaint();
                    }
                }
            }
        }
        else{
            if(e.getButton() ==  MouseEvent.BUTTON1){
                Point p = e.getPoint();
                ArrayList<ShapeObject> shapes = controler.getShapeManager().getShapes();
                
                boolean isInside = false;
                for(ShapeObject shape_obj : shapes){
                    
                    Shape shape = shape_obj.getTransformedShape();
                    
                    if(shape.contains(p)) {
                         isInside = true;
                         if(!shape_obj.isSelected()){
                             controler.getSelectManager().removeCurSelection();
                             controler.getSelectManager().setSelected(shape_obj, true);
                             controler.getDrawingPanel().repaint();
                         }
                        
                    }
                }
                if(!isInside){
                    controler.getSelectManager().removeCurSelection();
                    controler.getDrawingPanel().repaint();
                }
            }
        }
        
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        
       

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
