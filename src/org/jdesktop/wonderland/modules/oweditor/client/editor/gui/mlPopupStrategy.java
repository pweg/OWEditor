package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;

public class mlPopupStrategy implements mlMouseStrategy{
    
    private GUIController contr;
    
    public mlPopupStrategy(GUIController contr){
        this.contr = contr;
    }

    @Override
    public void mousePressed(Point p) {
        
        boolean shapesSelected = contr.esfi.popupInitialize(p);
        boolean copyShapesExist = contr.esfi.copyShapesExist();
        
        contr.effi.showPopupMenu(shapesSelected,copyShapesExist, 
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
