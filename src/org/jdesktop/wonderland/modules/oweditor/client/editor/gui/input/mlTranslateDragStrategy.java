package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Point;


/**
 * This mouse listener strategy is used for dragging
 * objects.
 *  
 * @author Patrick
 *
 */
public class mlTranslateDragStrategy implements mlMouseStrategy{

    private MouseAndKeyListener listener;
    private Point start = new Point();
    private boolean dragging = false;
    
    public mlTranslateDragStrategy(MouseAndKeyListener listener){
        this.listener = listener;
    }
    
    /**
     * Selects a shape, if the mouse pointer is in one
     * and activates dragging.
     */
    @Override
    public void mousePressed(Point p) {
            start.x = p.x;
            start.y = p.y;
            dragging = true;
    }

    @Override
    public void mouseReleased(Point p) {
        dragging = false;
        listener.graphic.translateFinished();
    }

    @Override
    public void mouseDragged(Point p) {
        if(dragging) {
            listener.graphic.draggingTranslate(p, start);
            start.x = p.x;
            start.y = p.y;
        }
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub
        
    }

}
