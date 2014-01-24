package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Point;

public class mlPasteStrategy implements mlMouseStrategy{

    public InputController controller = null;
    public MouseAndKeyListener listener = null;
    private Point start = new Point();
    private boolean dragging = false;
    private Point copyPoint = null;
    
    public mlPasteStrategy(InputController contr, MouseAndKeyListener listener){
        this.controller = contr;
        this.listener = listener;
        
        copyPoint = controller.graphic.copyInitialize();
    }
    
    
    @Override
    public void mousePressed(Point p) {
        if(!dragging){
            
            if(copyPoint == null)
                return;
            
            
            start.x = copyPoint.x;
            start.y = copyPoint.y;

            controller.graphic.clearCurSelection();
            controller.graphic.pasteInitialize();
            
            dragging = true;
        }else{
            dragging = false;
            
            controller.graphic.pasteFinished();
            
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
