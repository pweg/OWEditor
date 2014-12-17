package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

/**
 * Implements the input to window interface. Is only used 
 * to set the mode on the mk listener.
 * 
 * @author Patrick
 */
public class InputToWindow implements IInputToWindow{
    
    private InputController ic = null;
    
    public InputToWindow(InputController ic){
        this.ic = ic;
    }
    
    @Override
    public void setInputMode(byte mode) {
        ic.mkListener.setMode(mode);
    }

    @Override
    public void keyPressed(int key) {
        ic.mkListener.keyPressed(key);
    }

    @Override
    public MouseInputAdapter getMouseListener() {
        return ic.getMouseListener();
    }

    @Override
    public KeyListener getKeyListener() {
        return ic.getKeyListener();
    }

    @Override
    public MouseWheelListener getMouseWheelListener() {
        return ic.getMouseWheelListener();
    }

}
