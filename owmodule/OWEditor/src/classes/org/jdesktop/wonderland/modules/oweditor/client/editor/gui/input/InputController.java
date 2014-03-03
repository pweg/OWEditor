package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.IGraphicToInput;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToInput;

public class InputController {
    
    protected IGraphicToInput graphic = null;
    protected IWindowToInput window = null;
    
    protected MouseAndKeyListener mkListener = null;
    
    protected InputToWindow inputInterface = null;
    
    public InputController(){
        mkListener = new MouseAndKeyListener(this);
        inputInterface = new InputToWindow(this);
    }

    protected MouseInputAdapter getMouseListener(){
        return mkListener;
    }
    
    protected KeyListener getKeyListener(){
        return mkListener;
    }
    
    protected MouseWheelListener getMouseWheelListener(){
        return mkListener;
    }
    
    public void setRotationCenterStrategy(){
        mkListener.setRotationCenterStrategy();
        
    }

    public void registerGraphicInterface(IGraphicToInput graphic) {
        this.graphic = graphic;
    }

    public void registerFrameInterface(IWindowToInput frame) {
        this.window = frame;
    }

}
