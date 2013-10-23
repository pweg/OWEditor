package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

public class WindowPopupMenu extends JPopupMenu{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private GUIController gc = null;
    
    public WindowPopupMenu(GUIController gc){
        this.gc = gc;
        initializeComponents();
    }
    
    private void initializeComponents(){
        copyItem = new JMenuItem("copy");
        pasteItem = new JMenuItem("paste");
        rotateItem = new JMenuItem("rotate");
        propertiesItem = new JMenuItem("properties");
        
        
        propertiesItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        propertiesItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Properties");
              }
            });
        
        copyItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copyItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gc.mkListener.copyShapes();
              }
            });
        
        pasteItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        pasteItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gc.mkListener.pasteShapes();
              }
            });
        
        rotateItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        rotateItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gc.mkListener.rotateShapes();
              }
            });
        
        
        add(copyItem);
        add(pasteItem);
        add(rotateItem);
        addSeparator();
        add(propertiesItem);
    }
    
    public void setItemsEnabled(boolean shapesSelected, boolean copyShapesExist){
        
        if(shapesSelected){
            copyItem.setEnabled(true);  
            rotateItem.setEnabled(true);
        }else{
            copyItem.setEnabled(false);
            rotateItem.setEnabled(false);
        }
        if(copyShapesExist)
            pasteItem.setEnabled(true);
        else
            pasteItem.setEnabled(false);
    }
    
    private JMenuItem copyItem;
    private JMenuItem pasteItem;
    private JMenuItem rotateItem;
    private JMenuItem propertiesItem;

}