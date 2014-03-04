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

    private static final byte NOMODE = IInputToWindow.NOMODE;
    public static final byte COPY = IInputToWindow.COPY;
    private static final byte PASTE = IInputToWindow.PASTE;
    private static final byte ROTATE = IInputToWindow.ROTATE;
    private static final byte SCALE = IInputToWindow.SCALE;
    private static final byte CUT = IInputToWindow.CUT;
    private static final byte TRANSLATE = IInputToWindow.TRANSLATE;
    
    private mlMouseStrategy strategy = null;
    private mlTranslateStrategy pasteStrategy = null;
    
    private boolean shiftPressed = false;
    
    private byte mode = 0;
    //private boolean copy = false;
    //private boolean rotation = false;
    
    private InputController ic = null;
    
    public MouseAndKeyListener(InputController ic){
        this.ic = ic;
    }
    
    public void mousePressed(MouseEvent e) {
        
        Point p = ic.window.revertBack(e.getPoint());
        
        //operations with shift pressed
        if(shiftPressed && mode == NOMODE){
            if(e.getButton() ==  MouseEvent.BUTTON1){
                //switch selection, or selection rectangle
                //in shift mode
                if(!ic.graphic.selectionSwitch(p, true)){
                    strategy = new mlSelectionRectStrategy(ic);
                    strategy.mousePressed(p);
                }           
            }
        }else{
             //dragging shapes/selection rectangle
             if(e.getButton() ==  MouseEvent.BUTTON1){
                 if(mode == NOMODE){//translate
                     if(ic.graphic.isMouseInObject(p)){
                         ic.graphic.selectionSwitch(p, false);
                     }else{//selection rectangle
                         ic.graphic.clearCurSelection();
                         strategy = new mlSelectionRectStrategy(ic);
                     }
                 }
                 //paste/insertion
                 else if(mode == PASTE){
                 }
                 //rotation
                 else if(mode == ROTATE){
                     if(ic.graphic.isMouseInBorder(p))
                         strategy = new mlRotationStrategy(ic);
                     else if (ic.graphic.isMouseInBorderCenter(p)){
                         setRotationCenterStrategy();
                     }
                 }
                 //scale
                 else if(mode == SCALE){
                     if(ic.graphic.isMouseInBorder(p))
                         strategy = new mlScaleStrategy(ic);
                 }

             if(strategy != null)
                 strategy.mousePressed(p);
             //this has to be made after strategy mouse press to ensure
             //everything important already happened.
             ic.window.selectionChange(ic.graphic.isShapeSelected());
             }
             //Panning
             else if ((e.getButton() ==  MouseEvent.BUTTON2 || e.getButton() ==  MouseEvent.BUTTON3)){
                 if(mode == NOMODE || mode == PASTE)
                     ic.graphic.cleanHelpingShapes();
                 strategy = new mlPanStrategy(ic.window);
                 //mlPanStrategy needs the panel coordinates in order to
                 //work properly.
                 strategy.mousePressed(e.getPoint());
             }else{
                 clear();
                 
             }
        }
        if(strategy != null)
            ic.window.repaint();    
    }
    
    public void mouseClicked(MouseEvent e) {

        Point p = e.getPoint();
        
        if (e.getButton() ==  MouseEvent.BUTTON3 && mode <= PASTE){
            ic.graphic.selectionSwitch(ic.window.revertBack(p), false);
            ic.window.selectionChange(ic.graphic.isShapeSelected());
            
            strategy = new mlPopupStrategy(ic);
            strategy.mousePressed(p);
            ic.window.repaint();  
        }
     }

    public void mouseDragged(MouseEvent e) {
        Point p = ic.window.revertBack(e.getPoint());
        
        ic.window.paintMouseCoords(p.x, p.y);
        writeShapeName(p);
        
        if(strategy == null && mode == NOMODE){
            if(ic.graphic.isMouseInObject(p)){
                
                //creates the dragging shapes
                ic.graphic.translateIntialize(p);
                
                strategy = new mlTranslateDragStrategy(ic);
                strategy.mousePressed(p);
            }else
                return;
        }else if (strategy == null){
            return;
        }
        //mlPanStrategy needs the panel coordinates in order to
        //work properly.
        else if(strategy instanceof mlPanStrategy)
            p = e.getPoint();
        
        strategy.mouseDragged(p);
        ic.window.repaint();  
    }
    
    @Override
    public void mouseWheelMoved( MouseWheelEvent e )
    {
        /*
         * Do not allow scaling the image while translating.
         */
        
        /*
         * When removing this, it should be possible to drag selected
         * objects further when zooming in and out, but it will 
         * also need some refinements, due to the new scales.
         */
        if(mode != TRANSLATE)
            clear();        

        ic.window.changeScale(GUISettings.ZOOMSPEED * -(double)e.getWheelRotation());
        
        clearShapeName();
        ic.window.repaint();  
    }
    
    public void mouseMoved(MouseEvent e){
        Point p = ic.window.revertBack(e.getPoint());
        
        ic.window.paintMouseCoords(p.x, p.y);

        writeShapeName(p);
        
        if(strategy == null){
            return;
        }
        strategy.mouseMoved(p);
        ic.window.repaint();  
    }
    
    public void mouseReleased(MouseEvent e){

        Point p = ic.window.revertBack(e.getPoint());
        
        if(strategy == null){
            ic.window.repaint();
            return;
        }
        strategy.mouseReleased(p);
        strategy = null;
        ic.window.repaint();  
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            shiftPressed = true;
        }//DELETE
        else if( e.getKeyCode() == KeyEvent.VK_DELETE){
            ic.graphic.deleteCurrentSelection();
        }
        //ESCAPE
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            clear();
        }
        //ENTER
        else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
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

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                shiftPressed = false;
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
            if(strategy instanceof mlSelectionRectStrategy)
                strategy = null;
            else if(strategy instanceof mlTranslateStrategy){
                ((mlTranslateStrategy)strategy).reset();
            }
        }
        ic.window.repaint();
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
    public mlTranslateStrategy getPasteStrategy(){
        return pasteStrategy;
    }
    
    /**
     * Sets different modes for the listener.
     * 
     * @param mode The mode in byte form.
     */
    public void setMode(byte mode){
        switch(mode){
            case(COPY):
                pasteStrategy = new mlTranslateStrategy(ic, this, mlTranslateStrategy.PASTE);
                strategy = pasteStrategy;
                break;
            case(CUT):
                pasteStrategy = new mlTranslateStrategy(ic, this, mlTranslateStrategy.PASTE);
                strategy = pasteStrategy;
                ic.graphic.deleteCurrentSelection();
                break;
            case(PASTE):
                if (pasteStrategy == null)
                    return;

                ic.graphic.pasteInitialize();
                pasteStrategy.setDragging(false);
                strategy = pasteStrategy;
                
                strategy.mousePressed(null);
                strategy.mouseMoved(ic.window.revertBack(ic.window.getMousePosition()));
                this.mode = PASTE;
                break;
            case(ROTATE):
                strategy = null;
                //could be done in WindowToMenu, but don't want to split 
                //from other rotation operations
                ic.graphic.rotationInitialize();
                this.mode = ROTATE;
                break;
            case(SCALE):
                strategy = null;
                //could be done in WindowToMenu, but don't want to split 
                //from other scaling operations
                ic.graphic.scaleInitialize();
                this.mode = SCALE;
                break;
            case(TRANSLATE):
                strategy = new mlTranslateStrategy(ic, this, mlTranslateStrategy.TRANSLATE);
                strategy.mousePressed(null);
                strategy.mouseMoved(ic.window.revertBack(ic.window.getMousePosition()));
            
                this.mode = TRANSLATE;
                break;
            default:
                return;
        }
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
            ic.window.repaint();
        }
    }

    /**
     * Removes the shape name tooltip.
     */
    private void clearShapeName() {
        if(ic.graphic.removeShapeName())
            ic.window.repaint();
    }

}
