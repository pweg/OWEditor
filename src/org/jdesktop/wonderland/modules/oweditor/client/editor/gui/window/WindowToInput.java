package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.menu.IMenu;

public class WindowToInput implements IWindowToInput {
    
    protected static final byte NOMODE = 0;
    protected static final byte IMPORT = 1;

    private WindowController fc = null;    
    private DrawingPanel drawingPan = null;
    private IMenu menu = null;
    private byte mode = 0;
    private DataObjectManagerGUIInterface dm = null;
    
    public WindowToInput(WindowController fc){
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
        fc.mouseCoords.paintCoordinates(x,y);
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
    
    public void setMode(byte mode){
        this.mode  = mode;
    }
    
    @Override
    public void translateFinish() {
        //This is build just, if other parts also need translation.
        switch(mode){
            case IMPORT:
                Point coords = fc.graphic.getDraggingCoords();
                Point2D coords_origin = dm.transformCoordsBack(coords);
                
                fc.frame.setImportLocation(coords_origin.getX(), coords_origin.getY());
                fc.frame.showImportFrame();
                
                break;
            default:
                return;
        }
    }
    public void registerDataManager(DataObjectManagerGUIInterface dm) {
        this.dm = dm;
    }

}
