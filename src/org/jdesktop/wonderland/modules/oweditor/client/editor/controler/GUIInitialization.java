package org.jdesktop.wonderland.modules.oweditor.client.editor.controler;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUIControler;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUIInterface;


public class GUIInitialization {
    
    private JFrame frame = null;
    private GUIInterface guiControler = null;
    
    public GUIInitialization(){
        guiControler = new GUIControler();
        guiControler.createFrame();
        guiControler.setVisibility(true);
        
    }
    
    public static void main(String[] args) {  
        GUIInitialization app = new GUIInitialization();  
        
        
    } 

}
