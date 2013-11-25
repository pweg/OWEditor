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
        cutItem = new JMenuItem(BUNDLE.getString("MenuCut"));
        pasteItem = new JMenuItem(BUNDLE.getString("MenuPaste"));
        rotateItem = new JMenuItem(BUNDLE.getString("MenuRotate"));
        scaleItem = new JMenuItem(BUNDLE.getString("MenuScale"));
        propertiesItem = new JMenuItem(BUNDLE.getString("MenuProperties"));
        
        
        propertiesItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        propertiesItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Properties");
              }
            });
        
        cutItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        cutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input.cutShapes();
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

        rotateItem.setMnemonic(KeyEvent.VK_R);
        rotateItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input.rotateShapes();
              }
            });
        
        scaleItem.setMnemonic(KeyEvent.VK_S);
        scaleItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input.scaleShapes();
              }
            });
        

        add(cutItem);
        add(copyItem);
        add(pasteItem);
        add(rotateItem);
        add(scaleItem);
        addSeparator();
        add(propertiesItem);
    }
    
    public void setItemsEnabled(boolean shapesSelected, boolean copyShapesExist){
        
        if(shapesSelected){
            cutItem.setEnabled(true);
            copyItem.setEnabled(true);  
            rotateItem.setEnabled(true);
            scaleItem.setEnabled(true);
            propertiesItem.setEnabled(true);
        }else{
            cutItem.setEnabled(false);
            copyItem.setEnabled(false);
            rotateItem.setEnabled(false);
            scaleItem.setEnabled(false);
            propertiesItem.setEnabled(false);
        }
        if(copyShapesExist)
            pasteItem.setEnabled(true);
        else
            pasteItem.setEnabled(false);
    }

    private JMenuItem cutItem;
    private JMenuItem copyItem;
    private JMenuItem pasteItem;
    private JMenuItem rotateItem;
    private JMenuItem scaleItem;
    private JMenuItem propertiesItem;

}
