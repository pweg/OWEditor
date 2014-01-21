package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.menu;

import java.util.ArrayList;


public class TreeNode {

    private ArrayList<TreeNode> children;
    protected MenuItemInterface item;

    public TreeNode(MenuItemInterface caption) {
        super();
        children = new ArrayList<TreeNode>();
        this.item = caption;
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

    public MenuItemInterface getCaption() {
        return item;
    }

    public void setCaption(MenuItemInterface caption) {
        this.item = caption;
    }
    
    public TreeNode findNode(String menuName){
        
        for(TreeNode child : children){
            if(iterate(child, menuName) != null)
                return child;
        }
        return null;
    }
    
    public TreeNode iterate(TreeNode node, String name){
        
        if(node.item.getName().equals(name)){
            return node;
        }
        for(TreeNode child : node.getChildren()){
            if(iterate(child, name) != null){
                return child;
            }
        }
        return null;
    }
    
    public void printTree(){
        System.out.println(item.getName());
        
        for(TreeNode child : children){
            iteratePrint(child);
            System.out.println("--------");
        }
    }
    
    public void iteratePrint(TreeNode node){
        System.out.println(node.item.getName());
        
        for(TreeNode child : node.getChildren()){
            iteratePrint(child);
        }
    }

}