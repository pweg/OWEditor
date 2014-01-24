package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Point;

/**
 * This mouse listener strategy is used for 
 * panning.
 * 
 * @author Patrick
 *
 */
public class mlRotationTranslateCenterStrategy implements mlMouseStrategy{

    private InputController controller = null;
    
    public mlRotationTranslateCenterStrategy(InputController contr, Point startingPoint){
        this.controller = contr;
    }
    
    @Override
    public void mousePressed(Point p) {
  
    }

    @Override
    public void mouseReleased(Point p) {
         controller.mkListener.removeStrategy();
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
