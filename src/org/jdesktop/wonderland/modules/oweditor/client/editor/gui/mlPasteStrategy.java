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
        
        copyPoint = controller.esmi.copyInitialize();
        copyPoint.x += contr.drawingPan.getTranslationX();
        copyPoint.y += contr.drawingPan.getTranslationY();
    }
    
    
    @Override
    public void mousePressed(Point p) {
        if(!dragging){
            
            if(copyPoint == null)
                return;
            
            double scale = controller.drawingPan.getScale();
            
            int x = (int) Math.round(copyPoint.x * scale);
            int y = (int) Math.round(copyPoint.y * scale);
            
            start.x = x;
            start.y = y;

            controller.esmi.clearCurSelection();
            controller.esmi.pasteInitialize();
            
            dragging = true;
        }else{
            dragging = false;
            
            controller.esmi.pasteInsertShapes();
            
            listener.releaseCopyMouseLock();
            controller.drawingPan.repaint();
        }
    }

    @Override
    public void mouseReleased(Point p) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(Point p) {
        if(dragging) {
            controller.esmi.pasteTranslate(p.x,p.y, start);
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

}
