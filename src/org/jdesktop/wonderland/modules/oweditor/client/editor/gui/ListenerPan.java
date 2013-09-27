package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


class ListenerPan implements MouseListener,MouseMotionListener {  
    
    private Rectangle visiRect = null;  
    private Point pressed = null;  
    private Point here = null;  
    private WindowDrawingPanel pan = null;
    private boolean drag = false;
      
    public ListenerPan(WindowDrawingPanel z) {  
        pan = z;
    }  
      
    /*public void paintComponent(Graphics g) { 
        if(drag){
            g.clearRect(0, 0, pan.getWidth(),pan.getHeight());  
            g.drawRect(20,20,100,100); 
        }
    } */ 
      
    public void mouseDragged(MouseEvent e) {  
        if(drag){
            here = e.getPoint();  
            visiRect.x += (pressed.x - here.x);  
            visiRect.y += (pressed.y - here.y);  
            pan.scrollRectToVisible(visiRect); 
        }
    }  
      
    public void mouseMoved(MouseEvent e) { ;; }  
    public void mouseClicked(MouseEvent e) { ;; }  
    public void mouseEntered(MouseEvent e) { ;; }  
    public void mouseExited(MouseEvent e) { ;; }  
      
    public void mousePressed(MouseEvent e) { 
        if(e.getButton() ==  MouseEvent.BUTTON2 || e.getButton() ==  MouseEvent.BUTTON3){
            pressed = e.getPoint();  
            visiRect = pan.getVisibleRect();
            drag = true;
            
        }
    }  
      
    public void mouseReleased(MouseEvent e) { 
        drag = false;
    }  
}  
