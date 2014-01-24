package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

public class InputToWindow implements IInputToWindow{
    
    private InputController ic = null;
    
    public InputToWindow(InputController ic){
        this.ic = ic;
    }
    
    @Override
    public void setInputMode(byte mode) {
        ic.mkListener.setMode(mode);
    }

}
