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
    private mlPasteStrategy pasteStrategy = null;
    
    private boolean shiftPressed = false;
    
    private boolean copy = false;
    private boolean rotation = false;
    
    private GUIController gc = null;
    
    MouseAndKeyListener(GUIController gc){
        this.gc = gc;
    }
    
    public void mousePressed(MouseEvent e) {
        
        if(shiftPressed && !rotation){
            if(e.getButton() ==  MouseEvent.BUTTON1 && !copy){
                
                Point p = e.getPoint();
                
                if(!gc.esfi.selectionSwitch(p)){
                    strategy = new mlSelectionRectShiftStrategy(gc);
                    strategy.mousePressed(e.getPoint());
                } 
                gc.effi.repaint();               
            }
        }else{
             //dragging shapes/selection
             if(e.getButton() ==  MouseEvent.BUTTON1){
                 if(!copy && !rotation){
             
                     Point p = e.getPoint();
                        
                     if(gc.esfi.isMouseInObject(p)){
                         strategy = new mlDragAndDropStrategy(gc);
                         strategy.mousePressed(e.getPoint());
                     }else{
                         strategy = new mlSelectionRectStrategy(gc);
                         strategy.mousePressed(e.getPoint());
                     }
                 }
                 //paste/insertion
                 else if(copy && !rotation){
                     if(strategy != null)
                         strategy.mousePressed(e.getPoint());
                 }
                 //rotation
                 else if(rotation && !copy){
                     Point p = e.getPoint();
                     
                     gc.esfi.isMouseInBorder(p);
                     if(strategy != null)
                         strategy.mousePressed(p);
                 }
             }
             //Panning
             else if (e.getButton() ==  MouseEvent.BUTTON2 || e.getButton() ==  MouseEvent.BUTTON3){
                 if(!rotation)
                     gc.esfi.cleanHelpingShapes();
                 strategy = new mlPanStrategy(gc.effi);
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
        clear();        
        
        gc.effi.changeScale(GUISettings.zoomSpeed * -(double)e.getWheelRotation());
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
        strategy = null;
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
            gc.esfi.deleteCurrentSelection();
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
        }else{
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if(rotation){
                    gc.esfi.rotateSetUpdate();
                    clear();
                }
            }
        }
    }
    
    private void clear(){
        rotation = false;
        copy = false;
        gc.esfi.cleanAll();
        releaseCopyMouseLock();
        if(strategy != null){
            if(strategy instanceof mlSelectionRectStrategy ||
                    strategy instanceof mlSelectionRectShiftStrategy)
                strategy = null;
            else if(strategy instanceof mlPasteStrategy){
                ((mlPasteStrategy)strategy).reset();
            }
        }
        gc.effi.repaint();
    }
    
    public void copyShapes(){
        pasteStrategy = new mlPasteStrategy(gc, this);
        strategy = pasteStrategy;
    }
    
    public void pasteShapes(){
        
        strategy = pasteStrategy;
        
        if (strategy == null)
            return;
        
        strategy.mousePressed(null);
        strategy.mouseMoved(gc.effi.getMousePosition());
        copy = true;
        gc.effi.repaint();
    }
    
    public void releaseCopyMouseLock(){
        copy = false;
    }

    public void rotateShapes() {
        strategy = null;
        gc.esfi.rotationInitialize();
        rotation = true;
        gc.effi.repaint();
    }
    
    public void removeStrategy(){
        strategy = null;
    }
    
    
    public void setRotationStrategy(){
        strategy = new mlRotationStrategy(gc);
    }
    
    public void setRotationCenterStrategy(){
        strategy = new mlRotationCenterStrategy(gc);
    }
    
    public mlPasteStrategy getPasteStrategy(){
        return pasteStrategy;
    }

}
