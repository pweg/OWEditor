package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.menu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.InputToFrameInterface;

public class MenuController implements MenuInterface{

    private MenuBuilder topBuilder = null;
    private MenuBuilder popupBuilder = null;
    private InputToFrameInterface input = null;
    private JPopupMenu popupMenu = null;
    private JItemManager itemManager = null;
    

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    
    public MenuController(){
        topBuilder = new TopMenuBuilder();
        popupBuilder = new PopupMenuBuilder();
        itemManager = new JItemManager();
        
        createMenu();
    }
    
    /**
     * Creates the standard top menu, as well as the popup menu.
     */
    private void createMenu(){
        
        //get the strings for the submenus.
        String menuFile = BUNDLE.getString("MenuFile");
        String menuEdit = BUNDLE.getString("MenuEdit");
        
        //create the functions
        final Callable<Void> newItem = new Callable<Void>() {
            public Void call(){
                function();
                return null;
            }
        };
        
        final Callable<Void> copy = new Callable<Void>() {
            public Void call(){
                input.copyShapes();
                return null;
            }
        };
        
        final Callable<Void> cut = new Callable<Void>() {
            public Void call(){
                input.cutShapes();
                return null;
            }
        };
        
        final Callable<Void> paste = new Callable<Void>() {
            public Void call(){
                input.pasteShapes();
                return null;
            }
        };
        
        final Callable<Void> rotate = new Callable<Void>() {
            public Void call(){
                input.rotateShapes();
                return null;
            }
        };

        
        final Callable<Void> scale = new Callable<Void>() {
            public Void call(){
                input.scaleShapes();
                return null;
            }
        };
        
        /*
         * Top menu items.
         */
        topBuilder.addItem(menuFile, "New", newItem, KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.CTRL_MASK), false);
        topBuilder.addItem(menuEdit, 
                BUNDLE.getString("MenuCopy"), copy, 
                KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.CTRL_MASK), false);
        topBuilder.addItem(menuEdit, 
                BUNDLE.getString("MenuCut"), cut, 
                KeyStroke.getKeyStroke(
                KeyEvent.VK_X, ActionEvent.CTRL_MASK), false);
        topBuilder.addItem(menuEdit, 
                BUNDLE.getString("MenuPaste"), paste, 
                KeyStroke.getKeyStroke(
                KeyEvent.VK_V, ActionEvent.CTRL_MASK), false);
        topBuilder.addItem(menuEdit, 
                BUNDLE.getString("MenuRotate"), rotate, null, false);
        topBuilder.addItem(menuEdit, 
                BUNDLE.getString("MenuScale"), scale, null, false);
        
        /*
         * Popup menu items
         */
        popupBuilder.addItem(null, 
                BUNDLE.getString("MenuCopy"), copy, 
                KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.CTRL_MASK), false);
        popupBuilder.addItem(null, 
                BUNDLE.getString("MenuCut"), cut, 
                KeyStroke.getKeyStroke(
                KeyEvent.VK_X, ActionEvent.CTRL_MASK), false);
        popupBuilder.addItem(null, 
                BUNDLE.getString("MenuPaste"), paste, 
                KeyStroke.getKeyStroke(
                KeyEvent.VK_V, ActionEvent.CTRL_MASK), false);
        popupBuilder.addItem(null, 
                BUNDLE.getString("MenuRotate"), rotate, null, false);
        popupBuilder.addItem(null, 
                BUNDLE.getString("MenuScale"), scale, null, false);
        popupBuilder.addItem(null, 
                BUNDLE.getString("MenuProperties"), newItem, null, true);
        
        popupMenu =  (JPopupMenu) popupBuilder.buildMenu();
        
        //sets the popup menu and deactivates the entries.
        itemManager.setPopupItems(popupBuilder.getMenuItems());
        itemManager.setItemsEnabledSelection(false);
        itemManager.setItemsEnabledCopy(false);
    }
    
    private void function(){
        System.out.println("not implemented");
    }

    @Override
    public void addMenuItem(String menuName, String itemName,
            Callable<Void> function, KeyStroke keyCombination, boolean separator) {

        topBuilder.addItem(menuName, itemName, function, keyCombination, separator);
    }

    @Override
    public JMenuBar buildMenubar() {
        JMenuBar menu = (JMenuBar) topBuilder.buildMenu();
        
        //deactivates the entries of the new menu.
        itemManager.setTopItems(topBuilder.getMenuItems());
        itemManager.setItemsEnabledSelection(false);
        itemManager.setItemsEnabledCopy(false);
        
        return menu;
    }

    @Override
    public void registerInputInterface(InputToFrameInterface input) {
        this.input = input;
        //popupMenu.registerInputInterface(input);
    }

    @Override
    public void setItemsEnabledSelection(boolean shapesSelected) {
        //popupMenu.setItemsEnabled(shapesSelected, copyShapesExist);
        itemManager.setItemsEnabledSelection(shapesSelected);
    }

    @Override
    public void setItemsEnabledCopy(boolean copyShapesExist) {
        //popupMenu.setItemsEnabled(shapesSelected, copyShapesExist);
        itemManager.setItemsEnabledCopy(copyShapesExist);
    }

    @Override
    public void showPopup(Component invoker, int x, int y) {
        popupMenu.show(invoker, x, y);
    }

}
