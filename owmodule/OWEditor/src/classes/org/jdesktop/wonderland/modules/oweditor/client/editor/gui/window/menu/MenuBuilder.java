package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.menu;

import java.util.HashMap;
import java.util.concurrent.Callable;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public interface MenuBuilder {
    
    /**
     * Creates and adds a new Menu item to the menu.
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
    public void addItem(String menuName, String itemName, Callable<Void> function,
            KeyStroke keyCombination, boolean separator);
    
    /**
     * Builds the menun and returns it. 
     * 
     * @return MenuItem
     */
    public JComponent buildMenu();
    
    /**
     * Returns the created menu items.
     * 
     * @return All menu items build, or an empty list, if build
     * was not called yet.
     */
    public HashMap<String, JMenuItem> getMenuItems();
}
