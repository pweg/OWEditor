package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JToolBar;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames.FrameController;

/**
 * Bar for the transformations rotation/scale in order to give 
 * the user a confirm/cancel action, if he does not know the 
 * shortcuts.
 * 
 * @author Patrick
 *
 */
public class TransformationBar extends JToolBar{
    
    
    private static final long serialVersionUID = 102;
    private JButton confirm = null;
    private JButton cancel = null;
    private FrameController frame = null;

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    
    public TransformationBar(FrameController frame){
        this.frame = frame;
        
        initComponents();
    }
    
    private void initComponents(){
        confirm = new JButton(BUNDLE.getString("OK"));
        confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirm(evt);
            }
        });
        cancel = new JButton(BUNDLE.getString("Cancel"));
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel(evt);
            }
        });
        
        this.add(confirm);
        this.add(cancel);
        confirm.setFocusable(false);
        cancel.setFocusable(false);
        this.setVisible(false);
    }
    
    private void confirm(ActionEvent evt) {
        frame.window.inputKeyPressed(KeyEvent.VK_ENTER);
    }
    
    private void cancel(ActionEvent evt) {
        frame.window.inputKeyPressed(KeyEvent.VK_ESCAPE);
     }

}
