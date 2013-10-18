package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;

/**
 * This mouse listener strategy is used, when a selection rectangle
 * should be created and NO other key is pressed.
 * 
 * @author Patrick
 *
 */
public class mlSelectionRectStrategy implements mlMouseStrategy{
        
    private GUIController controller = null;
    private boolean selectionRect = false;
    
    private Point start = new Point();
    
    mlSelectionRectStrategy(GUIController contr){
        controller = contr;
    }

    @Override
    public void mousePressed(Point p) {
        
        controller.esmi.clearCurSelection();
            
        start = p;
        selectionRect = true;
            
        controller.drawingPan.repaint();
        
    }

    @Override
    public void mouseReleased(Point p) {
        if(selectionRect != false){
               selectionRect = false;
               
               controller.esmi.selectionRectReleased();
               
               controller.esmi.removeSelectionRect();
               controller.drawingPan.repaint();
           }
    }

    @Override
    public void mouseDragged(Point p) {
         if(selectionRect != false){
                Point end = p;
               
                controller.esmi.resizeSelectionRect(start, end);
                controller.drawingPan.repaint();
            }
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub
        
    }

}
