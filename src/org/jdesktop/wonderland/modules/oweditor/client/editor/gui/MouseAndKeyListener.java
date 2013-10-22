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
    
    private boolean copy = false;
    private boolean rotation = false;
    
    private GUIController gc = null;
    
    private double scale = 0.0;
    private Point currentPoint = null;
    
    MouseAndKeyListener(GUIController gc){
        this.gc = gc;
    }
    
    public void mousePressed(MouseEvent e) {
        
        if(shiftPressed && !rotation){
            if(e.getButton() ==  MouseEvent.BUTTON1 && !copy){
                
                Point p = e.getPoint();
                
                if(!gc.esmi.selectionSwitch(p)){
                    strategy = new mlSelectionRectShiftStrategy(gc);
                    strategy.mousePressed(e.getPoint());
                } 
                gc.drawingPan.repaint();               
            }
        }else{
             //dragging shapes/selection
             if(e.getButton() ==  MouseEvent.BUTTON1){
                 if(!copy && !rotation){
             
                     Point p = e.getPoint();
                        
                     if(gc.esmi.isMouseInObject(p)){
                         strategy = new mlDragAndDropStrategy(gc);
                         strategy.mousePressed(e.getPoint());
                     }else{
                         strategy = new mlSelectionRectStrategy(gc);
                         strategy.mousePressed(e.getPoint());
                     }
                 }
                 //paste/insertion
                 else if(copy && !rotation){
                     strategy.mousePressed(e.getPoint());
                 }
                 //rotation
                 else if(rotation && !copy){
                     Point p = e.getPoint();
                     currentPoint = p;
                     
                     if(strategy == null){
                         gc.esmi.isMouseInBorder(p);
                     }else{
                         strategy.mousePressed(p);
                     }
                 }
             }
             //Panning
             else if (e.getButton() ==  MouseEvent.BUTTON2 || e.getButton() ==  MouseEvent.BUTTON3){
                 gc.esmi.cleanHelpingShapes();
                 strategy = new mlPanStrategy(gc.drawingPan);
                 strategy.mousePressed(e.getPoint());
             }else{
                 clear();
                 
             }
        }
    }
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() ==  MouseEvent.BUTTON3 ){
            strategy = new mlPopupStrategy(gc);
            strategy.mousePressed(e.getPoint());
        }
     }
    
    @Override
    public void mouseWheelMoved( MouseWheelEvent e )
    {

        
        /*
         * When removing this, it should be possible to drag selected
         * objects further when zooming in and out, but it will 
         * also need some refinements, due to the new scales.
         */
        if(strategy != null){
            clear();
        }
        
        WindowDrawingPanel panel = gc.drawingPan;
        
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
            gc.esmi.deleteCurrentSelection();
        }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            clear();
        }else if(e.isControlDown()){
            if(e.getKeyCode() == KeyEvent.VK_C){
                copyShapes();
                //This can be used, if you want the copies to appear
                //approximated to the mouse position, when the copy
                //button was hit.
                /*
                 * copyPoint = gc.drawingPan.getMousePosition();
                 * double scale = gc.drawingPan.getScale();
                 * copyPoint.x = (int) Math.round(copyPoint.x / scale);
                 * copyPoint.y = (int) Math.round(copyPoint.y / scale);
                */
            }else if(e.getKeyCode() == KeyEvent.VK_V && !copy){
                pasteShapes(); 
            }else if(e.getKeyCode() == KeyEvent.VK_X && !copy){
                
            }
        }
    }
    
    private void clear(){
        rotation = false;
        copy = false;
        gc.esmi.cleanAll();
        gc.drawingPan.repaint();
        releaseCopyMouseLock();
        if(strategy instanceof mlSelectionRectStrategy ||
                strategy instanceof mlSelectionRectShiftStrategy)
            strategy = null;
        else if(strategy instanceof mlPasteStrategy){
            ((mlPasteStrategy)strategy).reset();
        }
    }
    
    public void copyShapes(){
        strategy = new mlPasteStrategy(gc, this);
    }
    
    public void pasteShapes(){
        
        if(strategy == null)
            return; 
        strategy.mousePressed(null);
        strategy.mouseMoved(gc.drawingPan.getMousePosition());
        copy = true;
    }
    
    public void releaseCopyMouseLock(){
        copy = false;
    }

    public void rotateShapes() {
        strategy = null;
        gc.esmi.rotationInitialize();
        gc.drawingPan.repaint();
        rotation = true;
    }
    
    
    public void setRotationStrategy(){
        strategy = new mlRotationStrategy(gc, currentPoint);
    }
    
    public void setRotationCenterStrategy(){
        
    }

}
