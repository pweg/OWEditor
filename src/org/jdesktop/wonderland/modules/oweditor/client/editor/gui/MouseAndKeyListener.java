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
                
                if(!gc.esmi.selectionSwitch(p)){
                    strategy = new mlSelectionRectShiftStrategy(gc);
                    strategy.mousePressed(e.getPoint());
                } 
                gc.drawingPan.repaint();               
            }
        }else{
             //dragging shapes/selection
             if(e.getButton() ==  MouseEvent.BUTTON1 && !lockLeftMousePressed){
                 Point p = e.getPoint();
                    
                 if(gc.esmi.isMouseInObject(p)){
                     strategy = new mlDragAndDropStrategy(gc);
                     strategy.mousePressed(e.getPoint());
                 }else{
                     strategy = new mlSelectionRectStrategy(gc);
                     strategy.mousePressed(e.getPoint());
                 }
             }
             //paste insertion
             else if(lockLeftMousePressed && e.getButton() ==  MouseEvent.BUTTON1 ){
                 strategy.mousePressed(e.getPoint());
             }
             //Panning
             else if (e.getButton() ==  MouseEvent.BUTTON2 || e.getButton() ==  MouseEvent.BUTTON3){
                 gc.esmi.clean();
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
            }else if(e.getKeyCode() == KeyEvent.VK_V){
                pasteShapes(); 
            }else if(e.getKeyCode() == KeyEvent.VK_X){
                
            }
        }
    }
    
    private void clear(){
        gc.esmi.clean();
        gc.drawingPan.repaint();
        releaseLockLeftMouse();
        strategy = null;
    }
    
    public void copyShapes(){
        copyPoint = gc.esmi.copyInitialize();
        copyPoint.x += gc.drawingPan.getTranslationX();
        copyPoint.y += gc.drawingPan.getTranslationY();
        
    }
    
    public void pasteShapes(){
        if(copyPoint == null)
            return;
        
        strategy = new mlPasteStrategy(gc, this);
        scale = gc.drawingPan.getScale();
        
        int x = (int) Math.round(copyPoint.x * scale);
        int y = (int) Math.round(copyPoint.y * scale);
        Point p = new Point(x,y);

        strategy.mousePressed(p);
        strategy.mouseMoved(gc.drawingPan.getMousePosition());
        lockLeftMousePressed = true;
    }
    
    public void releaseLockLeftMouse(){
        lockLeftMousePressed = false;
    }

    public void rotateShapes() {
        gc.esmi.rotationInitialize();
        gc.drawingPan.repaint();
    }

}
