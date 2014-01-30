package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToInput;

/**
 * This mouse listener strategy is used for 
 * normal panning.
 * 
 * @author Patrick
 *
 */
public class mlPanStrategy implements mlMouseStrategy{

    private Rectangle visiRect = null;  
    private IWindowToInput window = null;
    private Point pressed = null;  
    private Point here = null;  
    private boolean drag = false;
    
    public mlPanStrategy(IWindowToInput effi){
        this.window = effi;
    }
    
    @Override
    public void mousePressed(Point p) {
         pressed = p;  
         visiRect = window.getVisibleRect();
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

            Dimension size = window.getPanelSize();

            visiRect.x = Math.max(0, visiRect.x);
            visiRect.y = Math.max(0, visiRect.y);
            
            visiRect.x = Math.min(size.width, visiRect.x);
            visiRect.y = Math.min(size.height, visiRect.y);
            
            window.scrollRectToVisible(visiRect); 
        }
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub
        
    }


}
