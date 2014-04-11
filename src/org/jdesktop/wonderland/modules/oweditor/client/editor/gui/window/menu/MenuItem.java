package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.menu;

import java.util.concurrent.Callable;

import javax.swing.KeyStroke;

/**
 * A menu item.
 * 
 * @author Patrick
 */
public class MenuItem implements IMenuItem{

    private Callable<Void> function = null;
    private String name = "";
    private KeyStroke keyCombination;
    private boolean separator;
    
    /**
     * Creates a new instance.
     * 
     * @param name The name of the menu entry.
     * @param function The function which should be called.
     * @param keyCombination A key short cut (may be null).
     * @param separator If true, a separator will be build before the item.
     */
    public MenuItem(String name, Callable<Void> function, KeyStroke keyCombination,
            boolean separator){
        this.name = name;
        this.function = function;
        this.keyCombination = keyCombination;
        this.separator = separator;
    }
    
    @Override
    public void call() {
        try {
            function.call();
        } catch (Exception e) {
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public KeyStroke getKeyCombination() {
        return keyCombination;
    }

    @Override
    public boolean hasFunction() {
        if(function == null)
            return false;
        else
            return true;
    }

    @Override
    public boolean hasSeparator() {
        return separator;
    }

}
