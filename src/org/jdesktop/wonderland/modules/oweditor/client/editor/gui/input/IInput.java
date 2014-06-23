package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.IGraphicToInput;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToInput;

/**
 * This interface is used by the guis main controller. It is only used
 * for registering and getting other interfaces as well as listeners.
 * 
 * @author Patrick
 *
 */
public interface IInput {
    
    /**
     * Registers the graphics interface.
     * 
     * @param graphic The graphics interface.
     */
    public void registerGraphicInterface(IGraphicToInput graphic);
    
    /**
     * Registers the window interface.
     * 
     * @param windowToInputInterface The frame interface.
     */
    public void registerWindowInterface(IWindowToInput windowToInputInterface);
    
    /**
     * Returns the interface for window.
     * 
     * @return An interface.
     */
    public IInputToWindow getWindowInterface();

}
