package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames.toolbar;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JToolBar;

/**
 * This is the bottom toolbar, which houses the mouse coordinates.
 * 
 * @author Patrick
 */
public class BottomToolBar extends JToolBar{
   
    private static final long serialVersionUID = 1L;
    
    private final String nullString = "0.000";

    public BottomToolBar(){ initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */                     
    private void initComponents() {

        xLabel = new JLabel();
        xField = new JLabel();
        jSeparator1 = new Separator();
        yLabel = new JLabel();
        yField = new JLabel();
        
        this.setFloatable(false);
        
        xLabel.setText("X: ");
        xLabel.setEnabled(false);
        this.add(xLabel);
        
        xField.setText(nullString);
        xField.setEnabled(false);
        this.add(xField);

        jSeparator1.setEnabled(false);
        jSeparator1.setRequestFocusEnabled(false);
        jSeparator1.setSeparatorSize(new Dimension(10, 20));
        this.add(jSeparator1);
        
        yLabel.setText("Y: ");
        yLabel.setEnabled(false);
        this.add(yLabel);
        
        yField.setText(nullString);
        yField.setEnabled(false);
        this.add(yField);
    }   
    
    public void setCoordinates(String x, String y){
        xField.setText(x);
        yField.setText(y);
    }


    // Variables declaration - do not modify                     
    private Separator jSeparator1;
    private JLabel xField;
    private JLabel xLabel;
    private JLabel yField;
    private JLabel yLabel;
    
}
