package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames;

import javax.swing.JFrame;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.WindowToFrameInterface;

public class FrameController implements FrameInterface{
    
    public JFrame mainframe = null;
    public ImportFrame importFrame = null;
    public WindowToFrameInterface window = null;
    
    public FrameController(){
        
    }
    @Override
    public void registerWindow(WindowToFrameInterface window) {
        this.window = window;
    }

    @Override
    public void registerMainFrame(JFrame mainframe) {
        this.mainframe = mainframe;
    }

    @Override
    public void showImportFrame() {
        
        if(importFrame == null){
            importFrame = new ImportFrame();
        }
        importFrame.setVisible(true);
    }


}
