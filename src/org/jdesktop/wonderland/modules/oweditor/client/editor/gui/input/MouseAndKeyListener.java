package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUISettings;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToInput;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.IGraphicToInput;

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
    
    protected IGraphicToInput graphic = null;
    protected IWindowToInput window = null;
    
    private boolean shiftPressed = false;
    
    private byte mode = 0;
    //private boolean copy = false;
    //private boolean rotation = false;
    
    public MouseAndKeyListener(){
    }
    
    public void registerWindow(IWindowToInput window){
        this.window = window;
    }

    public void registerGraphic(IGraphicToInput graphic){
        this.graphic = graphic;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        
        Point p = window.revertBack(e.getPoint());
        
        //operations with shift pressed
        if(shiftPressed && mode == NOMODE){
            if(e.getButton() ==  MouseEvent.BUTTON1){
                //switch selection, or selection rectangle
                //in shift mode
                if(!graphic.selectionSwitch(p, true)){
                    strategy = new mlSelectionRectStrategy(this);
                    strategy.mousePressed(p);
                }           
            }
        }else{
             //translating/selection rectangle
             if(e.getButton() ==  MouseEvent.BUTTON1){
                 if(mode == NOMODE){//translate
                     if(graphic.isMouseInObject(p)){
                         graphic.selectionSwitch(p, false);
                     }else{//selection rectangle
                         graphic.clearCurSelection();
                         strategy = new mlSelectionRectStrategy(this);
                     }
                 }
                 //paste/insertion
                 else if(mode == PASTE){
                 }
                 //rotation
                 else if(mode == ROTATE){
                     if(graphic.isMouseInBorder(p))
                         strategy = new mlRotationStrategy(this);
                     else if (graphic.isMouseInBorderCenter(p)){
                         setRotationCenterStrategy();
                     }
                 }
                 //scale
                 else if(mode == SCALE){
                     if(graphic.isMouseInBorder(p))
                         strategy = new mlScaleStrategy(this);
                 }

             if(strategy != null)
                 strategy.mousePressed(p);
             //this has to be made after strategy mouse press to ensure
             //everything important already happened.
             window.selectionChange(graphic.isShapeSelected());
             }
             //Panning
             else if ((e.getButton() ==  MouseEvent.BUTTON2 || e.getButton() ==  MouseEvent.BUTTON3)){
                 if(mode == NOMODE || mode == PASTE)
                     graphic.cleanHelpingShapes();
                 strategy = new mlPanStrategy(window);
                 //mlPanStrategy needs the panel coordinates in order to
                 //work properly.
                 strategy.mousePressed(e.getPoint());
             }else{
                 clear();
                 
             }
        }
        if(strategy != null)
            window.repaint();    
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {

        Point p = e.getPoint();
        
        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
            if(mode == NOMODE){//properties
                p = window.revertBack(p);
                if(graphic.isMouseInObject(p)){
                    graphic.clearCurSelection();
                    graphic.selectionSwitch(p, false);
                    window.setPropertiesVisible(true);
                }
            }
        }
        
        if (e.getButton() ==  MouseEvent.BUTTON3 && mode <= PASTE){            
            new mlPopupStrategy(this).mousePressed(p);
            window.repaint();  
        }
     }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point p = window.revertBack(e.getPoint());
        
        window.paintMouseCoords(p.x, p.y);
        writeShapeName(p);
        
        if(strategy == null && mode == NOMODE){
            if(graphic.isMouseInObject(p)){
                
                //creates the dragging shapes
                graphic.translateIntialize(p);
                
                strategy = new mlTranslateDragStrategy(this);
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
        window.repaint();  
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

        window.changeScale(GUISettings.ZOOMSPEED * -(double)e.getWheelRotation());
        
        clearShapeName();
        window.repaint();  
    }
    
    @Override
    public void mouseMoved(MouseEvent e){
        Point p = window.revertBack(e.getPoint());
        
        window.paintMouseCoords(p.x, p.y);

        writeShapeName(p);
        
        if(strategy == null){
            return;
        }
        strategy.mouseMoved(p);
        window.repaint();  
    }
    
    @Override
    public void mouseReleased(MouseEvent e){

        Point p = window.revertBack(e.getPoint());
        
        if(strategy == null){
            window.repaint();
            return;
        }
        strategy.mouseReleased(p);
        strategy = null;
        window.repaint();  
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyPressed(e.getKeyCode());
    }
    
    public void keyPressed(int key){
        if (key == KeyEvent.VK_SHIFT) {
            shiftPressed = true;
        }
        //DELETE
        else if(key == KeyEvent.VK_DELETE){
            graphic.deleteCurrentSelection();
        }
        //ESCAPE
        else if(key == KeyEvent.VK_ESCAPE){
            clear();
            window.setTransformBarVisible(false);
        }
        //ENTER
        else if (key == KeyEvent.VK_ENTER) {
            //FINISH ROTATION
            if(mode == ROTATE){
                graphic.rotateFinished();
                clear();
            }
            //FINISH SCALING
            else if(mode == SCALE){
                graphic.scaleFinished();
                clear();
            }
            window.setTransformBarVisible(false);
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
        graphic.cleanAll();
        if(strategy != null){
            if(strategy instanceof mlSelectionRectStrategy)
                strategy = null;
            else if(strategy instanceof mlTranslateStrategy){
                ((mlTranslateStrategy)strategy).reset();
            }
        }
        window.clearToolbarText();
        window.repaint();
    }
    
    /**
     * Sets a new strategy for the rotation center.
     */
    public void setRotationCenterStrategy(){
        strategy = new mlRotationCenterStrategy(this);
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
                pasteStrategy = new mlTranslateStrategy(this, mlTranslateStrategy.PASTE);
                strategy = pasteStrategy;
                break;
            case(CUT):
                pasteStrategy = new mlTranslateStrategy(this, mlTranslateStrategy.PASTE);
                strategy = pasteStrategy;
                graphic.deleteCurrentSelection();
                break;
            case(PASTE):
                if (pasteStrategy == null)
                    return;

                graphic.pasteInitialize();
                pasteStrategy.setDragging(false);
                strategy = pasteStrategy;
                
                strategy.mousePressed(null);
                strategy.mouseMoved(window.revertBack(window.getMousePosition()));
                this.mode = PASTE;
                break;
            case(ROTATE):
                strategy = null;
                //could be done in WindowToMenu, but don't want to split 
                //from other rotation operations
                graphic.rotationInitialize();
                this.mode = ROTATE;
                break;
            case(SCALE):
                strategy = null;
                //could be done in WindowToMenu, but don't want to split 
                //from other scaling operations
                graphic.scaleInitialize();
                this.mode = SCALE;
                break;
            case(TRANSLATE):
                strategy = new mlTranslateStrategy(this, mlTranslateStrategy.TRANSLATE);
                strategy.mousePressed(null);
                strategy.mouseMoved(window.revertBack(window.getMousePosition()));
            
                this.mode = TRANSLATE;
                break;
            default:
        }
    }
    
    /**
     * Check for name tooltip. If the graphic package 
     * returns true, a repaint is necessary. 
     * 
     * @param p The current mouse point.
     */
    private void writeShapeName(Point p){
        String name = graphic.getShapeName(p);
        
        //only repaint, when necessary, ergo when the name
        //tooltip moves or has to be shown/hidden.
        if(graphic.paintShapeName(p, name)){
            window.repaint();
        }
    }

    /**
     * Removes the shape name tooltip.
     */
    private void clearShapeName() {
        if(graphic.removeShapeName())
            window.repaint();
    }

}
