package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.menu;

import javax.swing.KeyStroke;

/**
 * This interface exists only because of the 
 * possibility of other menu items in the future.
 * This interface is used to help building menu 
 * structures and contains all information needed.
 * 
 * @author Patrick
 *
 */
public interface IMenuItem {

    /**
     * Calls the callback function stored in the 
     * item.
     */
    public void call();
    
    /**
     * Returns the name of the menu entry.
     * 
     * @return The name as string.
     */
    public String getName();
    
    /**
     * Returns the key combination, which is used
     * to call the menu item.
     * 
     * @return The keystroke combination.
     */
    public KeyStroke getKeyCombination();
    
    /**
     * Returns whether the item has a 
     * function, which could be called or not.
     * 
     * @return True, if there is a function,
     * false otherwise.
     */
    public boolean hasFunction();
    
    /**
     * Returns whether the item should have a 
     * separator before it will be build.
     * 
     * @return True, if there should be a separator
     * directly before the menu item, false otherwise.
     */
    public boolean hasSeparator();
}
