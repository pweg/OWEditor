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
        
        //operations with shift pressed
        if(shiftPressed && mode == NOMODE){
            if(e.getButton() ==  MouseEvent.BUTTON1){
                
                Point p = e.getPoint();
                
                //switch selection, or selection rectangle
                //in shift mode
                if(!ic.graphic.selectionSwitch(p)){
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
                        
                     if(ic.graphic.isMouseInObject(p)){
                         strategy = new mlTranslateStrategy(ic);
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
                     
                     if(ic.graphic.isMouseInBorder(p))
                         strategy = new mlRotationStrategy(ic);
                     else if (ic.graphic.isMouseInBorderCenter(p)){
                         setRotationCenterStrategy();
                     }
                     if(strategy != null)
                         strategy.mousePressed(p);
                 }
                 //scale
                 else if(mode == SCALE){
                     Point p = e.getPoint();
                     
                     if(ic.graphic.isMouseInBorder(p))
                         strategy = new mlScaleStrategy(ic);
                     if(strategy != null)
                         strategy.mousePressed(p);
                 }
             }
             //Panning
             else if ((e.getButton() ==  MouseEvent.BUTTON2 || e.getButton() ==  MouseEvent.BUTTON3)){
                 if(mode == NOMODE || mode == COPY)
                     ic.graphic.cleanHelpingShapes();
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

        ic.frame.changeScale(GUISettings.ZOOMSPEED * -(double)e.getWheelRotation());
        
        clearShapeName();
    }

    public void mouseDragged(MouseEvent e) {
        Point p = e.getPoint();
        ic.frame.paintMouseCoords(p.x, p.y);
        writeShapeName(p);
        
        if(strategy == null){
            return;
        }
        strategy.mouseDragged(p);
    }
    
    public void mouseMoved(MouseEvent e){
        Point p = e.getPoint();
        ic.frame.paintMouseCoords(p.x, p.y);

        writeShapeName(p);
        
        if(strategy == null){
            return;
        }
        strategy.mouseMoved(p);
        
    }
    
    public void mouseReleased(MouseEvent e){
        if(strategy == null){
            ic.frame.repaint();
            return;
        }
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
        }
        //DELETE
        else if( e.getKeyCode() == KeyEvent.VK_DELETE){
            ic.graphic.deleteCurrentSelection();
        }
        //ESCAPE
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            clear();
        }
        //CTRL
        else if(e.isControlDown()){
            //COPY
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
            }
            //PASTE
            else if(e.getKeyCode() == KeyEvent.VK_V && mode != COPY){
                pasteShapes(); 
            }
            //CUT
            else if(e.getKeyCode() == KeyEvent.VK_X && mode != COPY){
                cutShapes();
            }
        }else{
            //ENTER
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                //FINISH ROTATION
                if(mode == ROTATE){
                    ic.graphic.rotateFinished();
                    clear();
                }
                //FINISH SCALING
                else if(mode == SCALE){
                    ic.graphic.scaleFinished();
                    clear();
                }
            }
        }
    }
    
    /**
     * Removes the current set mode, cleans all helping shapes
     * and sets the current strategy to zero. If the paste
     * strategy is set, the strategy will be reseted.
     */
    public void clear(){
        mode = NOMODE;
        ic.graphic.cleanAll();
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
    
    /**
     * Cut shapes, like copy, but removing the
     * original shapes.
     */
    public void cutShapes(){
        copyShapes();
        ic.graphic.deleteCurrentSelection();
    }
    
    /**
     * Copy shapes.
     */
    public void copyShapes(){
        pasteStrategy = new mlPasteStrategy(ic, this);
        strategy = pasteStrategy;
    }
    
    /**
     * Paste shapes.
     */
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

    /**
     * Rotates the shapes.
     */
    public void rotateShapes() {
        strategy = null;
        ic.graphic.rotationInitialize();
        mode = ROTATE;
        ic.frame.repaint();
    }
    
    /**
     * Scales the shapes.
     */
    public void scaleShapes(){
        strategy = null;
        ic.graphic.scaleInitialize();
        mode = SCALE;
        ic.frame.repaint();
    }
    
    /**
     * Removes the current strategy.
     */
    public void removeStrategy(){
        strategy = null;
    }
    
    /**
     * Sets a new strategy for the rotation center.
     */
    public void setRotationCenterStrategy(){
        strategy = new mlRotationCenterStrategy(ic);
    }
    
    /**
     * Returns the current paste strategy set.
     * 
     * @return The paste Strategy.
     */
    public mlPasteStrategy getPasteStrategy(){
        return pasteStrategy;
    }
    
    /**
     * Check for name tooltip. If the graphic package 
     * returns true, a repaint is necessary. 
     * 
     * @param p The current mouse point.
     */
    private void writeShapeName(Point p){
        String name = ic.graphic.getShapeName(p);
        
        //only repaint, when necessary, ergo when the name
        //tooltip moves or has to be shown/hidden.
        if(ic.graphic.paintShapeName(p, name)){
            ic.frame.repaint();
        }
    }

    /**
     * Removes the shape name tooltip.
     */
    private void clearShapeName() {
        if(ic.graphic.removeShapeName())
            ic.frame.repaint();
    }

}
