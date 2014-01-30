package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Point;


/**
 * This mouse listener strategy is used for dragging
 * the center of the rotation border.
 * 
 * @author Patrick
 *
 */
public class mlRotationCenterStrategy implements mlMouseStrategy{

    private InputController controller;
    private Point start = new Point();
    private boolean dragging = false;
    
    public mlRotationCenterStrategy(InputController contr){
        controller = contr;
    }

    @Override
    public void mousePressed(Point p) {
        start.x = p.x;
        start.y = p.y;
        dragging = true;
    }

    @Override
    public void mouseReleased(Point p) {
        dragging = false;
        controller.graphic.rotationCenterUpdate();
    }

    @Override
    public void mouseDragged(Point p) {
        if(dragging) {
            controller.graphic.rotationCenterTranslate(start, p);
            start.x = p.x;
            start.y = p.y;
        }
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub
        
    }

}
