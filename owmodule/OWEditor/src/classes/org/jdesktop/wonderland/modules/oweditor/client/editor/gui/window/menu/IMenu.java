package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.menu;

import java.awt.Component;
import java.util.concurrent.Callable;

import javax.swing.JMenuBar;
import javax.swing.KeyStroke;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToMenu;

public interface IMenu {
    
    /**
     * Creates and adds a new Menu item to the menu, BUT this does not change
     * the current menu, because it is not implemented currently. 
     * The menu has to be build again and swapped with the old
     * one in the frame package.
     * 
     * @param menuName The name of the submenu, if the submenu is not
     * found or is null, a new entry of it will be created at the root.
     * @param itemName The name of the item.
     * @param function The function the new menu item should call. If it is null
     * a normal submenu category will be created under menuName.
     * @param keyCombination The hotkey the item should be used with.
     * When no hotkey is needed, null should be entered.
     * @param separator If true, a separator component will be created right before
     * the component.
     */
    public void addMenuItem(String menuName, String itemName, Callable<Void> function,
            KeyStroke keyCombination, boolean separator);
    
    /**
     * Builds a new top menu bar.
     * 
     * @return The JMenubar
     */
    public JMenuBar buildMenubar();
    
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
