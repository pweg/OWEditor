package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.menu;

import java.util.concurrent.Callable;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

public interface MenuBuilder {

    /**
     * Changes the order of a given menu item.
     * 
     * @param name The name of the item.
     * @param position The new position it should be in.
     */
    public void rearange(String name, int position);
    
    /**
     * Creates and adds a new Menu item to the menu.
     * 
     * @param menuName The name of the submenu, if the submenu is not
     * found, a new entry of it will be created at the root.
     * @param itemName The name of the item.
     * @param function The function the new menu item should call.
     * @param keyCombination The hotkey the item should be used with.
     * When no hotkey is needed, null should be entered.
     */
    public void addItem(String menuName, String itemName, Callable<Void> function,
            KeyStroke keyCombination);
    
    /**
     * Builds the menun and returns it. 
     * 
     * @return MenuItem
     */
    public JComponent buildMenu();
}
