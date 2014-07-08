package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Point;

/**
 * This strategy is used for the popup menu.
 * 
 * @author Patrick
 *
 */
public class mlPopupStrategy implements mlMouseStrategy{
    
    private MouseAndKeyListener listener;
    
    public mlPopupStrategy(MouseAndKeyListener listener){
        this.listener = listener;
    }

    @Override
    public void mousePressed(Point p) {
        
        Point reverted = listener.window.revertBack(p);
        
        //mouse is in normal shape.
        if(listener.graphic.selectionSwitch(reverted, false)){
            listener.window.selectionChange(listener.graphic.isShapeSelected());
            listener.window.setToBGVisible(true);
        //mouse is in background shape
        }else if(listener.graphic.isMouseInBGObject(reverted)){
            listener.window.selectionChange(false);
            listener.window.setToBGVisible(false);
        //mouse is in no shape, but there are shapes selected
        }else if (!listener.graphic.isShapeSelected()){
            listener.window.selectionChange(false);
            listener.window.setToBGVisible(true);
        }
        
        listener.window.showPopupMenu(p.x, p.y);            
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
