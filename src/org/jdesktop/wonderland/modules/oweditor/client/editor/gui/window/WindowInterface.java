package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.InputToMenuInterface;

/**
 * Interface for the gui package, which extends the 
 * interface, which forwards changes to the graphics.
 * 
 * @author Patrick
 *
 */
public interface WindowInterface extends GraphicForwardInterface{
    
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
     * Registers the input interface to the frame controller.
     * 
     * @param input The input interface for the frame package.
     */
    public void registerInputInterface(InputToMenuInterface input);
    
    public WindowToInputInterface getInputInterface();
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

    /**
     * This sets a new width for the world represented in the 
     * drawing panel
     * 
     * @param width The new width.
     */
    public void setNewWidth(int width);

    /**
     * This sets a new height for the world represented in the 
     * drawing panel
     * 
     * @param height The new height.
     */

    public void setNewHeight(int height);

    /**
     * This sets a new minimal x for the world represented
     * in the drawing panel. This is needed for viewport changes
     * in order to maintain the current view.
     *  
     * @param x The new minimal x.
     */
    public void setNewMinX(int x);

    /**
     * This sets a new minimal y for the world represented
     * in the drawing panel. This is needed for viewport changes
     * in order to maintain the current view.
     *  
     * @param y The new minimal y.
     */
    public void setNewMinY(int y);

    /**
     * Registers a data manager interface instance.
     * 
     * @param dm The instance
     */
    public void registerDataManager(DataObjectManagerGUIInterface dm);


    

}
