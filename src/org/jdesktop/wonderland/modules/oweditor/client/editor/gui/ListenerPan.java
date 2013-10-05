package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * This is a simple mouse listener for panning the drawing panel.
 * 
 * @author Patrick
 *
 */
class ListenerPan implements MouseListener,MouseMotionListener {  
    
    private Rectangle visiRect = null;  
    private Point pressed = null;  
    private Point here = null;  
    private WindowDrawingPanel pan = null;
    private boolean drag = false;
      
    public ListenerPan(WindowDrawingPanel drawingPan) {  
        pan = drawingPan;
    }  
      
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
