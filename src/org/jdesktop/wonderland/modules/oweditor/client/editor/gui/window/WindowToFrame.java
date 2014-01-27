package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

public class WindowToFrame implements IWindowToFrame{

    public WindowController wc = null;
    
    public WindowToFrame(WindowController wc) {
        this.wc = wc;
    }

    @Override
    public int[] loadKMZ(String url) {        
        return wc.adapter.loadKMZ(url);
    }

}
