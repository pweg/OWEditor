package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

public class WindowToFrame implements IWindowToFrame{

    public WindowController wc = null;
    
    public WindowToFrame(WindowController wc) {
        this.wc = wc;
    }

    @Override
    public void importKMZ(String url) {
        // TODO Auto-generated method stub
        
    }

}
