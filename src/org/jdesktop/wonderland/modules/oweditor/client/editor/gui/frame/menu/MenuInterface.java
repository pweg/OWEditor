package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.menu;

import java.awt.Component;
import java.util.concurrent.Callable;

import javax.swing.JMenuBar;
import javax.swing.KeyStroke;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.InputToFrameInterface;

public interface MenuInterface {
    
    public void addMenuItem(String menuName, String itemName, Callable<Void> function,
            KeyStroke keyCombination);
    
    public JMenuBar buildMenubar();

    public void registerInputInterface(InputToFrameInterface input);
    
    public void setItemsEnabled(boolean shapesSelected, boolean copyShapesExist);
    
    public void showPopup(Component invoker, int x, int y);

}
