package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUISettings;

/**
 * This is the main window of the editor.
 * It holds the JScrollpane, which houses the drawing panel.
 * @author Patrick
 *
 */
public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");


    
    protected BottomToolBar bottomBar = null;
  

    /**
     * Creates a new Window frame instance and sets it up.
     */
    public MainFrame(JScrollPane mainScrollPanel){
        
        Container content = this.getContentPane();
        content.add(mainScrollPanel);
        bottomBar = new BottomToolBar();
        content.add(bottomBar, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
        //f.getContentPane().add(app.getControl(), "Last");  
        setSize(GUISettings.MAINWIDTH, GUISettings.MAINHEIGHT);  
        setLocation(GUISettings.MAINPOSX, GUISettings.MAINPOSY);  
        setTitle(BUNDLE.getString("FrameTitle"));
        setVisible(false); 
    }
    
    public BottomToolBar getBottomToolBar(){
        return bottomBar;
    }

    

}
