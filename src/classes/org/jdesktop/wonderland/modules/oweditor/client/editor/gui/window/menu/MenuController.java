package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.menu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToMenu;

/**
 * The menu controller, which builds the menus.
 * 
 * @author Patrick
 */
public class MenuController implements IMenu{

    private JPopupMenu popupMenu = null;
    private JMenuBar topMenu = null;
    private JItemManager itemManager = null;
    private IWindowToMenu window = null;

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    
    public MenuController(){
        itemManager = new JItemManager();
        
        createMenu();
        itemManager.setToBackgroundVisible(true);
    }
    
    /**
     * Creates the standard top menu, as well as the popup menu.
     */
    private void createMenu(){
        
        //get the strings for the submenus.
        String menuFile = BUNDLE.getString("MenuFile");
        String menuEdit = BUNDLE.getString("MenuEdit");
        String menuImport = BUNDLE.getString("MenuImport");
        
        //create the functions
        final Callable<Void> newCall = new Callable<Void>() {
            public Void call(){
                window.deleteAll();
                return null;
            }
        };
        
        final Callable<Void> loadWorld = new Callable<Void>() {
            public Void call(){
                window.loadWorld();
                return null;
            }
        };
        final Callable<Void> saveWorld = new Callable<Void>() {
            public Void call(){
                window.saveWorld();
                return null;
            }
        };
        
        
        final Callable<Void> setProperties = new Callable<Void>() {
            public Void call(){
                window.setPropertiesVisible(true);
                return null;
            }
        };
        
        final Callable<Void> selectAll = new Callable<Void>(){
            public Void call(){
                window.selectAllShapes();
                setItemsEnabledSelection(true);
                return null;
            }
        };
        
        final Callable<Void> background = new Callable<Void>() {
            public Void call(){
                window.setBackground(true);
                return null;
            }
        };
        
        final Callable<Void> foreground = new Callable<Void>() {
            public Void call(){
                window.setBackground(false);
                return null;
            }
        };
        
        final Callable<Void> copy = new Callable<Void>() {
            public Void call(){
                window.copyShapes();
                setItemsEnabledCopy(true);
                return null;
            }
        };
        
        final Callable<Void> cut = new Callable<Void>() {
            public Void call(){
                window.cutShapes();
                setItemsEnabledCopy(true);
                return null;
            }
        };
        
        final Callable<Void> paste = new Callable<Void>() {
            public Void call(){
                window.pasteShapes();
                return null;
            }
        };
        
        final Callable<Void> delete = new Callable<Void>() {
            public Void call(){
                window.deleteShapes();
                return null;
            }
        };
        
        final Callable<Void> rotate = new Callable<Void>() {
            public Void call(){
                window.rotateShapes();
                return null;
            }
        };

        
        final Callable<Void> scale = new Callable<Void>() {
            public Void call(){
                window.scaleShapes();
                return null;
            }
        };
        
        final Callable<Void> addkmz = new Callable<Void>() {
            public Void call(){
                window.importKmz();
                return null;
            }
        };
        
        final Callable<Void> close = new Callable<Void>() {
            public Void call(){
                window.close();
                return null;
            }
        };


        IMenuBuilder topBuilder = new TopMenuBuilder();;
        IMenuBuilder popupBuilder = new PopupMenuBuilder();
        
        /*
         * Top menu items.
         */
        //file menu
        topBuilder.addItem(menuFile, BUNDLE.getString("MenuNew"), 
                newCall, KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.CTRL_MASK), false);
        topBuilder.addItem(menuFile, BUNDLE.getString("MenuLoadWorld"),
                loadWorld, KeyStroke.getKeyStroke(
                KeyEvent.VK_L, ActionEvent.CTRL_MASK), true);
        topBuilder.addItem(menuFile, BUNDLE.getString("MenuSaveWorld"),
                saveWorld, KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.CTRL_MASK), false);
        topBuilder.addItem(menuFile, BUNDLE.getString("MenuClose"),
                close, null, true);
        //edit menu
        topBuilder.addItem(menuEdit, 
                BUNDLE.getString("MenuSelectAll"), selectAll, 
                KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.CTRL_MASK), 
                false); 
        topBuilder.addItem(menuEdit, 
                BUNDLE.getString("MenuToBackground"), background, 
                 null, true);
        topBuilder.addItem(menuEdit, 
                BUNDLE.getString("MenuToForeground"), foreground, 
                 null, false);
        topBuilder.addItem(menuEdit, 
                BUNDLE.getString("MenuCopy"), copy, 
                KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.CTRL_MASK), true);
        topBuilder.addItem(menuEdit, 
                BUNDLE.getString("MenuCut"), cut, 
                KeyStroke.getKeyStroke(
                KeyEvent.VK_X, ActionEvent.CTRL_MASK), false);
        topBuilder.addItem(menuEdit, 
                BUNDLE.getString("MenuPaste"), paste, 
                KeyStroke.getKeyStroke(
                KeyEvent.VK_V, ActionEvent.CTRL_MASK), false);
        topBuilder.addItem(menuEdit, 
                BUNDLE.getString("MenuDelete"), delete, 
                KeyStroke.getKeyStroke(
                KeyEvent.VK_DELETE, 0), false);
        topBuilder.addItem(menuEdit, 
                BUNDLE.getString("MenuRotate"), rotate, null, false);
        topBuilder.addItem(menuEdit, 
                BUNDLE.getString("MenuScale"), scale, null, false);
        topBuilder.addItem(menuEdit, 
                BUNDLE.getString("MenuProperties"), setProperties, null, true);
        
        //import menu
        topBuilder.addItem(menuImport, 
                BUNDLE.getString("MenuAddKMZ"), addkmz, null, false);
        
        /*
         * Popup menu items
         */
        popupBuilder.addItem(null, 
                BUNDLE.getString("MenuToBackground"), background, 
                null, false);        
        popupBuilder.addItem(null, 
                BUNDLE.getString("MenuToForeground"), foreground, 
                null, false);
        popupBuilder.addItem(null, 
                BUNDLE.getString("MenuCopy"), copy, 
                KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.CTRL_MASK), true);
        popupBuilder.addItem(null, 
                BUNDLE.getString("MenuCut"), cut, 
                KeyStroke.getKeyStroke(
                KeyEvent.VK_X, ActionEvent.CTRL_MASK), false);
        popupBuilder.addItem(null, 
                BUNDLE.getString("MenuPaste"), paste, 
                KeyStroke.getKeyStroke(
                KeyEvent.VK_V, ActionEvent.CTRL_MASK), false);
        popupBuilder.addItem(null, 
                BUNDLE.getString("MenuDelete"), delete, 
                KeyStroke.getKeyStroke(
                KeyEvent.VK_DELETE, 0), false);
        popupBuilder.addItem(null, 
                BUNDLE.getString("MenuRotate"), rotate, null, false);
        popupBuilder.addItem(null, 
                BUNDLE.getString("MenuScale"), scale, null, false);
        popupBuilder.addItem(null, 
                BUNDLE.getString("MenuProperties"), setProperties, null, true);

        //builds the menus
        topMenu = (JMenuBar) topBuilder.buildMenu();
        popupMenu =  (JPopupMenu) popupBuilder.buildMenu();
        
        //sets the popup menu and deactivates the entries.
        itemManager.setTopItems(topBuilder.getMenuItems());
        itemManager.setPopupItems(popupBuilder.getMenuItems());
        
        itemManager.setItemsEnabledSelection(false);
        itemManager.setItemsEnabledCopy(false);
        
    }

    @Override
    public JMenuBar getMenubar() {
        return topMenu;
    }
    
    @Override
    public void setItemsEnabledSelection(boolean shapesSelected) {
        itemManager.setItemsEnabledSelection(shapesSelected);
    }

    @Override
    public void setItemsEnabledCopy(boolean copyShapesExist) {
        itemManager.setItemsEnabledCopy(copyShapesExist);
    }

    @Override
    public void showPopup(Component invoker, int x, int y) {
        popupMenu.show(invoker, x, y);
    }

    @Override
    public void registerWindowInterface(IWindowToMenu window) {
        this.window  = window;
    }

    @Override
    public void setToBackgroundVisible(boolean b) {
        itemManager.setToBackgroundVisible(b);
    }

}
