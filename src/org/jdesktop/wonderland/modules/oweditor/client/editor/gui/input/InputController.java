package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.FrameToInputInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape.ExternalShapeToInputFacadeInterface;

public class InputController {
    
    protected ExternalShapeToInputFacadeInterface shape = null;
    protected FrameToInputInterface frame = null;
    
    protected MouseAndKeyListener mkListener = null;
    
    protected InputToFrame inputInterface = null;
    protected InputToShape shapeInterface = null;
    
    public InputController(){
        mkListener = new MouseAndKeyListener(this);
        inputInterface = new InputToFrame(this);
        shapeInterface = new InputToShape(this);
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

    public void setRotationStrategy(){
        mkListener.setRotationStrategy();
    }
    
    public void setRotationCenterStrategy(){
        mkListener.setRotationCenterStrategy();
        
    }

    public void registerShapeInterface(ExternalShapeToInputFacadeInterface shape) {
        this.shape = shape;
    }

    public void registerFrameInterface(FrameToInputInterface frame) {
        this.frame = frame;
    }

}
