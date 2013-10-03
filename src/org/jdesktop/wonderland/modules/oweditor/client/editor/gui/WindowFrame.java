package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import javax.swing.JFrame;

public class WindowFrame extends JFrame {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public WindowFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        
        //f.getContentPane().add(app.getControl(), "Last");  
        setSize(GUISettings.frameWidth, GUISettings.frameHeight);  
        setLocation(GUISettings.framePositionX, GUISettings.framePositionY);  
        setVisible(false); 
        setTitle(GUISettings.frameTitle);
    }

    

}
