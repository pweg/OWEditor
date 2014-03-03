package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.ResourceBundle;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.EtchedBorder;

/**
 * This is a dialog that should be shown, when loading.
 * Unfortunately it is not possible to make it appear in wonderland 
 * propperly, if it is not modal. So this frame blocks every frame thread
 * that is used to start it, which means it also blocks the thread of the 
 * frame, which does not allow further work in the frame. Therefore,
 * a new thread has to be constructed before this dialog is shown, in order
 * to do work.
 * 
 * @author Patrick
 */
public final class LoadingDialog extends JDialog{
    private static final long serialVersionUID = 1000L;
    
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
        
    public LoadingDialog(JFrame frame){
        //super(BUNDLE.getString("Dialog_Loading"));
        super(frame, BUNDLE.getString("Dialog_Loading"), true);
        
        initComponents();
        setLocationRelativeTo(frame);
    }
    
    private void initComponents(){
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        
        setSize(200, 100);
        
        JPanel pan=new JPanel();
        pan.setLayout(new GridBagLayout());
        
        JLabel label = new JLabel(BUNDLE.getString("Dialog_Loading"));
        Font labelFont = label.getFont();
        label.setFont(new Font(labelFont.getName(), Font.PLAIN, 15));
        
        
        pan.add(label);
        pan.setBorder(javax.swing.BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        
        add(pan);
    }

}
