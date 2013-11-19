package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.InputToFrameInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape.ExternalShapeToFrameInterface;

public interface FrameInterface {
    
    /**
     * Adds a mouse and key listener to the drawing panel
     * 
     * @param mouseInputAdapter the listener
     */
    public void addMouseListener(MouseInputAdapter mouseInputAdapter);
    
    /**
     * Adds
     * @param keyListener
     */
    public void addKeyListener(KeyListener keyListener);
    
    public void addMouseWheelListener(MouseWheelListener mouseWheelListener);
    
    /**
     * returns the drawing panel, which contains the 2d graphics, like shapes etc..
     * 
     * @return the drawing panel
     */
    public WindowDrawingPanel getDrawingPan();

    /**
     * registers the external shape facade to the frame controller.
     * 
     * @param esmi the shape facade.
     */
    public void registerShapeInterface(ExternalShapeToFrameInterface shape);
    
    /**
     * This repaints ONLY the drawing panel, nothing else.
     */
    public void repaint();
    
    public void setVisible(boolean visibility);
    
    public int getTranslationX();
    
    public int getTranslationY();

    public void setNewWidth(int width);

    public void setNewHeight(int height);

    public void setNewMinX(int x);

    public void setNewMinY(int y);
    
    public FrameToShapeInterface getShapeInterface();
    
    public FrameToInputInterface getInputInterface();

    public void registerInputInterface(InputToFrameInterface input);
    

}
