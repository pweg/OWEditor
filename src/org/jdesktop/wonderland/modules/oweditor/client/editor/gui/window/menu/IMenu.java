package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.menu;

import java.awt.Component;

import javax.swing.JMenuBar;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToMenu;

public interface IMenu {
    
    /**
     * Returns the top menu bar.
     * 
     * @return The JMenubar
     */
    public JMenuBar getMenubar();
    
    /**
     * Registers the window interface.
     * 
     * @param window An interface instance.
     */
    public void registerWindowInterface(IWindowToMenu window);

    /**
     * Is used for some menu items. When nothing is selected they get
     * deactivated. 
     * 
     * @param shapesSelected True means, that at least one shape is currently 
     * selected
     */
    public void setItemsEnabledSelection(boolean shapesSelected);
    
    /**
     * Is used for the paste menu item. This activates the
     * menu entry, if a copy was made and paste is possible.
     * 
     * @param copyShapesExist True if a copy was invoked and therefore
     * shapes for copy are ready.
     */
    public void setItemsEnabledCopy(boolean copyShapesExist);
    
    /**
     * Shows the popup menu at a specific location.
     * 
     * @param invoker Usually the drawing panel item.
     * @param x The x coordinate of the popup menu.
     * @param y The y coordinate of the popup menu.
     */
    public void showPopup(Component invoker, int x, int y);

    /**
     * Sets the tobackground item of the popup menu visible.
     * 
     * @param b If true, the to background item is getting visible,
     * if false, the to foreground item is getting visible.
     */
    public void setToBackgroundVisible(boolean b);
}
