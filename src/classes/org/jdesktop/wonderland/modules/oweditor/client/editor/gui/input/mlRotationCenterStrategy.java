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

    private MouseAndKeyListener listener;
    private Point start = new Point();
    private boolean dragging = false;
    
    public mlRotationCenterStrategy(MouseAndKeyListener listener){
        this.listener = listener;
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
        listener.graphic.rotationCenterUpdate();
    }

    @Override
    public void mouseDragged(Point p) {
        if(dragging) {
            listener.graphic.rotationCenterTranslate(start, p);
            start.x = p.x;
            start.y = p.y;
        }
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub
        
    }

}
