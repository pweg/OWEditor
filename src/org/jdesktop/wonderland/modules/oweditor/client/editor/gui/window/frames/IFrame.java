package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames;

import javax.swing.JFrame;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToFrame;

public interface IFrame {
    
    public void registerWindow(IWindowToFrame window);
    
    public void registerMainFrame(JFrame mainframe);
    
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
     * Repaints the frame.
     */
    //public void repaint();

}
