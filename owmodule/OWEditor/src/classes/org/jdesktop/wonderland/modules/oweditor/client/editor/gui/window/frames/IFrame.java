package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.JMenuBar;
import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.BottomToolBar;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToFrame;

public interface IFrame {
    
    public void registerWindow(IWindowToFrame window);

    /**
     * Shows the main frame, or hides it.
     * 
     * @param visibility If true, mainframe will be shown,
     * if false mainframe will be invisible.
     */
    void setMainFrameVisible(boolean visibility);
    
    /**
     * Shows the add file frame.
     */
    public void showImportFrame();

    /**
     * Sets the position fields of the import frame
     * 
     * @param x The x position.
     * @param y The y position.
     */
    public void setImportLocation(double x, double y);

    /**
     * Adds a mouse listener to the drawing panel.
     * 
     * @param mkListener The mouseListener
     */
    void addMouseListener(MouseInputAdapter mkListener);

    /**
     * Adds a key listener to the mainframe.
     * 
     * @param mkListener The key listener.
     */
    void addKeyListener(KeyListener mkListener);

    /**
     * Adds a mouse wheel listener to the drawing panel.
     * 
     * @param mouseWheelListener 
     */
    void addMouseWheelListener(MouseWheelListener mouseWheelListener);

    /**
     * Sets a new world width for the drawing panel.
     * 
     * @param width The new width.
     */
    void setNewWidth(int width);

    /**
     * Sets a new world height for the drawing panel.
     * 
     * @param height The new height.
     */
    void setNewHeight(int height);

    /**
     * Sets a new minimal x for the drawing panel,
     * which results in a size change.
     * 
     * @param x The new minimal x.
     */
    void setNewMinX(int x);

    /**
     * Sets a new minimal y for the drawing panel,
     * which results in a size change.
     * 
     * @param x The new minimal y.
     */
    void setNewMinY(int y);

    /**
     * Repaints the drawing panel.
     */
    public void repaint();

    /**
     * Returns the drawing panel.
     * 
     * @return The drawing panel instance.
     */
    public IDrawingPanel getDrawingPan();

    /**
     * Sets a menu bar.
     * 
     * @param buildMenubar The menu bar instance.
     */
    public void setJMenuBar(JMenuBar buildMenubar);

    /**
     * Returns the bottom toolbar.
     * 
     * @return A toolbar instance.
     */
    public BottomToolBar getBottomToolBar();

}