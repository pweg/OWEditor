package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class mSelectionStrategy implements MouseStrategy{
	

    private static final byte NOSELSPAN = 0;
    private static final byte SELFIRST = 1;
    private static final byte SELSPAN = 2;
    
    private GUIController controller = null;
    private byte selectionRect = NOSELSPAN;
    
    private Point start = new Point();
    
    mSelectionStrategy(GUIController contr){
    	controller = contr;
    }

	@Override
	public void mousePressed(MouseEvent e) {
        
        controller.samm.removeCurSelection();
            
        start.x = e.getX();
        start.y = e.getY();
        selectionRect = SELFIRST;
            
        controller.drawingPan.repaint();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(selectionRect != NOSELSPAN){
	           selectionRect = NOSELSPAN;
	           
	           controller.samm.selectionPressReleased();
	           
	           controller.sm.removeSelectionRect();
	           controller.drawingPan.repaint();
	       }
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		 if(selectionRect != NOSELSPAN){
	            Point end = new Point(e.getX(), e.getY());
	           
	            controller.samm.resizeSelectionRect(start, end);
	            controller.drawingPan.repaint();
	        }
	}

}
