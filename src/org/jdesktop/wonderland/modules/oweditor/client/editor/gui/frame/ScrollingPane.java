package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.awt.Component;

import javax.swing.JScrollPane;

public class ScrollingPane extends JScrollPane{
    
    public ScrollingPane(Component view){
        super(view);
    }
    
    @Override
    public void setViewportView(Component view) {
        super.setViewportView(view);
        System.out.println("BB");
    }


}
