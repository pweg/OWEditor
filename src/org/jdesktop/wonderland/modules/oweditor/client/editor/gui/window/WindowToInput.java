package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.menu.IMenu;

public class WindowToInput implements IWindowToInput {

    private WindowController wc = null;    
    private DrawingPanel drawingPan = null;
    private IMenu menu = null;
    private stateInput state = null;
    
    public WindowToInput(WindowController fc){
        this.wc = fc;
        registerComponents();
        menu = fc.menu;
    }
    private void registerComponents(){
        drawingPan = wc.drawingPan;
    }

    @Override
    public void repaint() {
        wc.repaint();
    }

    @Override
    public Dimension getPanelSize() {
        return drawingPan.getSize();
    }
    
    @Override
    public void showPopupMenu(int x, int y) {
        //menu.setItemsEnabled(shapesSelected, copyShapesExist);
        drawingPan.repaint();
        menu.showPopup(drawingPan, x, y);
    }

    @Override
    public void changeScale(double scale) {
        double curScale = drawingPan.getScale();
        drawingPan.changeScale(scale);
        drawingPan.changeViewPort(curScale);
    }

    @Override
    public Point getMousePosition() {
        return drawingPan.getMousePosition();
    }

    @Override
    public Rectangle getVisibleRect() {
        return drawingPan.getVisibleRect();
    }

    @Override
    public void scrollRectToVisible(Rectangle rect) {
        drawingPan.scrollRectToVisible(rect);
    }
    @Override
    public void paintMouseCoords(int x, int y) {
        wc.mouseCoords.paintCoordinates(x,y);
    }
    @Override
    public Point revertBack(Point point) {
        return drawingPan.transformBack(point);
    }
    @Override
    public void selectionChange(boolean shapesSelected) {
        menu.setItemsEnabledSelection(shapesSelected);
    }
    @Override
    public void copyChange(boolean copyShapesExist) {
        menu.setItemsEnabledCopy(copyShapesExist);
    }
    
    public void setState(stateInput state){
        this.state  = state;
    }
    
    @Override
    public void translateFinish() {
        //This is build just, if other parts also need translation.
        if(state != null)
            state.finished();
        
    }

}