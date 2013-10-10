package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 * This mouse listener strategy is used, when a selection rectangle
 * should be created and NO other key is pressed.
 * 
 * @author Patrick
 *
 */
public class mlSelectionRectStrategy implements MouseStrategy{
        
    private GUIController controller = null;
    private boolean selectionRect = false;
    
    private Point start = new Point();
    
    mlSelectionRectStrategy(GUIController contr){
        controller = contr;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        controller.samm.removeCurSelection();
            
        start.x = e.getX();
        start.y = e.getY();
        selectionRect = true;
            
        controller.drawingPan.repaint();
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(selectionRect != false){
               selectionRect = false;
               
               controller.samm.selectionRectReleased();
               
               controller.sm.removeSelectionRect();
               controller.drawingPan.repaint();
           }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
         if(selectionRect != false){
                Point end = new Point(e.getX(), e.getY());
               
                controller.samm.resizeSelectionRect(start, end);
                controller.drawingPan.repaint();
            }
    }

}
