package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.InputToFrameInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape.ExternalShapeToFrameInterface;

public interface FrameInterface {
    
    /**
     * Adds a mouse to the drawing panel
     * 
     * @param mouseInputAdapter the listener
     */
    public void addMouseListener(MouseInputAdapter mouseInputAdapter);
    
    /**
     * Adds a key listener to the frame.
     * 
     * @param keyListener the listener
     */
    public void addKeyListener(KeyListener keyListener);
    
    /**
     * Adds a mouse wheel listener to the drawing panel
     * 
     * @param mouseWheelListener the listener
     */
    public void addMouseWheelListener(MouseWheelListener mouseWheelListener);

    /**
     * Registers the shape interface to the frame controller.
     * 
     * @param esmi the shape facade.
     */
    public void registerShapeInterface(ExternalShapeToFrameInterface shape);
    
    /**
     * Registers the input interface to the frame controller.
     * 
     * @param esmi the shape facade.
     */
    public void registerInputInterface(InputToFrameInterface input);
    
    public FrameToShapeInterface getShapeInterface();
    
    public FrameToInputInterface getInputInterface();
    /**
     * This repaints ONLY the drawing panel, nothing else.
     */
    public void repaint();
    
    public void setVisible(boolean visibility);
    
    /**
     * Returns the translation value in x direction.
     * This translation value is used to move the whole 2d graph away
     * from the minus coordinates, to fit in the 0,0 coordinates of the shapes,
     * which do not allow for minus coordinates.
     * 
     * @return the value of the graph translation.
     */
    public int getTranslationX();
    
    /**
     * Returns the translation value in y direction.
     * This translation value is used to move the whole 2d graph away
     * from the minus coordinates, to fit in the 0,0 coordinates of the shapes,
     * which do not allow for minus coordinates.
     * 
     * @return the value of the graph translation.
     */
    public int getTranslationY();

    public void setNewWidth(int width);

    public void setNewHeight(int height);

    public void setNewMinX(int x);

    public void setNewMinY(int y);

    

}