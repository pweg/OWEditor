package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.menu;

import java.util.ArrayList;


public class TreeNode {

    private ArrayList<TreeNode> children;
    protected IMenuItem item;

    public TreeNode(IMenuItem item) {
        children = new ArrayList<TreeNode>();
        this.item = item;
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<TreeNode> children) {
        this.children = children;
    }
    
    public void addChild(TreeNode child){
        children.add(child);
    }
    
    public TreeNode addChild(IMenuItem item){
        TreeNode node = new TreeNode(item);
        children.add(node);
        return node;
    }

    public IMenuItem getMenuItem() {
        return item;
    }

    public void setCaption(IMenuItem caption) {
        this.item = caption;
    }
    
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
    
    public TreeNode iterate(TreeNode node, String name){
        
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
   
}