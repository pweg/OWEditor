package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import javax.swing.JFrame;

/**
 * This is the main window of the editor.
 * It holds the JScrollpane, which houses the drawing panel.
 * @author Patrick
 *
 */
public class WindowFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new Window frame instance and sets it up.
     */
    public WindowFrame(){
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);  
        
        //f.getContentPane().add(app.getControl(), "Last");  
        setSize(GUISettings.frameWidth, GUISettings.frameHeight);  
        setLocation(GUISettings.framePositionX, GUISettings.framePositionY);  
        setTitle(GUISettings.frameTitle);
        setVisible(false); 
    }

    

}
