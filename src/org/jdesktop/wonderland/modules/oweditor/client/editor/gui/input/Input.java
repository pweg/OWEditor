package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.IGraphicToInput;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToInput;

/**
 * Implements the input interface and is used to set/get
 * listeners and interfaces. Furthermore it creates the input controller.
 * 
 * @author Patrick
 */
public class Input implements IInput{
    
    private InputController ic = null;
    
    public Input(){
        ic = new InputController();
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

    @Override
    public IInputToWindow getFrameInterface() {
        return ic.inputInterface;
    }

    @Override
    public void registerGraphicInterface(IGraphicToInput graphic) {
        ic.registerGraphicInterface(graphic);
    }

    @Override
    public void registerFrameInterface(IWindowToInput frame) {
       ic.registerFrameInterface(frame);
    }

}
