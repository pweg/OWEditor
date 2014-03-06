package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Point;

/**
 * This strategy is used for the popup menu.
 * 
 * @author Patrick
 *
 */
public class mlPopupStrategy implements mlMouseStrategy{
    
    private InputController controller;
    
    public mlPopupStrategy(InputController contr){
        this.controller = contr;
    }

    @Override
    public void mousePressed(Point p) {
        
        Point reverted = controller.window.revertBack(p);
        
        //mouse is in normal shape.
        if(controller.graphic.selectionSwitch(reverted, false)){
            controller.window.selectionChange(controller.graphic.isShapeSelected());
            controller.window.setToBGVisible(true);
        //mouse is in background shape
        }else if(controller.graphic.isMouseInBGObject(reverted)){
            controller.window.selectionChange(false);
            controller.window.setToBGVisible(false);
        //mouse is in no shape, but there are shapes selected
        }else if (!controller.graphic.isShapeSelected()){
            controller.window.selectionChange(false);
            controller.window.setToBGVisible(true);
        }
        
        controller.window.showPopupMenu(p.x, p.y);            
    }

    @Override
    public void mouseReleased(Point p) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseDragged(Point p) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub
        
    }

}
