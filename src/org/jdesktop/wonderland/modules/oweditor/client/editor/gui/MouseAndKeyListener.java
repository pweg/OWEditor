package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.event.MouseInputAdapter;

public class MouseAndKeyListener extends MouseInputAdapter implements KeyListener,
                                    MouseWheelListener{

    private MouseStrategy strategy = null;
    private boolean shiftPressed = false;
    private GUIController gc = null;
    
    MouseAndKeyListener(GUIController gc){
        this.gc = gc;
    }
    
    public void mousePressed(MouseEvent e) {
        if(shiftPressed){
            if(e.getButton() ==  MouseEvent.BUTTON1){

                Point p = e.getPoint();
                ArrayList<ShapeObject> shapes = gc.sm.getShapes();
                
                for(ShapeObject shape_obj : shapes){
                    Shape shape = shape_obj.getTransformedShape();
                    
                    if(shape.contains(p)) {
                        gc.samm.switchSelection(shape_obj);
                        gc.drawingPan.repaint();
                        break;
                    }
                }
                /*
                 * Add a selection rectangle strategy here, for using 
                 * shift and selection rectangle to add/remove from 
                 * selection.
                 */
                
            }
        }else{
             if(e.getButton() ==  MouseEvent.BUTTON1){
                 Point p = e.getPoint();
                    
                 ShapeObject shape = gc.sm.getShapeSuroundingPoint(p);
                    
                 if(shape != null){
                     strategy = new mlDragAndDropStrategy(gc);
                     strategy.mousePressed(e);
                 }else{
                     strategy = new mlSelectionStrategy(gc);
                     strategy.mousePressed(e);
                 }
             }
             else if (e.getButton() ==  MouseEvent.BUTTON2 || e.getButton() ==  MouseEvent.BUTTON3){
                 strategy = new mlPanStrategy(gc.drawingPan);
                 strategy.mousePressed(e);
             }
        }
    }
    
    @Override
    public void mouseWheelMoved( MouseWheelEvent e )
    {

        WindowDrawingPanel panel = gc.drawingPan;
        
        /*
         * When removing this, it should be possible to drag selected
         * objects further when zooming in and out, but it will 
         * also need some refinements, due to the new scales.
         */
        if(strategy != null){
            gc.sm.clearDraggingShapes();
            gc.sm.removeSelectionRect();
            panel.repaint();
        }
        strategy = null;
        
        double curScale = panel.getScale();
        
        panel.changeScale(GUISettings.zoomSpeed * -(double)e.getWheelRotation());
        panel.changeViewPort(curScale);
    }
    
    public void mouseDragged(MouseEvent e) {
        if(strategy == null)
            return;
        strategy.mouseDragged(e);
    }
    
    public void mouseReleased(MouseEvent e){
        if(strategy == null)
            return;
        strategy.mouseReleased(e);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
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
        }else if( e.getKeyCode() == KeyEvent.VK_DELETE){
            gc.samm.deletePressed();
        }
    }

}
