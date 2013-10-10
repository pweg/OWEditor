package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;


public class mlPanStrategy implements MouseStrategy{

    private Rectangle visiRect = null;  
    private JPanel panel = null;
    private Point pressed = null;  
    private Point here = null;  
    private boolean drag = false;
    
    public mlPanStrategy(JPanel panel){
        this.panel = panel;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
         pressed = e.getPoint();  
         visiRect = panel.getVisibleRect();
         drag = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
         drag = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(drag){
            here = e.getPoint();  
            visiRect.x += (pressed.x - here.x);  
            visiRect.y += (pressed.y - here.y);  
            panel.scrollRectToVisible(visiRect); 
        }
    }


}
