package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.IGraphicToInput;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToInput;

/**
 * This interface is used by the guis main controller
 * 
 * @author Patrick
 *
 */
public interface InputInterface {
    
    public MouseInputAdapter getMouseListener();
    
    public KeyListener getKeyListener();
    
    public MouseWheelListener getMouseWheelListener();
    
    public IInputToWindow getFrameInterface();
    
    public void registerGraphicInterface(IGraphicToInput graphic);
    
    public void registerFrameInterface(IWindowToInput frameToInputInterface);

}
