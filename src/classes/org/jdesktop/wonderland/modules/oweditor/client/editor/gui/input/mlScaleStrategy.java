package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Point;

/**
 * This state is used for scaling.
 * 
 * @author Patrick
 *
 */
public class mlScaleStrategy implements mlMouseStrategy {
    
    private MouseAndKeyListener listener = null;
    
    public mlScaleStrategy(MouseAndKeyListener listener){
        this.listener = listener;
    }
    @Override
    public void mousePressed(Point p) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(Point p) {
        listener.graphic.scaleUpdate();
    }

    @Override
    public void mouseDragged(Point p) {
        listener.graphic.scale(p);
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub

    }

}
