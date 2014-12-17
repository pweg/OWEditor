package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames;

import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.EtchedBorder;

import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IWaitingDialog;

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
public final class WaitingDialog extends JDialog implements IWaitingDialog {
    private static final long serialVersionUID = 1003;
    
    private String title = "";
        
    public WaitingDialog(JFrame frame, String title){
        //super(BUNDLE.getString("Dialog_Loading"));
        super(frame, title, true);
        this.title = title;
        
        initComponents();
        setLocationRelativeTo(frame);
        setVisible(false);
    }
    
    private void initComponents(){
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        
        setSize(300, 120);
        
        JPanel pan=new JPanel();
        pan.setLayout(new GridBagLayout());
        
        label = new JLabel(title);
        Font labelFont = label.getFont();
        label.setFont(new Font(labelFont.getName(), Font.PLAIN, 15));
        
        
        pan.add(label);
        pan.setBorder(javax.swing.BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        
        add(pan);
    }
    
    @Override
    public void setText(String text){
        if(text.length() > 28)
            text = text.substring(0,25)+"...";
        
        label.setText(title + " " + text);
    }
    
    JLabel label;
}
