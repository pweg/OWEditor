package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Point;


/**
 * This mouse listener strategy is used for 
 * rotating.
 * 
 * @author Patrick
 *
 */
public class mlRotationStrategy implements mlMouseStrategy{

    private MouseAndKeyListener listener = null;
    
    public mlRotationStrategy(MouseAndKeyListener listener){
        this.listener = listener;
    }
    
    @Override
    public void mousePressed(Point p) {
  
    }

    @Override
    public void mouseReleased(Point p) {
         listener.graphic.rotationUpdate();
    }

    @Override
    public void mouseDragged(Point p) {
        listener.graphic.rotate(p);
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub
        
    }
    


}
