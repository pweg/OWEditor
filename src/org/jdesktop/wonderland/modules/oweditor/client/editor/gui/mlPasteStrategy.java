package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;

public class mlPasteStrategy implements mlMouseStrategy{

    public GUIController controller = null;
    public MouseAndKeyListener listener = null;
    private Point start = new Point();
    private boolean dragging = false;
    private Point copyPoint = null;
    
    public mlPasteStrategy(GUIController contr, MouseAndKeyListener listener){
        this.controller = contr;
        this.listener = listener;
        
        copyPoint = controller.esfi.copyInitialize();
        copyPoint.x += contr.effi.getTranslationX();
        copyPoint.y += contr.effi.getTranslationY();
    }
    
    
    @Override
    public void mousePressed(Point p) {
        if(!dragging){
            
            if(copyPoint == null)
                return;
            
            double scale = controller.effi.getScale();
            
            int x = (int) Math.round(copyPoint.x * scale);
            int y = (int) Math.round(copyPoint.y * scale);
            System.out.println (x + " " + y);
            
            start.x = x;
            start.y = y;

            controller.esfi.clearCurSelection();
            controller.esfi.pasteInitialize();
            
            dragging = true;
        }else{
            dragging = false;
            
            controller.esfi.pasteInsertShapes();
            
            listener.releaseCopyMouseLock();
            controller.effi.repaint();
        }
    }

    @Override
    public void mouseReleased(Point p) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(Point p) {
        if(dragging) {
            controller.esfi.pasteTranslate(p.x,p.y, start);
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
