package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.IAdapterCommunication;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.IDataManager;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.IInputToWindow;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames.IFrame;

/**
 * Implements the FrameInterface for the gui package and also
 * extends the frameGraphic class, which is used for forwarding
 * changes to the graphic package.
 * 
 * @author Patrick
 *
 */
public class Window extends GraphicForward implements IWindow {
    
    private WindowController wc = null;
    private IFrame frame = null;
    
    public Window(IAdapterCommunication adapter){
        super(adapter);
        this.wc = new WindowController(adapter, this);
    }

    public void registerFrameInterface(IFrame frame) {
        this.frame = frame;
    }

    @Override
    public void registerDataManager(IDataManager dm) {
        wc.registerDataManager(dm);
        wc.graphicInterface.registerDataManager(dm);
    }
    
    @Override
    public void registerInputInterface(IInputToWindow input){
        wc.registerInputInterface(input);
    }

    @Override
    public void addMouseListener(MouseInputAdapter mkListener){
        frame.addMouseListener(mkListener);
    }
    
    @Override
    public void addKeyListener(KeyListener mkListener){
        frame.addKeyListener(mkListener);
    }
    
    @Override
    public void addMouseWheelListener(MouseWheelListener mouseWheelListener){
        frame.addMouseWheelListener(mouseWheelListener);
    }

    @Override
    public void repaint() {
        wc.repaint();
    }

    @Override
    public void setVisible(boolean visibility) {
        frame.setMainFrameVisible(visibility);
    }

    @Override
    public void setNewWidth(int width) {
        frame.setNewWidth(width);
        
    }

    @Override
    public void setNewHeight(int height) {
        frame.setNewHeight(height);
    }

    @Override
    public void setNewMinX(int x) {
        frame.setNewMinX(x);
    }

    @Override
    public void setNewMinY(int y) {
        frame.setNewMinY(y);
    }

    @Override
    public IWindowToInput getInputInterface() {
        return wc.inputInterface;
    }

    @Override
    public void setUndoEnabled(boolean b) {
      this.frame.setUndoEnabled(b);
    }

    @Override
    public void setRedoEnabled(boolean b) {
        this.frame.setRedoEnabled(b);
    }

    @Override
    public void updateRightsComponent(long id) {
        this.frame.updateRightsComponent(id);
    }
}
