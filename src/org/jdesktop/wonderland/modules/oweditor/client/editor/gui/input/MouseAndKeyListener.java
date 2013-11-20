package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUISettings;

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
    
    private InputController ic = null;
    
    public MouseAndKeyListener(InputController ic){
        this.ic = ic;
    }
    
    public void mousePressed(MouseEvent e) {
        
        if(shiftPressed && !rotation){
            if(e.getButton() ==  MouseEvent.BUTTON1 && !copy){
                
                Point p = e.getPoint();
                
                if(!ic.shape.selectionSwitch(p)){
                    strategy = new mlSelectionRectShiftStrategy(ic);
                    strategy.mousePressed(e.getPoint());
                } 
                ic.frame.repaint();               
            }
        }else{
             //dragging shapes/selection
             if(e.getButton() ==  MouseEvent.BUTTON1){
                 if(!copy && !rotation){
             
                     Point p = e.getPoint();
                        
                     if(ic.shape.isMouseInObject(p)){
                         strategy = new mlDragAndDropStrategy(ic);
                         strategy.mousePressed(e.getPoint());
                     }else{
                         strategy = new mlSelectionRectStrategy(ic);
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
                     
                     if(ic.shape.isMouseInBorder(p))
                         setRotationStrategy();
                     else if (ic.shape.isMouseInBorderCenter(p)){
                         setRotationCenterStrategy();
                     }
                     if(strategy != null)
                         strategy.mousePressed(p);
                 }
             }
             //Panning
             else if (e.getButton() ==  MouseEvent.BUTTON2 || e.getButton() ==  MouseEvent.BUTTON3){
                 if(!rotation)
                     ic.shape.cleanHelpingShapes();
                 strategy = new mlPanStrategy(ic.frame);
                 strategy.mousePressed(e.getPoint());
             }else{
                 clear();
                 
             }
        }
    }
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() ==  MouseEvent.BUTTON3 ){
            strategy = new mlPopupStrategy(ic);
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
        
        ic.frame.changeScale(GUISettings.zoomSpeed * -(double)e.getWheelRotation());
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
            ic.shape.deleteCurrentSelection();
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
                cutShapes();
            }
        }else{
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if(rotation){
                    ic.shape.rotateFinished();
                    clear();
                }
            }
        }
    }
    
    private void clear(){
        rotation = false;
        copy = false;
        ic.shape.cleanAll();
        releaseCopyMouseLock();
        if(strategy != null){
            if(strategy instanceof mlSelectionRectStrategy ||
                    strategy instanceof mlSelectionRectShiftStrategy)
                strategy = null;
            else if(strategy instanceof mlPasteStrategy){
                ((mlPasteStrategy)strategy).reset();
            }
        }
        ic.frame.repaint();
    }
    
    public void cutShapes(){
        copyShapes();
        ic.shape.deleteCurrentSelection();
    }
    
    public void copyShapes(){
        pasteStrategy = new mlPasteStrategy(ic, this);
        strategy = pasteStrategy;
    }
    
    public void pasteShapes(){
        
        strategy = pasteStrategy;
        
        if (strategy == null)
            return;
        
        strategy.mousePressed(null);
        strategy.mouseMoved(ic.frame.getMousePosition());
        copy = true;
        ic.frame.repaint();
    }
    
    public void releaseCopyMouseLock(){
        copy = false;
    }

    public void rotateShapes() {
        strategy = null;
        ic.shape.rotationInitialize();
        rotation = true;
        ic.frame.repaint();
    }
    
    public void removeStrategy(){
        strategy = null;
    }
    
    
    public void setRotationStrategy(){
        strategy = new mlRotationStrategy(ic);
    }
    
    public void setRotationCenterStrategy(){
        strategy = new mlRotationCenterStrategy(ic);
    }
    
    public mlPasteStrategy getPasteStrategy(){
        return pasteStrategy;
    }

}
