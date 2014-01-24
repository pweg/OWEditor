package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.menu;

import javax.swing.KeyStroke;

public interface MenuItemInterface {

    public void call();
    
    public String getName();
    
    public KeyStroke getKeyCombination();
    
    public boolean hasFunction();
    
    public boolean hasSeparator();
}
