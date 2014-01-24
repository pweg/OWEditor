package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicToInputInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.WindowToInputInterface;

public class Input implements InputInterface{
    
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
    public InputToMenuInterface getFrameInterface() {
        return ic.inputInterface;
    }

    @Override
    public void registerGraphicInterface(GraphicToInputInterface graphic) {
        ic.registerGraphicInterface(graphic);
    }

    @Override
    public void registerFrameInterface(WindowToInputInterface frame) {
       ic.registerFrameInterface(frame);
    }

}
