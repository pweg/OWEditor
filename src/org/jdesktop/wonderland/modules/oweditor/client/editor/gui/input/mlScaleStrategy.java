package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Point;

/**
 * This state is used for scaling.
 * 
 * @author Patrick
 *
 */
public class mlScaleStrategy implements mlMouseStrategy {
    
    private InputController controller = null;
    
    public mlScaleStrategy(InputController contr){
        this.controller = contr;
    }
    @Override
    public void mousePressed(Point p) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(Point p) {
        controller.mkListener.removeStrategy();
        controller.graphic.scaleUpdate();
    }

    @Override
    public void mouseDragged(Point p) {
        controller.graphic.scale(p);
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub

    }

}
