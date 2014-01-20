package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUIController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.FrameToInputInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicToInputInterface;

public class Input implements InputInterface{
    
    private InputController ic = null;
    
    public Input(GUIController gc){
        ic = new InputController(gc);
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
    public InputToFrameInterface getFrameInterface() {
        return ic.inputInterface;
    }

    @Override
    public void registerGraphicInterface(GraphicToInputInterface graphic) {
        ic.registerGraphicInterface(graphic);
    }

    @Override
    public void registerFrameInterface(FrameToInputInterface frame) {
       ic.registerFrameInterface(frame);
    }

}
