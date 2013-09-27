package org.jdesktop.wonderland.modules.oweditor.client.adapter;

import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;  
  
public class test3 extends JFrame {   
      
     public test3() {  
           
         super("Pan Test");  
         DrawPanel3 drawing = new DrawPanel3();  
         JScrollPane scroll = new JScrollPane(drawing);  
         setSize(100,100);  
           
         this.add(scroll);  
         setVisible(true);  
     }  
      
  public static void main(String argv[])  {  
    new test3();  
    }  
}  
  
class DrawPanel3 extends JPanel implements MouseListener,MouseMotionListener {  
  
    Rectangle visiRect = null;  
    Point pressed = null;  
    Point here = null;  
      
    public DrawPanel3() {  
        this.setPreferredSize(new Dimension(400,400));  
        this.addMouseListener(this);  
        this.addMouseMotionListener(this);  
    }  
      
    public void paintComponent(Graphics g) {  
        g.clearRect(0, 0, this.getWidth(), this.getHeight());  
        g.drawRect(20,20,100,100);  
    }  
      
    public void mouseDragged(MouseEvent e) {  
          
        here = e.getPoint();  
        visiRect.x += (pressed.x - here.x);  
        visiRect.y += (pressed.y - here.y);  
        this.scrollRectToVisible(visiRect);  
    }  
      
    public void mouseMoved(MouseEvent e) { ;; }  
    public void mouseClicked(MouseEvent e) { ;; }  
    public void mouseEntered(MouseEvent e) { ;; }  
    public void mouseExited(MouseEvent e) { ;; }  
      
    public void mousePressed(MouseEvent e) {  
        pressed = e.getPoint();  
        visiRect = this.getVisibleRect();  
    }  
      
    public void mouseReleased(MouseEvent e) { ;;  }  
}  