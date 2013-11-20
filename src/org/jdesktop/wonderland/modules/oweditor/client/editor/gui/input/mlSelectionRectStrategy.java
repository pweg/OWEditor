package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Point;

/**
 * This mouse listener strategy is used, when a selection rectangle
 * should be created and NO other key is pressed.
 * 
 * @author Patrick
 *
 */
public class mlSelectionRectStrategy implements mlMouseStrategy{
        
    private InputController controller = null;
    private boolean selectionRect = false;
    
    private Point start = new Point();
    
    mlSelectionRectStrategy(InputController contr){
        controller = contr;
    }

    @Override
    public void mousePressed(Point p) {
        
        controller.shape.clearCurSelection();
            
        start = p;
        selectionRect = true;
            
        controller.frame.repaint();
        
    }

    @Override
    public void mouseReleased(Point p) {
        if(selectionRect != false){
               selectionRect = false;

               controller.shape.selectionRectFinished();
               controller.frame.repaint();
           }
    }

    @Override
    public void mouseDragged(Point p) {
         if(selectionRect != false){
                Point end = p;
               
                controller.shape.selectionRectUpdate(start, end);
                controller.frame.repaint();
            }
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub
        
    }

}
