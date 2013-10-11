package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JPanel;

/**
 * This mouse listener strategy is used for 
 * panning.
 * 
 * @author Patrick
 *
 */
public class mlPanStrategy implements mlMouseStrategy{

    private Rectangle visiRect = null;  
    private JPanel panel = null;
    private Point pressed = null;  
    private Point here = null;  
    private boolean drag = false;
    
    public mlPanStrategy(JPanel panel){
        this.panel = panel;
    }
    
    @Override
    public void mousePressed(Point p) {
         pressed = p;  
         visiRect = panel.getVisibleRect();
         drag = true;
    }

    @Override
    public void mouseReleased(Point p) {
         drag = false;
    }

    @Override
    public void mouseDragged(Point p) {
        if(drag){
            here = p;  
            visiRect.x += (pressed.x - here.x);  
            visiRect.y += (pressed.y - here.y);  
            panel.scrollRectToVisible(visiRect); 
        }
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub
        
    }


}
