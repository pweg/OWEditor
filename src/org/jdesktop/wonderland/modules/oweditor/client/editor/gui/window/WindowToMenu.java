package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

public class WindowToMenu implements WindowToMenuInterface{

    private WindowController wc = null;;

    public WindowToMenu(WindowController wc){
        this.wc  = wc;
    }
    
    @Override
    public void importKmz() {
        wc.frame.showImportFrame();
    }

    @Override
    public void copyShapes() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void cutShapes() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void pasteShapes() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void rotateShapes() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void scaleShapes() {
        // TODO Auto-generated method stub
        
    }

}
