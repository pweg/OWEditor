package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;

public class mlPopupStrategy implements mlMouseStrategy{
    
    private GUIController contr;
    
    public mlPopupStrategy(GUIController contr){
        this.contr = contr;
    }

    @Override
    public void mousePressed(Point p) {
        
        contr.esmi.popupInitialize(p);

        contr.drawingPan.repaint();
        contr.popupMenu.show(contr.drawingPan, p.x, p.y);
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
