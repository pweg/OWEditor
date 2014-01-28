package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames;

import javax.swing.JFrame;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToFrame;

public class FrameController implements IFrame{
    
    public JFrame mainframe = null;
    public ImportFrame importFrame = null;
    public IWindowToFrame window = null;
    
    public FrameController(){
        
    }
    @Override
    public void registerWindow(IWindowToFrame window) {
        this.window = window;
    }

    @Override
    public void registerMainFrame(JFrame mainframe) {
        this.mainframe = mainframe;
    }

    @Override
    public void showImportFrame() {
        
        if(importFrame == null){
            importFrame = new ImportFrame(this);
        }
        importFrame.setVisible(true);
    }
    @Override
    public void setImportLocation(double x, double y) {
        if(importFrame == null)
            return;
        
        importFrame.setLocation(x,y);
    }


}
