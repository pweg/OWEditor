package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.IInputToWindow;

public class WindowToMenu implements IWindowToMenu{

    private WindowController wc = null;

    public WindowToMenu(WindowController wc){
        this.wc  = wc;
    }
    
    @Override
    public void importKmz() {
        wc.frame.showImportFrame();
    }

    @Override
    public void copyShapes() {
        wc.input.setInputMode(IInputToWindow.COPY);
        wc.menu.setItemsEnabledCopy(true);
        wc.repaint();
    }

    @Override
    public void cutShapes() {
        wc.input.setInputMode(IInputToWindow.CUT);
        wc.repaint();
        
    }

    @Override
    public void pasteShapes() {
        wc.input.setInputMode(IInputToWindow.PASTE);
        wc.repaint();
        
    }

    @Override
    public void rotateShapes() {
        wc.input.setInputMode(IInputToWindow.ROTATE);
        wc.repaint();
        
    }

    @Override
    public void scaleShapes() {
        wc.input.setInputMode(IInputToWindow.SCALE);
        wc.repaint();
    }

}