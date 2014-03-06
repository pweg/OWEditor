package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Point;


/**
 * This mouse event strategy is used, when a selection rectangle
 * should be painted.
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
            
        start = p;
        selectionRect = true;
        
    }

    @Override
    public void mouseReleased(Point p) {
        if(selectionRect != false){
               selectionRect = false;
               
               controller.graphic.selectionRectFinished();
               controller.window.selectionChange(controller.graphic.isShapeSelected());
           }
    }

    @Override
    public void mouseDragged(Point p) {
         if(selectionRect != false){
                Point end = p;
               
                controller.graphic.selectionRectUpdate(start, end);
            }
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub
        
    }

}
