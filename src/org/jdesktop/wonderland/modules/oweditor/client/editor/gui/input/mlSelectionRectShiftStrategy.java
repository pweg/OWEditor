package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Point;

/**
 * This mouse event strategy is used, when a selection rectangle
 * should be painted AND the shift-key is pressed.
 * 
 * @author Patrick
 *
 */
public class mlSelectionRectShiftStrategy implements mlMouseStrategy{
        
    private InputController controller = null;
    private boolean selectionRect = false;
    
    private Point start = new Point();
    
    mlSelectionRectShiftStrategy(InputController contr){
        controller = contr;
    }

    @Override
    public void mousePressed(Point p) {
            
        start = p;
        selectionRect = true;
            
        controller.frame.repaint();
        
    }

    @Override
    public void mouseReleased(Point p) {
        if(selectionRect != false){
               selectionRect = false;
               
               controller.shape.selectionReleased();
               controller.frame.repaint();
           }
    }

    @Override
    public void mouseDragged(Point p) {
         if(selectionRect != false){
                Point end = p;
               
                controller.shape.selectionUpdate(start, end);
                controller.frame.repaint();
            }
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub
        
    }

}
