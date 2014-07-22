package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.concurrent.Callable;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

/**
 * Builds the popup menu.
 * 
 * @author Patrick
 */
public class PopupMenuBuilder implements IMenuBuilder{
    
  //tree for menu creation.
    private TreeNode root = null;
    //stores the menu items for later access.
    private HashMap<String, JMenuItem> itemList = null;
    
    public PopupMenuBuilder(){
        itemList = new HashMap<String, JMenuItem>();
        
        IMenuItem rootItem = new MenuItem("", null, null, false);
        
        root = new TreeNode(rootItem);
    }
    
    @Override
    public void addItem(String menuName, String itemName, Callable<Void> function,
            KeyStroke keyCombination, boolean separator){
        
        if( (menuName != null && menuName.equals("")) || itemName == null || 
                itemName.equals("")){
            return;
        }

        TreeNode node = removeNode(itemName);
        TreeNode parent = root.findNode(menuName);

        if(parent == null && menuName != null){
            MenuItem parent_item = new MenuItem(menuName, null,null, false);
            parent = root.addChild(parent_item);
        }else if (parent == null){
            parent = root;
        }
        
        if(node == null){
            IMenuItem item = new MenuItem(itemName, function, keyCombination, separator);
            parent.addChild(item);
        }else{
            parent.addChild(node);
        }
    }
    
    /**
     * Removes one item from the root and returns it.
     * 
     * @param itemName The name of the item.
     * @return The removed node.
     */
    private TreeNode removeNode(String itemName){
        for(TreeNode child : root.getChildren()){
            if(child.getMenuItem().getName().equals(itemName)){
                root.removeChild(child);
                return child;
            }
        }
        return null;
    }
    
    @Override
    public JPopupMenu buildMenu(){
        
        JPopupMenu menuBar = new JPopupMenu();
        
        for(TreeNode child : root.getChildren()){
            buildIterator(child, menuBar);
        }

        return menuBar;
    }
    
    private void buildIterator(TreeNode node, JComponent menu){
        
        IMenuItem item = node.getMenuItem();
        String name = node.getMenuItem().getName();
        
        if(item.hasSeparator()){
            if(menu instanceof JMenu)
                ((JMenu) menu).addSeparator();
            else if(menu instanceof JPopupMenu)
                ((JPopupMenu) menu).addSeparator();
        }
        
        //creates the leafs aka the menu items.
        if(item.hasFunction()){
            menu.add(createMenuItem(item));
        }
        //creates the submenus.
        else{
            JMenu jmenu = new JMenu(item.getName());
            jmenu.setName(name);
            menu.add(jmenu);
            
            for(TreeNode child : node.getChildren()){
                buildIterator(child, jmenu);
            }
        }
    }
    
    private JMenuItem createMenuItem(final IMenuItem item){
        
        String name = item.getName();
        
        JMenuItem jItem = new JMenuItem(name);
        jItem.setName(name);
        
        if(item.getKeyCombination() != null)
            jItem.setAccelerator(item.getKeyCombination());                
        jItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                item.call();
              }
        });
        
        itemList.put(name, jItem);
        
        return jItem;
    }

    @Override
    public HashMap<String, JMenuItem> getMenuItems() {
        return itemList;
    }

}
