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
        
    private MouseAndKeyListener listener = null;
    private boolean selectionRect = false;
    
    private Point start = new Point();
    
    mlSelectionRectStrategy(MouseAndKeyListener listener){
        this.listener = listener;
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
               
               listener.graphic.selectionRectFinished();
               listener.window.selectionChange(listener.graphic.isShapeSelected());
           }
    }

    @Override
    public void mouseDragged(Point p) {
         if(selectionRect != false){
                Point end = p;
               
                listener.graphic.selectionRectUpdate(start, end);
            }
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub
        
    }

}
