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

public class LoadingDialog extends JDialog{
    private static final long serialVersionUID = 1L;
    
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    
    private JFrame parent = null;
    
    public LoadingDialog(JFrame frame){
        super(frame, BUNDLE.getString("Dialog_Loading"));
        parent = frame;
        
        initComponents();
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(frame);
        setVisible(false);
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
    
    @Override
    public void setVisible(boolean b){
        if(b)
            parent.setEnabled(false);
        else
            parent.setEnabled(true);
        super.setVisible(b);
    }

}
