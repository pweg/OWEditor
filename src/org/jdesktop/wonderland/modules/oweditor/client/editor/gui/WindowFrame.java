package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import javax.swing.JFrame;

public class WindowFrame extends JFrame {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public WindowFrame(){
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);  
        
        //f.getContentPane().add(app.getControl(), "Last");  
        setSize(800, 600);  
        setLocation(200,200);  
        setVisible(true); 
        setTitle("Open Wonderland Editor");
    }

    

}
