package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.IAdapterCommunication;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.IInputToWindow;

/**
 * Implements the FrameInterface for the gui package and also
 * extends the frameGraphic class, which is used for forwarding
 * changes to the graphic package.
 * 
 * @author Patrick
 *
 */
public class Window extends GraphicForward implements WindowInterface {
    
    private DrawingPanel drawingPan = null;
    private MainFrame mainframe = null;
    private WindowController fc = null;
    
    public Window(IAdapterCommunication adapter){
        super(adapter);
        this.fc = new WindowController(adapter, this);
        
    }
    
    public void registerComponents(DrawingPanel drawingPan, MainFrame mainframe){
        this.drawingPan = drawingPan;
        this.mainframe = mainframe;
    }
    
    @Override
    public void registerInputInterface(IInputToWindow input){
        fc.registerInputInterface(input);
    }

    @Override
    public void addMouseListener(MouseInputAdapter mkListener){
        drawingPan.addMouseListener(mkListener);
        drawingPan.addMouseMotionListener(mkListener);
    }
    
    @Override
    public void addKeyListener(KeyListener mkListener){
        mainframe.addKeyListener(mkListener);
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
       mainframe.setVisible(visibility);
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
    public IWindowToInput getInputInterface() {
        return fc.inputInterface;
    }

    @Override
    public void registerDataManager(DataObjectManagerGUIInterface dm) {
        fc.registerDataManager(dm);
    }

}
