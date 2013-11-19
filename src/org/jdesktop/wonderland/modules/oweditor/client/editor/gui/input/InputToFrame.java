package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

public class InputToFrame implements InputToFrameInterface{
    
    private InputController ic = null;
    
    public InputToFrame(InputController ic){
        this.ic = ic;
    }


    @Override
    public void setRotationStrategy(){
        ic.setRotationStrategy();
    }
    
    @Override
    public void setRotationCenterStrategy(){
        ic.setRotationCenterStrategy();
        
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

}
