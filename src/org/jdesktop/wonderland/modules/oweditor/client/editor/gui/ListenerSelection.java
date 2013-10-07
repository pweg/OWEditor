package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.event.MouseInputAdapter;

/**
 * This is a mouse/key listener used for selecting and unselecting shapes.
 * It is also used for the selection rectangle.
 * 
 * @author Patrick
 *
 */
public class ListenerSelection extends MouseInputAdapter implements KeyListener{
    
	/*
	 * NOSELSPAN = There is no selection rectangle.
	 * SELFIRST = The selection rectangle is initialized and has a 
	 * 			  starting point.
	 * SELSPAN = The selection rectangle is created.
	 */
    public static final byte NOSELSPAN = 0;
    public static final byte SELFIRST = 1;
    public static final byte SELSPAN = 2;
    
    private GUIController controller = null;
    private boolean shiftPressed = false;
    private byte selectionRect = NOSELSPAN;
    
    private Point start = new Point();
    
    
    public ListenerSelection(GUIController contr){
        controller = contr;
    }


   /**
    * When mouse button 1 is pressed, it looks for shapes the mouse is
    * currently over and switches their selection, if one was found. If
    * no shape was found, the whole selection will be unselected and the 
    * first part for the selection rectangle will be created.
    * If the SHIFT key was pressed, it will simply add/remove a shape to
    * the current selection.
    */
    public void mousePressed(MouseEvent e) {
        if(shiftPressed){

            Point p = e.getPoint();
            if(e.getButton() ==  MouseEvent.BUTTON1){
                ArrayList<ShapeObject> shapes = controller.sm.getShapes();
                
                for(ShapeObject shape_obj : shapes){
                    Shape shape = shape_obj.getTransformedShape();
                    
                    if(shape.contains(p)) {
                        controller.samm.switchSelection(shape_obj);
                        controller.drawingPan.repaint();
                        break;
                    }
                }
            }
        }
        else{
            if(e.getButton() ==  MouseEvent.BUTTON1){
                Point p = e.getPoint();
                ArrayList<ShapeObject> shapes = controller.sm.getShapes();
                
                boolean isInside = false;
                for(ShapeObject shape_obj : shapes){
                    
                    Shape shape = shape_obj.getTransformedShape();
                    
                    if(shape.contains(p)) {
                         isInside = true;
                         if(!shape_obj.isSelected()){
                             controller.samm.removeCurSelection();
                             controller.samm.setSelected(shape_obj, true);
                             controller.drawingPan.repaint();
                         }
                    }
                }
                
                if(!isInside){
                    controller.samm.removeCurSelection();
                    
                    start.x = e.getX();
                    start.y = e.getY();
                    selectionRect = SELFIRST;
                    
                    controller.drawingPan.repaint();
                }
            }
        }
    }

    /**
     * This is used, when the selection rectangle was at least
     * initialized and will create a selection rectangle.
     */
    public void mouseDragged(MouseEvent e) {
        
        if(selectionRect != NOSELSPAN){
            Point end = new Point(e.getX(), e.getY());
           
            controller.samm.resizeSelectionRect(start, end);
            controller.drawingPan.repaint();
        }
    }
   
    /**
     * This is only used when the selection rectangle was at least
     * initialized and will create a selection of shapes which are
     * in the selection rectangle.
     */
    public void mouseReleased(MouseEvent arg0) {
        
       if(selectionRect != NOSELSPAN){
           selectionRect = NOSELSPAN;
           
           controller.samm.selectionPressReleased();
           
           controller.sm.removeSelectionRect();
           controller.drawingPan.repaint();
       }
    }
    

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            shiftPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            shiftPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
}
