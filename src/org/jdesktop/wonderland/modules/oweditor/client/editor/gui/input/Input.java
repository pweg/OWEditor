package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

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
    public void registerGraphicInterface(IGraphicToInput graphic) {
        ic.registerGraphicInterface(graphic);
    }

    @Override
    public void registerWindowInterface(IWindowToInput frame) {
       ic.registerFrameInterface(frame);
    }

    @Override
    public IInputToWindow getWindowInterface() {
        return ic.inputInterface;
    }

}
