package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape.sCollisionAllStrategy;

public class mlCopyStrategy implements mlMouseStrategy{

    public GUIController controller = null;
    public MouseAndKeyListener listener = null;
    private Point start = new Point();
    private boolean dragging = false;
    
    public mlCopyStrategy(GUIController contr, MouseAndKeyListener listener){
        this.controller = contr;
        this.listener = listener;
    }
    
    
    @Override
    public void mousePressed(Point p) {
        if(!dragging){
            
            start.x = p.x;
            start.y = p.y;

            controller.esmi.clearCurSelection();
            controller.esmi.createCopyShapes();
            
            dragging = true;
        }else{
            dragging = false;
            
            if(!controller.esmi.checkCollision()){
                controller.esmi.setTranslatedShapes();
                controller.setCopyUpdate();
            }
            listener.releaseLockLeftMouse();
            controller.esmi.clearDraggingShapes();
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
            controller.esmi.translateShapeCopy(p.x,p.y, start);
            start.x = p.x;
            start.y = p.y;
        }
    }


    @Override
    public void mouseDragged(Point p) {
        // TODO Auto-generated method stub
        
    }

}
