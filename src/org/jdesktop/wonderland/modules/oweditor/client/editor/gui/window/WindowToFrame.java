package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.IInputToWindow;

public class WindowToFrame implements IWindowToFrame{

    public WindowController wc = null;
    
    public WindowToFrame(WindowController wc) {
        this.wc = wc;
    }

    @Override
    public int[] loadKMZ(String url) {        
        return wc.adapter.loadKMZ(url);
    }

    @Override
    public void chooseLocation(int width, int height, double rotation,
            double scale) {
        wc.graphic.createDraggingRect(width, height, 0, 0, rotation, scale);
        wc.inputInterface.setMode(WindowToInput.IMPORT);
        wc.input.setInputMode(IInputToWindow.TRANSLATE);
    }

}
