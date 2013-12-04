package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.util.ResourceBundle;

import javax.swing.JFrame;

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

  

    /**
     * Creates a new Window frame instance and sets it up.
     */
    public MainFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
        //f.getContentPane().add(app.getControl(), "Last");  
        setSize(GUISettings.FRAMEWIDTH, GUISettings.FRAMEHEIGHT);  
        setLocation(GUISettings.FRAMEPOSITIONX, GUISettings.FRAMEPOSITIONY);  
        setTitle(BUNDLE.getString("FrameTitle"));
        setVisible(false); 
    }

    

}
