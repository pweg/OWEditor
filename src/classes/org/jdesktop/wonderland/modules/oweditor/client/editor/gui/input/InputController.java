package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToInput;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.IGraphicToInput;

/**
 * The input controller builds the input package.
 * 
 * @author Patrick
 */
public class InputController {
    
    protected MouseAndKeyListener mkListener = null;
    protected InputToWindow inputInterface = null;
    
    public InputController(){
        mkListener = new MouseAndKeyListener();
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
    
    /**
     * Sets the rotation strategy, where a rotation around the
     * center is done.
     */
    public void setRotationCenterStrategy(){
        mkListener.setRotationCenterStrategy();
    }

    public void registerGraphicInterface(IGraphicToInput graphic) {
        mkListener.registerGraphic(graphic);
    }

    public void registerFrameInterface(IWindowToInput window) {
        mkListener.registerWindow(window);
    }

}
