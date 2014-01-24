package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames;

import javax.swing.JFrame;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.WindowToFrameInterface;

public interface FrameInterface {
    
    public void registerWindow(WindowToFrameInterface window);
    
    public void registerMainFrame(JFrame mainframe);
    
    /**
     * Shows the add file frame.
     */
    public void showImportFrame();

}
