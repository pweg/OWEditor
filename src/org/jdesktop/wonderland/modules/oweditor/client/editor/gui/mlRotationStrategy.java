package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;

/**
 * This mouse listener strategy is used for 
 * panning.
 * 
 * @author Patrick
 *
 */
public class mlRotationStrategy implements mlMouseStrategy{

    private GUIController controller = null;
    
    public mlRotationStrategy(GUIController contr){
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
        controller.esmi.rotate(p);
        controller.drawingPan.repaint();
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub
        
    }
    


}
