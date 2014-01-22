package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.menu;

import java.util.concurrent.Callable;

import javax.swing.KeyStroke;

public class MenuItem implements MenuItemInterface{

    private Callable<Void> function = null;
    private String name = "";
    private KeyStroke keyCombination;
    private boolean separator;
    
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
            e.printStackTrace();
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
