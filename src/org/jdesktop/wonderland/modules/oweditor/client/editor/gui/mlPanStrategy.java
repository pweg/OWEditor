package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.awt.Rectangle;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.ExternalFrameFacadeInterface;

/**
 * This mouse listener strategy is used for 
 * panning.
 * 
 * @author Patrick
 *
 */
public class mlPanStrategy implements mlMouseStrategy{

    private Rectangle visiRect = null;  
    private ExternalFrameFacadeInterface effi = null;
    private Point pressed = null;  
    private Point here = null;  
    private boolean drag = false;
    
    public mlPanStrategy(ExternalFrameFacadeInterface effi){
        this.effi = effi;
    }
    
    @Override
    public void mousePressed(Point p) {
         pressed = p;  
         visiRect = effi.getVisibleRect();
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
            effi.scrollRectToVisible(visiRect); 
        }
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub
        
    }


}
