package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;

/**
 * This mouse listener strategy is used for dragging
 * objects and creates the dragging shapes in the 
 * ShapeManager.
 * 
 * @author Patrick
 *
 */
public class mlDragAndDropStrategy implements mlMouseStrategy{

    private GUIController controller;
    private Point start = new Point();
    private boolean dragging = false;
    
    public mlDragAndDropStrategy(GUIController contr){
        controller = contr;
    }
    
    /**
     * Selects a shape, if the mouse pointer is in one
     * and activates dragging.
     */
    @Override
    public void mousePressed(Point p) {
        
        controller.esmi.translationInitialization(p);
        
        if(controller.esmi.isMouseInObject(p)){
            start.x = p.x;
            start.y = p.y;
            dragging = true;
        }
        controller.drawingPan.repaint();
    }

    @Override
    public void mouseReleased(Point p) {
        dragging = false;
        
        controller.esmi.translationSave();
        controller.drawingPan.repaint();
    }

    @Override
    public void mouseDragged(Point p) {
        if(dragging) {
            
            controller.esmi.translation(p.x, p.y, start);
            start.x = p.x;
            start.y = p.y;
        }
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub
        
    }

}
