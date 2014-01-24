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
     * Repaints the frame.
     */
    //public void repaint();

}
