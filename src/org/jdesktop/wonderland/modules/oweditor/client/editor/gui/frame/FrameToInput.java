package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.menu.MenuInterface;

public class FrameToInput implements FrameToInputInterface {

    private FrameController fc = null;    
    private WindowDrawingPanel drawingPan = null;
    private MenuInterface menu = null;
    
    public FrameToInput(FrameController fc){
        this.fc = fc;
        registerComponents();
        menu = fc.menu;
    }
    private void registerComponents(){
        drawingPan = fc.drawingPan;
    }

    @Override
    public void repaint() {
        fc.repaint();
    }

    @Override
    public Dimension getPanelSize() {
        return drawingPan.getSize();
    }
    
    @Override
    public void showPopupMenu(boolean shapesSelected, boolean copyShapesExist, int x, int y) {
        menu.setItemsEnabled(shapesSelected, copyShapesExist);
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
        fc.mouseCoords.paintCoordinates(x,y);
    }
    @Override
    public Point revertBack(Point point) {
        return drawingPan.revertBack(point);
    }

}
