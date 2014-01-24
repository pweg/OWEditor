package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

public class WindowToFrame implements WindowToFrameInterface{

    public WindowController wc = null;
    
    public WindowToFrame(WindowController wc) {
        this.wc = wc;
    }

}
