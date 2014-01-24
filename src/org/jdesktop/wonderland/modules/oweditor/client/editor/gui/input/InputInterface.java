package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicToInputInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.WindowToInputInterface;

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
    
    public InputToMenuInterface getFrameInterface();
    
    public void registerGraphicInterface(GraphicToInputInterface graphic);
    
    public void registerFrameInterface(WindowToInputInterface frameToInputInterface);

}
