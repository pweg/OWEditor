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

    private InputController controller;
    private Point start = new Point();
    private boolean dragging = false;
    
    public mlTranslateDragStrategy(InputController contr){
        controller = contr;
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
        controller.graphic.translateFinished();
    }

    @Override
    public void mouseDragged(Point p) {
        if(dragging) {
            controller.graphic.draggingTranslate(p.x, p.y, start);
            start.x = p.x;
            start.y = p.y;
        }
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub
        
    }

}
