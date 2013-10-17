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
        rotateItem = new JMenuItem("rotate");
        propertiesItem = new JMenuItem("properties");
        
        
        propertiesItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        propertiesItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Properties");
              }
            });
        

        rotateItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        rotateItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gc.srm.initializeRotation();
              }
            });
        
        
        add(copyItem);
        add(rotateItem);
        addSeparator();
        add(propertiesItem);
    }
    
    private JMenuItem copyItem;
    private JMenuItem rotateItem;
    private JMenuItem propertiesItem;

}
