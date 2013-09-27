package org.jdesktop.wonderland.modules.oweditor.client.adapter;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

class PanListener implements MouseListener,MouseMotionListener {  
    
    Rectangle visiRect = null;  
    Point pressed = null;  
    Point here = null;  
    zoom2 pan = null;
      
    public PanListener(zoom2 z) {  
        pan = z;
    }  
      
    public void paintComponent(Graphics g) { 
        g.clearRect(0, 0, pan.getWidth(),pan.getHeight());  
        g.drawRect(20,20,100,100); 
    }  
      
    public void mouseDragged(MouseEvent e) {  
        here = e.getPoint();  
        visiRect.x += (pressed.x - here.x);  
        visiRect.y += (pressed.y - here.y);  
        pan.scrollRectToVisible(visiRect); 
    }  
      
    public void mouseMoved(MouseEvent e) { ;; }  
    public void mouseClicked(MouseEvent e) { ;; }  
    public void mouseEntered(MouseEvent e) { ;; }  
    public void mouseExited(MouseEvent e) { ;; }  
      
    public void mousePressed(MouseEvent e) {  
        pressed = e.getPoint();  
        visiRect = pan.getVisibleRect(); 
    }  
      
    public void mouseReleased(MouseEvent e) { ;;  }  
}  
