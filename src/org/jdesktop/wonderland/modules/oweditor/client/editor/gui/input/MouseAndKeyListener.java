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

    private static final byte NOMODE = 0;
    private static final byte COPY = 1;
    private static final byte ROTATE = 2;
    private static final byte SCALE = 3;
    
    private mlMouseStrategy strategy = null;
    private mlPasteStrategy pasteStrategy = null;
    
    private boolean shiftPressed = false;
    
    
    private byte mode = 0;
    //private boolean copy = false;
    //private boolean rotation = false;
    
    private InputController ic = null;
    
    public MouseAndKeyListener(InputController ic){
        this.ic = ic;
    }
    
    public void mousePressed(MouseEvent e) {
        
        if(shiftPressed && mode == NOMODE){
            if(e.getButton() ==  MouseEvent.BUTTON1){
                
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
                 if(mode == NOMODE){
             
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
                 else if(mode == COPY){
                     if(strategy != null)
                         strategy.mousePressed(e.getPoint());
                 }
                 //rotation
                 else if(mode == ROTATE){
                     Point p = e.getPoint();
                     
                     if(ic.shape.isMouseInBorder(p))
                         strategy = new mlRotationStrategy(ic);
                     else if (ic.shape.isMouseInBorderCenter(p)){
                         setRotationCenterStrategy();
                     }
                     if(strategy != null)
                         strategy.mousePressed(p);
                 }
                 //scale
                 else if(mode == SCALE){
                     Point p = e.getPoint();
                     
                     if(ic.shape.isMouseInBorder(p))
                         strategy = new mlScaleStrategy(ic);
                     //else if (ic.shape.isMouseInBorderCenter(p)){
                     //    setRotationCenterStrategy();
                     //}
                     if(strategy != null)
                         strategy.mousePressed(p);
                 }
             }
             //Panning
             else if ((e.getButton() ==  MouseEvent.BUTTON2 || e.getButton() ==  MouseEvent.BUTTON3)){
                 if(mode == NOMODE || mode == COPY)
                     ic.shape.cleanHelpingShapes();
                 strategy = new mlPanStrategy(ic.frame);
                 strategy.mousePressed(e.getPoint());
             }else{
                 clear();
                 
             }
        }
    }
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() ==  MouseEvent.BUTTON3 && mode <= COPY){
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
            }else if(e.getKeyCode() == KeyEvent.VK_V && mode != COPY){
                pasteShapes(); 
            }else if(e.getKeyCode() == KeyEvent.VK_X && mode != COPY){
                cutShapes();
            }
        }else{
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if(mode == ROTATE){
                    ic.shape.rotateFinished();
                    clear();
                }else if(mode == SCALE){
                    ic.shape.scaleFinished();
                    clear();
                }
            }
        }
    }
    
    public void clear(){
        mode = NOMODE;
        ic.shape.cleanAll();
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
        
        if (pasteStrategy == null)
            return;
        
        pasteStrategy.setDragging(false);
        strategy = pasteStrategy;
        
        strategy.mousePressed(null);
        strategy.mouseMoved(ic.frame.getMousePosition());
        mode = COPY;
        ic.frame.repaint();
    }

    public void rotateShapes() {
        strategy = null;
        ic.shape.rotationInitialize();
        mode = ROTATE;
        ic.frame.repaint();
    }
    
    public void scaleShapes(){
        strategy = null;
        ic.shape.scaleInitialize();
        mode = SCALE;
        ic.frame.repaint();
    }
    
    public void removeStrategy(){
        strategy = null;
    }
    
    
    public void setRotationCenterStrategy(){
        strategy = new mlRotationCenterStrategy(ic);
    }
    
    public mlPasteStrategy getPasteStrategy(){
        return pasteStrategy;
    }

}
