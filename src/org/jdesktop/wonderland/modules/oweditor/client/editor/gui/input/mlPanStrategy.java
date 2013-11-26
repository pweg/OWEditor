package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.FrameToInputInterface;

/**
 * This mouse listener strategy is used for 
 * panning.
 * 
 * @author Patrick
 *
 */
public class mlPanStrategy implements mlMouseStrategy{

    private Rectangle visiRect = null;  
    private FrameToInputInterface effi = null;
    private Point pressed = null;  
    private Point here = null;  
    private boolean drag = false;
    
    public mlPanStrategy(FrameToInputInterface effi){
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

            Dimension size = effi.getPanelSize();

            visiRect.x = Math.max(0, visiRect.x);
            visiRect.y = Math.max(0, visiRect.y);
            
            visiRect.x = Math.min(size.width, visiRect.x);
            visiRect.y = Math.min(size.height, visiRect.y);
            
            effi.scrollRectToVisible(visiRect); 
        }
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub
        
    }


}
