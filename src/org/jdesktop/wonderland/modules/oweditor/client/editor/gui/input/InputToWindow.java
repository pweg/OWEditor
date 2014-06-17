package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

/**
 * Implements the input to window interface. Is only used 
 * to set the mode on the mk listener.
 * 
 * @author Patrick
 */
public class InputToWindow implements IInputToWindow{
    
    private MouseAndKeyListener mkListener = null;
    
    public InputToWindow(MouseAndKeyListener mkListener){
        this.mkListener = mkListener;
    }
    
    @Override
    public void setInputMode(byte mode) {
        mkListener.setMode(mode);
    }

    @Override
    public void keyPressed(int key) {
        mkListener.keyPressed(key);
    }

}
