package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

public class InputToMenu implements InputToMenuInterface{
    
    private InputController ic = null;
    
    public InputToMenu(InputController ic){
        this.ic = ic;
    }

    @Override
    public void cutShapes() {
        ic.mkListener.cutShapes();
    }
    
    @Override
    public void copyShapes() {
        ic.mkListener.copyShapes();
    }


    @Override
    public void pasteShapes() {
        ic.mkListener.pasteShapes();
    }


    @Override
    public void rotateShapes() {
        ic.mkListener.rotateShapes();
    }

    @Override
    public void scaleShapes() {
        ic.mkListener.scaleShapes();
    }

}
