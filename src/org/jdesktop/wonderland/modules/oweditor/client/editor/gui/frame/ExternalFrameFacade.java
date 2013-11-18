package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JScrollPane;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.MouseAndKeyListener;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape.ExternalShapeFacadeInterface;

public class ExternalFrameFacade implements ExternalFrameFacadeInterface{
    
    protected JScrollPane mainScrollPanel = null;
    protected WindowDrawingPanel drawingPan = null;
    protected WindowPopupMenu popupMenu = null;
    protected MainFrame frame = null;
    protected FrameController fc = null;
    
    public ExternalFrameFacade(){
        this.fc = new FrameController();
        registerComponents();
        
    }
    private void registerComponents(){
        mainScrollPanel = fc.mainScrollPanel;
        drawingPan = fc.drawingPan;
        popupMenu = fc.popupMenu;
        frame = fc.frame;
    }

    public void addMouseListenerToDrawingPan(MouseAndKeyListener mkListener){
        popupMenu.registerMouseListener(mkListener);
        
        drawingPan.addMouseListener(mkListener);
        drawingPan.addMouseMotionListener(mkListener);
        drawingPan.addMouseWheelListener(mkListener);
        frame.addKeyListener(mkListener);
    }
    
    
    public WindowDrawingPanel getDrawingPan(){
        return drawingPan;
    }

    @Override
    public void registerShapeInterface(ExternalShapeFacadeInterface esmi) {
        fc.shapes = esmi;
    }

    @Override
    public void repaint() {
        drawingPan.repaint();
    }

    @Override
    public void setVisible(boolean visibility) {
       frame.setVisible(visibility);
    }

    @Override
    public double getScale() {
        return drawingPan.getScale();
    }

    @Override
    public int getTranslationX() {
        return drawingPan.getTranslationX();
    }

    @Override
    public int getTranslationY() {
        return drawingPan.getTranslationY();
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
    public void setNewWidth(int width) {
        drawingPan.setNewWidth(width);
        
    }

    @Override
    public void setNewHeight(int height) {
        drawingPan.setNewHeight(height);
    }

    @Override
    public void setNewMinX(int x) {
        drawingPan.setNewMinX(x);
    }

    @Override
    public void setNewMinY(int y) {
        drawingPan.setNewMinY(y);
    }

    @Override
    public ExternalFrameToShapeInterface getShapeInterface() {
        return fc.efs;
    }

}
