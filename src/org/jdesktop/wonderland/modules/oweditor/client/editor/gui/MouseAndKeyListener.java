package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

/**
 * This class is used to catch mouse and key events.
 * 
 * @author Patrick
 *
 */
public class MouseAndKeyListener extends MouseInputAdapter implements KeyListener,
                                    MouseWheelListener{

    private mlMouseStrategy strategy = null;
    private boolean shiftPressed = false;
    private boolean lockLeftMousePressed = false;
    
    private GUIController gc = null;
    
    private double scale = 0.0;
    private Point copyPoint = null;
    
    MouseAndKeyListener(GUIController gc){
        this.gc = gc;
    }
    
    public void mousePressed(MouseEvent e) {
        
        if(shiftPressed){
            if(e.getButton() ==  MouseEvent.BUTTON1 && !lockLeftMousePressed){
                
                Point p = e.getPoint();
                ShapeObject shape = gc.sm.getShapeSuroundingPoint(p);
                
                if(shape != null){
                    gc.samm.switchSelection(shape);
                    gc.drawingPan.repaint();
                }else{
                    strategy = new mlSelectionRectShiftStrategy(gc);
                    strategy.mousePressed(e.getPoint());
                }                
            }
        }else{
             if(e.getButton() ==  MouseEvent.BUTTON1 && !lockLeftMousePressed){
                 Point p = e.getPoint();
                    
                 ShapeObject shape = gc.sm.getShapeSuroundingPoint(p);
                    
                 if(shape != null){
                     strategy = new mlDragAndDropStrategy(gc);
                     strategy.mousePressed(e.getPoint());
                 }else{
                     strategy = new mlSelectionRectStrategy(gc);
                     strategy.mousePressed(e.getPoint());
                 }
             }else if(lockLeftMousePressed){
                 strategy.mousePressed(e.getPoint());
             }
             else if (e.getButton() ==  MouseEvent.BUTTON2 || e.getButton() ==  MouseEvent.BUTTON3){
                 strategy = new mlPanStrategy(gc.drawingPan);
                 strategy.mousePressed(e.getPoint());
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
        strategy.mouseDragged(e.getPoint());
    }
    
    public void mouseMoved(MouseEvent e){
        if(strategy == null)
            return;
        strategy.mouseMoved(e.getPoint());
    }
    
    public void mouseReleased(MouseEvent e){
        if(strategy == null)
            return;
        strategy.mouseReleased(e.getPoint());
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
            gc.samm.deleteCurrentSelection();
        }else if(e.isControlDown()){
            if(e.getKeyCode() == KeyEvent.VK_C){
                gc.samm.copyCurrentSelection();
                
                copyPoint = gc.drawingPan.getMousePosition();
                scale = gc.drawingPan.getScale();
                
                copyPoint.x = (int) Math.round(copyPoint.x / scale);
                copyPoint.y = (int) Math.round(copyPoint.y / scale);
            }else if(e.getKeyCode() == KeyEvent.VK_V){
                if(copyPoint == null)
                    return;
                
                strategy = new mlCopyStrategy(gc, this);
                scale = gc.drawingPan.getScale();
                
                int x = (int) Math.round(copyPoint.x * scale);
                int y = (int) Math.round(copyPoint.y * scale);
                Point p = new Point(x,y);
                
                strategy.mousePressed(p);
                strategy.mouseMoved(gc.drawingPan.getMousePosition());
                lockLeftMousePressed = true;
                
            }else if(e.getKeyCode() == KeyEvent.VK_X){
                
            }
        }
    }
    
    public void releaseLockLeftMouse(){
        lockLeftMousePressed = false;
    }

}
