package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * The interface implementation for the input package.
 * 
 * @author Patrick
 *
 */
public class WindowToInput implements IWindowToInput {

    private WindowController wc = null;    
    private stateInput state = null;

    protected MouseCoordinates mouseCoords = null;
    
    public WindowToInput(WindowController wc){
        this.wc = wc;
        
        mouseCoords = new MouseCoordinates(wc);
    }
    
    /*public void registerDrawingPan(IDrawingPanel drawingPan){
        this.drawingPan = drawingPan;
    }*/

    @Override
    public void repaint() {
        wc.repaint();
    }

    @Override
    public Dimension getPanelSize() {
        return wc.frame.getDrawingPan().getSize();
    }
    
    @Override
    public void showPopupMenu(int x, int y) {
        //menu.setItemsEnabled(shapesSelected, copyShapesExist);
        wc.repaint();
        wc.menu.showPopup((Component)wc.frame.getDrawingPan(), x, y);
    }

    @Override
    public void changeScale(double scale) {
        //double curScale = drawingPan.getScale();
        wc.frame.getDrawingPan().changeScale(scale);
        //drawingPan.changeViewPort(curScale);
    }

    @Override
    public Point getMousePosition() {
        return wc.frame.getDrawingPan().getMousePosition();
    }

    @Override
    public Rectangle getVisibleRect() {
        return wc.frame.getDrawingPan().getVisibleRect();
    }

    @Override
    public void scrollRectToVisible(Rectangle rect) {
        wc.frame.getDrawingPan().scrollRectToVisible(rect);
    }
    @Override
    public void paintMouseCoords(int x, int y) {
        mouseCoords.paintCoordinates(x,y);
    }
    @Override
    public Point revertBack(Point point) {
        return wc.frame.getDrawingPan().transformBack(point);
    }
    @Override
    public void selectionChange(boolean shapesSelected) {
        wc.menu.setItemsEnabledSelection(shapesSelected);
    }
    @Override
    public void copyChange(boolean copyShapesExist) {
        wc.menu.setItemsEnabledCopy(copyShapesExist);
    }
    
    /**
     * Sets the input state type. Currently only used for 
     * the import state.
     * 
     * @param state The state
     */
    public void setState(stateInput state){
        this.state  = state;
    }
    
    @Override
    public void translateFinish() {
        //This is build just, if other parts also need translation.
        if(state != null)
            state.finished();
    }

    @Override
    public void setToBGVisible(boolean b) {
       wc.menu.setToBackgroundVisible(b);
    }

    @Override
    public void setPropertiesVisible(boolean b) {
        wc.adapter.updateObjects(wc.graphic.getSelectedShapes());
        wc.frame.setPropertiesVisible(b);
    }

    @Override
    public void setTransformBarVisible(boolean b) {
        wc.frame.setTransformBarVisible(b);
    }

    @Override
    public void clearToolbarText() {
        wc.frame.clearToolbarText();
    }

}
