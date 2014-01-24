package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicToInputInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.WindowToInputInterface;

public class InputController {
    
    protected GraphicToInputInterface graphic = null;
    protected WindowToInputInterface frame = null;
    
    protected MouseAndKeyListener mkListener = null;
    
    protected InputToMenu inputInterface = null;
    
    public InputController(){
        mkListener = new MouseAndKeyListener(this);
        inputInterface = new InputToMenu(this);
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

    public void registerGraphicInterface(GraphicToInputInterface graphic) {
        this.graphic = graphic;
    }

    public void registerFrameInterface(WindowToInputInterface frame) {
        this.frame = frame;
    }

}
