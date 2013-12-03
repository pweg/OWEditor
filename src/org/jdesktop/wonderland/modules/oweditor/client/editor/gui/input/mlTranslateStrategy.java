package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Point;

/**
 * This mouse listener strategy is used for dragging
 * objects and creates the dragging shapes in the 
 * ShapeManager.
 * 
 * @author Patrick
 *
 */
public class mlTranslateStrategy implements mlMouseStrategy{

    private InputController controller;
    private Point start = new Point();
    private boolean dragging = false;
    
    public mlTranslateStrategy(InputController contr){
        controller = contr;
    }
    
    /**
     * Selects a shape, if the mouse pointer is in one
     * and activates dragging.
     */
    @Override
    public void mousePressed(Point p) {
        
        controller.shape.translateIntialize(p);
        
        if(controller.shape.isMouseInObject(p)){
            start.x = p.x;
            start.y = p.y;
            dragging = true;
        }
        controller.frame.repaint();
    }

    @Override
    public void mouseReleased(Point p) {
        dragging = false;
        controller.shape.translateFinished();
        controller.frame.repaint();
    }

    @Override
    public void mouseDragged(Point p) {
        if(dragging) {
            
            controller.shape.translate(p.x, p.y, start);
            start.x = p.x;
            start.y = p.y;
        }
        controller.frame.repaint();
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub
        
    }

}
