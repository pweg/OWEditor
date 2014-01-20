package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.menu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import javax.swing.JMenuBar;
import javax.swing.KeyStroke;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.InputToFrameInterface;

public class MenuController implements MenuInterface{
    
    private TopMenuBuilder topMenu = null;
    private InputToFrameInterface input = null;
    private PopupMenu popupMenu = null;
    

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    
    public MenuController(){
        topMenu = new TopMenuBuilder();
        popupMenu = new PopupMenu();
        
        createStandardMenu();
    }
    
    private void createStandardMenu(){
        
        final Callable<Void> newItem = new Callable<Void>() {
            public Void call(){
                function();
                return null;
            }
        };
        topMenu.addItem(BUNDLE.getString("MenuFile"), "New", newItem, KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        
        final Callable<Void> copy = new Callable<Void>() {
            public Void call(){
                input.copyShapes();
                return null;
            }
        };
        topMenu.addItem(BUNDLE.getString("MenuEdit"), 
                BUNDLE.getString("MenuCopy"), copy, 
                KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        
        final Callable<Void> cut = new Callable<Void>() {
            public Void call(){
                input.cutShapes();
                return null;
            }
        };
        topMenu.addItem(BUNDLE.getString("MenuEdit"), 
                BUNDLE.getString("MenuCut"), cut, 
                KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        
        final Callable<Void> paste = new Callable<Void>() {
            public Void call(){
                input.pasteShapes();
                return null;
            }
        };
        topMenu.addItem(BUNDLE.getString("MenuEdit"), 
                BUNDLE.getString("MenuPaste"), paste, 
                KeyStroke.getKeyStroke(
                KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        
        final Callable<Void> rotate = new Callable<Void>() {
            public Void call(){
                input.rotateShapes();
                return null;
            }
        };
        topMenu.addItem(BUNDLE.getString("MenuEdit"), 
                BUNDLE.getString("MenuRotate"), rotate, null);

        
        final Callable<Void> scale = new Callable<Void>() {
            public Void call(){
                input.scaleShapes();
                return null;
            }
        };
        topMenu.addItem(BUNDLE.getString("MenuEdit"), 
                BUNDLE.getString("MenuScale"), scale, null);

        /*
         * This is used to keep the menu system in the right order.
         * Usually menu items will be shown in the order of adding them,
         * but this can be used to change their positions.
         */
        topMenu.reorder("File", 0);
        topMenu.reorder("Edit", 1);
    }
    
    private void function(){
        System.out.println("blar");
    }

    @Override
    public void addMenuItem(String menuName, String itemName,
            Callable<Void> function, KeyStroke keyCombination) {

        topMenu.addItem(menuName, itemName, function, keyCombination);
    }

    @Override
    public JMenuBar buildMenubar() {
        return topMenu.buildMenu();
    }

    @Override
    public void registerInputInterface(InputToFrameInterface input) {
        this.input = input;
        popupMenu.registerInputInterface(input);
    }

    @Override
    public void setItemsEnabled(boolean shapesSelected, boolean copyShapesExist) {
        popupMenu.setItemsEnabled(shapesSelected, copyShapesExist);
    }

    @Override
    public void showPopup(Component invoker, int x, int y) {
        popupMenu.show(invoker, x, y);
    }

}
