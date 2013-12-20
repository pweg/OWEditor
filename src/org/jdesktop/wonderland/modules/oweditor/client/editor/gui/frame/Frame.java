package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.AdapterCommunicationInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.InputToFrameInterface;

/**
 * Implements the FrameInterface for the gui package and also
 * extends the frameGraphic class, which is used for forwarding
 * changes to the graphic package.
 * 
 * @author Patrick
 *
 */
public class Frame extends FrameGraphic implements FrameInterface {
    
    private WindowDrawingPanel drawingPan = null;
    private WindowPopupMenu popupMenu = null;
    private MainFrame frame = null;
    private FrameController fc = null;
    
    public Frame(AdapterCommunicationInterface adapter){
        super(adapter);
        this.fc = new FrameController();
        
        fc.graphic = getInterface();
        getInterface().registerFrameInterface(fc.graphicInterface);
        registerComponents();
    }
    
    private void registerComponents(){
        drawingPan = fc.drawingPan;
        popupMenu = fc.popupMenu;
        frame = fc.frame;
    }
    
    @Override
    public void registerInputInterface(InputToFrameInterface input){
        popupMenu.registerInputInterface(input);
    }

    @Override
    public void addMouseListener(MouseInputAdapter mkListener){
        drawingPan.addMouseListener(mkListener);
        drawingPan.addMouseMotionListener(mkListener);
    }
    
    @Override
    public void addKeyListener(KeyListener mkListener){
        frame.addKeyListener(mkListener);
    }
    
    @Override
    public void addMouseWheelListener(MouseWheelListener mouseWheelListener){
        drawingPan.addMouseWheelListener(mouseWheelListener);
    }

    @Override
    public void repaint() {
        fc.repaint();
    }

    @Override
    public void setVisible(boolean visibility) {
       frame.setVisible(visibility);
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
    public FrameToInputInterface getInputInterface() {
        return fc.inputInterface;
    }

    public void setCoordinateTranslator(CoordinateTranslatorInterface coordinateTranslator) {
        fc.setCoordinateTranslator(coordinateTranslator);
    }

}
