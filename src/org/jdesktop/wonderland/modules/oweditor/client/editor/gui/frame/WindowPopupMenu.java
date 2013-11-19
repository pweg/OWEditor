package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.InputToFrameInterface;

public class WindowPopupMenu extends JPopupMenu{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    
    private InputToFrameInterface input = null;
    
    public WindowPopupMenu(){
        initializeComponents();
    }
    
    protected void registerInputInterface(InputToFrameInterface input){
        this.input = input;
    }
    
    private void initializeComponents(){
        copyItem = new JMenuItem(BUNDLE.getString("MenuCopy"));
        pasteItem = new JMenuItem(BUNDLE.getString("MenuPaste"));
        rotateItem = new JMenuItem(BUNDLE.getString("MenuRotate"));
        propertiesItem = new JMenuItem(BUNDLE.getString("MenuProperties"));
        
        
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
                input.copyShapes();
              }
            });
        
        pasteItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        pasteItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input.pasteShapes();
              }
            });
        
        rotateItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        rotateItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input.rotateShapes();
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
