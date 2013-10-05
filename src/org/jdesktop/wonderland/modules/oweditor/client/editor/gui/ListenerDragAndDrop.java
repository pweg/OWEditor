package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

/**
 * This is a mouse listener class used for the drag and drop
 * implementation of shapes.
 * 
 * @author Patrick
 *
 */
public class ListenerDragAndDrop extends MouseInputAdapter{

    private GUIController controler;
    private Point start = new Point();
    private boolean dragging = false;
 
    public ListenerDragAndDrop(GUIController contr) {
        controler = contr;
    }
    
    /**
     * Start the mouse dragging event.
     */
    public void mousePressed(MouseEvent e) {
        if(e.getButton() ==  MouseEvent.BUTTON1){
            Point p = e.getPoint();
            
            ShapeObject shape = controler.sm.getShapeSuroundingPoint(p);
            
            if(shape != null){
                start.x = p.x;
                start.y = p.y;
                dragging = true;
            }
        }
    }
 
    /**
     * Ends the mouse dragging event and signals to
     * update the shape object coordinates when no collision
     * is found.
     */
    public void mouseReleased(MouseEvent e) {
        dragging = false;
        
        if(!controler.samm.collision){
            controler.sm.saveDraggingShapes();
            controler.setAdapterUpdate();
        }
        controler.sm.clearDraggingShapes();
        controler.drawingPan.repaint();
    }
 
    public void mouseDragged(MouseEvent e) {
        if(dragging) {
            controler.samm.translateShape(e.getX(), e.getY(), start);
            start.x = e.getX();
            start.y = e.getY();
        }
    }
}
