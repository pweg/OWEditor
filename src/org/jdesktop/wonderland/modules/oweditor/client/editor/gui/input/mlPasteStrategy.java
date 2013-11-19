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
        
        copyPoint = controller.shape.copyInitialize();
        copyPoint.x += contr.frame.getTranslationX();
        copyPoint.y += contr.frame.getTranslationY();
    }
    
    
    @Override
    public void mousePressed(Point p) {
        if(!dragging){
            
            if(copyPoint == null)
                return;
            
            double scale = controller.frame.getScale();
            
            int x = (int) Math.round(copyPoint.x * scale);
            int y = (int) Math.round(copyPoint.y * scale);
            
            start.x = x;
            start.y = y;

            controller.shape.clearCurSelection();
            controller.shape.pasteInitialize();
            
            dragging = true;
        }else{
            dragging = false;
            
            controller.shape.pasteInsertShapes();
            
            listener.releaseCopyMouseLock();
            controller.frame.repaint();
        }
    }

    @Override
    public void mouseReleased(Point p) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(Point p) {
        if(dragging) {
            controller.shape.pasteTranslate(p.x,p.y, start);
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
    
    public void notifyMinXChange(int x) {
        copyPoint.x -= x;
    }

    public void notifyMinYChange(int y) {
       copyPoint.y -= y;
    }

}
