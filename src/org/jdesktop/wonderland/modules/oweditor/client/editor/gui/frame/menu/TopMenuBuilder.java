package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;


/**
 * This class is the top menu of the main frame.
 * 
 * @author Patrick
 *
 */
public class TopMenuBuilder {
    
    private HashMap<String, ArrayList<MenuItemInterface>> menuList = null;
    private ArrayList<String> menuOrder = null;
    
    
    public TopMenuBuilder(){
        menuList = new HashMap<String, ArrayList<MenuItemInterface>>();
        menuOrder = new ArrayList<String>();
    }
    
    private ArrayList<MenuItemInterface> getMenu(String menuName){
        return menuList.get(menuName);
    }
    
    private int containsName(String name){
        int i=0;
        for(String ordername : menuOrder){
            if(ordername.equals(name))
                return i;
            i++;
        }
        return -1;
    }
    
    public void reorder(String name, int order){
        
        if(order < 0)
            return;
        
        int index = containsName(name);
        
        if(index != -1){
            menuOrder.remove(index);
            menuOrder.add(order, name);
        }
    }
    
    
    public void addItem(String menuName, String itemName, Callable<Void> function,
            KeyStroke keyCombination){
        ArrayList<MenuItemInterface> menu = getMenu(menuName);
        if(menu == null){
            menu = new ArrayList<MenuItemInterface>();
            this.menuList.put(menuName, menu);
            
            if(containsName(menuName) == -1){
                menuOrder.add(menuName);
            }
        }
        MenuItemInterface item = new MenuItem(itemName, function, keyCombination);
        menu.add(item);
    }
    
    
    public JMenuBar buildMenu(){
        JMenuBar menuBar = new JMenuBar();
        
        for (String menu_name : menuOrder) {
            
            ArrayList<MenuItemInterface> menuEntries = menuList.get(menu_name);
            
            JMenu menu = new JMenu(menu_name);
            
            for(final MenuItemInterface item : menuEntries){
                
                JMenuItem jItem = new JMenuItem(item.getName());
                
                if(item.getKeyCombination() != null)
                    jItem.setAccelerator(item.getKeyCombination());                
                jItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        item.call();
                      }
                });
                menu.add(jItem);
            }
            menuBar.add(menu);
        }

        return menuBar;
    }

}
