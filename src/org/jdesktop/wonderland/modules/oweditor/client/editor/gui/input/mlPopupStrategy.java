package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Point;

public class mlPopupStrategy implements mlMouseStrategy{
    
    private InputController controller;
    
    public mlPopupStrategy(InputController contr){
        this.controller = contr;
    }

    @Override
    public void mousePressed(Point p) {
        
        boolean shapesSelected = controller.shape.popupInitialize(p);
        boolean copyShapesExist = controller.shape.copyShapesExist();
        
        controller.frame.showPopupMenu(shapesSelected,copyShapesExist, 
                p.x, p.y);
            
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
