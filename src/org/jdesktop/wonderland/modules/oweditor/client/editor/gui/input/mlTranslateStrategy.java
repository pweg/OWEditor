package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Point;

/**
 * This strategy is like the drag translate, but does not need the mouse
 * to be dragged. 
 * 
 * @author Patrick
 *
 */
public class mlTranslateStrategy implements mlMouseStrategy{

    public InputController controller = null;
    public MouseAndKeyListener listener = null;
    private Point start = new Point();
    private boolean dragging = false;
    private Point movePoint = null;
    
    public mlTranslateStrategy(InputController contr, MouseAndKeyListener listener){
        this.controller = contr;
        this.listener = listener;
        
        movePoint = controller.graphic.getDraggingCenter();
    }
    
    
    @Override
    public void mousePressed(Point p) {
        if(!dragging){
            
            if(movePoint == null)
                return;
            
            
            start.x = movePoint.x;
            start.y = movePoint.y;
            

            //controller.graphic.clearCurSelection();
            //controller.graphic.pasteInitialize();
            
            dragging = true;
        }else{
            dragging = false;
            
            controller.window.translateFinish();
            
            listener.clear();
            controller.window.repaint();
        }
    }

    @Override
    public void mouseReleased(Point p) {
        
    }

    @Override
    public void mouseMoved(Point p) {
        if(dragging) {
            controller.graphic.pasteTranslate(p.x,p.y, start);
            start.x = p.x;
            start.y = p.y;
        }
    }
    
    public void reset(){
        dragging = false;
    }


    @Override
    public void mouseDragged(Point p) {
        // TODO Auto-generated method stub
        
    }
    
    public void setDragging(boolean dragging){
        this.dragging = dragging;
    }

}
