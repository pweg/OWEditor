package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Point;

/**
 * This strategy is used for normal translation, without dragging the mouse.
 * It has two modes, the first is for translation, which is called
 * through frames of the window package. The second is for paste translation.
 * @author Patrick
 *
 */
public class mlTranslateStrategy implements mlMouseStrategy{
    
    public static final byte TRANSLATE = 0;
    public static final byte PASTE = 1;

    public InputController controller = null;
    public MouseAndKeyListener listener = null;
    private Point start = new Point();
    private boolean dragging = false;
    private Point movePoint = null;
    private byte mode = 0;;
    
    public mlTranslateStrategy(InputController contr, MouseAndKeyListener listener,
            byte mode){
        this.controller = contr;
        this.listener = listener;
        
        this.mode  = mode;
        
        switch(mode){
            case(TRANSLATE):
                movePoint = controller.graphic.getDraggingCenter();
                break;                
            case(PASTE):
                movePoint = controller.graphic.copyInitialize();
                break;
        }
    }
    
    @Override
    public void mousePressed(Point p) {
        if(!dragging){
            
            if(movePoint == null)
                return;
            
            
            start.x = movePoint.x;
            start.y = movePoint.y;

            if(mode == PASTE){
                controller.graphic.clearCurSelection();
                controller.graphic.pasteInitialize();
            }
            
            dragging = true;
        }else{
            dragging = false;
            
            switch(mode){
                case(TRANSLATE):
                    controller.window.translateFinish();
                    break;
                case(PASTE):
                    controller.graphic.pasteFinished();
                    break;
            }
            
            listener.clear();
        }
    }

    @Override
    public void mouseReleased(Point p) {
        
    }

    @Override
    public void mouseMoved(Point p) {
        if(dragging) {
            controller.graphic.translate(p.x,p.y, start);
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
