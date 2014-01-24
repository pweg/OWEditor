package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Point;

/**
 * This mouse listener strategy is used for 
 * panning.
 * 
 * @author Patrick
 *
 */
public class mlRotationStrategy implements mlMouseStrategy{

    private InputController controller = null;
    
    public mlRotationStrategy(InputController contr){
        this.controller = contr;
    }
    
    @Override
    public void mousePressed(Point p) {
  
    }

    @Override
    public void mouseReleased(Point p) {
         controller.mkListener.removeStrategy();
         controller.graphic.rotationUpdate();
    }

    @Override
    public void mouseDragged(Point p) {
        controller.graphic.rotate(p);
        controller.window.repaint();
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub
        
    }
    


}
