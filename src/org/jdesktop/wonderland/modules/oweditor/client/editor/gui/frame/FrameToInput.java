package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class FrameToInput implements FrameToInputInterface {

    private FrameController fc = null;    
    private WindowDrawingPanel drawingPan = null;
    private WindowPopupMenu popupMenu = null;
    
    public FrameToInput(FrameController fc){
        this.fc = fc;
        registerComponents();
    }
    private void registerComponents(){
        drawingPan = fc.drawingPan;
        popupMenu = fc.popupMenu;
    }

    @Override
    public void repaint() {
        fc.repaint();
    }
    
    @Override
    public int getTranslationX() {
        return fc.getTranslationX();
    }

    @Override
    public int getTranslationY() {
        return fc.getTranslationY();
    }

    @Override
    public Dimension getPanelSize() {
        return drawingPan.getSize();
    }
    @Override
    public double getScale() {
        return drawingPan.getScale();
    }
    
    @Override
    public void showPopupMenu(boolean shapesSelected, boolean copyShapesExist, int x, int y) {
        popupMenu.setItemsEnabled(shapesSelected,copyShapesExist);
        drawingPan.repaint();
        
        popupMenu.show(drawingPan, x, y);
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
    public void changeMouseCoords(int x, int y) {
        fc.mouseCoords.paintCoordinates(drawingPan);
    }

}
