package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.FrameToInputInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicToInputFacadeInterface;

public class InputController {
    
    protected GraphicToInputFacadeInterface graphic = null;
    protected FrameToInputInterface frame = null;
    
    protected MouseAndKeyListener mkListener = null;
    
    protected InputToFrame inputInterface = null;
    
    public InputController(){
        mkListener = new MouseAndKeyListener(this);
        inputInterface = new InputToFrame(this);
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

    public void registerGraphicInterface(GraphicToInputFacadeInterface graphic) {
        this.graphic = graphic;
    }

    public void registerFrameInterface(FrameToInputInterface frame) {
        this.frame = frame;
    }

}
