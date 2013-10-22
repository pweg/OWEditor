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
public class mlRotationStrategy implements mlMouseStrategy{

    private Rectangle visiRect = null;  
    private GUIController controller = null;
    private Point startingPoint = null;
    
    public mlRotationStrategy(GUIController contr, Point startingPoint){
        this.controller = contr;
        this.startingPoint = startingPoint;
    }
    
    @Override
    public void mousePressed(Point p) {
  
    }

    @Override
    public void mouseReleased(Point p) {
         
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
