package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.menu;

import java.util.ArrayList;

/**
 * A simple treenode implementation.
 * 
 * @author Patrick
 */
public class TreeNode {

    private ArrayList<TreeNode> children;
    protected IMenuItem item;

    public TreeNode(IMenuItem item) {
        children = new ArrayList<TreeNode>();
        this.item = item;
    }

    /**
     * Returns the children of the node.
     * 
     * @return The children.
     */
    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    /**
     * Sets the children of the node. Note: all other
     * children will be removed.
     * 
     * @param children The children.
     */
    public void setChildren(ArrayList<TreeNode> children) {
        this.children = children;
    }
    
    /**
     * Adds a child to the node.
     * 
     * @param child The child.
     */
    public void addChild(TreeNode child){
        children.add(child);
    }
    
    /**
     * Adds a child.
     * 
     * @param item A menu item, which should be the child.
     * @return The child.
     */
    public TreeNode addChild(IMenuItem item){
        TreeNode node = new TreeNode(item);
        children.add(node);
        return node;
    }

    /**
     * Returns the menu item of the node.
     * 
     * @return The menu item.
     */
    public IMenuItem getMenuItem() {
        return item;
    }

    /**
     * Sets the item of this node.
     * 
     * @param item The item.
     */
    public void setCaption(IMenuItem item) {
        this.item = item;
    }
    
    /**
     * Searches iteratively all children for a given node.
     * 
     * @param menuName The nodes name.
     * @return The node, or null if it was not found.
     */
    public TreeNode findNode(String menuName){
        
        if(menuName == null){
            return null;
        }
        
        for(TreeNode child : children){
            
            TreeNode found = iterate(child, menuName);
            if(found != null){
                return found;
            }
        }
        return null;
    }
    
    /**
     * Iterative method to find a node.
     * 
     * @param node The node which is currently looked upon.
     * @param name The name of the node to find.
     * @return The node, or null if it was not found.
     */
    private TreeNode iterate(TreeNode node, String name){
        
        if(node.item.getName().equals(name)){
            return node;
        }
        for(TreeNode child : node.getChildren()){
            TreeNode node2 = iterate(child, name);
            if(node2 != null){
                return node2;
            }
        }
        return null;
    }

    public void removeChild(TreeNode node) {
        children.remove(node);
    }
   
}