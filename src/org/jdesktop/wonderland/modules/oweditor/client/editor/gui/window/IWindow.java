package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.IDataManager;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.IInputToWindow;

/**
 * Interface for the gui package, which extends the 
 * interface, which forwards changes to the graphics.
 * 
 * @author Patrick
 *
 */
public interface IWindow extends IGraphicForward{
    
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
    public void registerInputInterface(IInputToWindow input);
    
    /**
     * Returns the interface for the input package.
     * 
     * @return The interface.
     */
    public IWindowToInput getInputInterface();
    
    /**
     * This repaints ONLY the drawing panel, nothing else.
     */
    public void repaint();
    
    /**
     * Sets the window visible.
     * 
     * @param visibility True = visible, false = not visible.
     */
    public void setVisible(boolean visibility);
    
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
    public void registerDataManager(IDataManager dm);

    /**
     * Sets the undo option enabled/disabled.
     * 
     * @param b If true, undo is enabled, if false
     * undo is disabled.
     */
    public void setUndoEnabled(boolean b);

    /**
     * Sets the redo option enabled/disabled.
     * 
     * @param b If true, redo is enabled, if false
     * redo is disabled.
     */
    public void setRedoEnabled(boolean b);

    /**
     * Updates for created rights.
     * 
     * @param id The object id.
     */
    public void updateRightsComponent(long id);
    

}
