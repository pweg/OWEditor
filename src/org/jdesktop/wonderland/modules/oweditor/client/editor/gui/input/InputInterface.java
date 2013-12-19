package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.FrameToInputInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicToInputFacadeInterface;

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
    
    public InputToFrameInterface getFrameInterface();

    public InputToShapeInterface getShapeInterface();
    
    public void notifyMinXChange(int x);
    
    public void notifyMinYChange(int y);
    
    public void registerGraphicInterface(GraphicToInputFacadeInterface shape);
    
    public void registerFrameInterface(FrameToInputInterface frameToInputInterface);

}
