package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames.toolbar;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JToolBar;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames.FrameController;

public class UndoBar extends JToolBar{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JButton undo = null;
    private JButton redo = null;
    private FrameController frame = null;

    public UndoBar(FrameController frame){
        this.frame = frame;
        
        initComponents();
    }
    
    private void initComponents(){
        undo = new JButton("undo");
        undo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undo(evt);
            }
        });
        redo = new JButton("redo");
        redo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redo(evt);
            }
        });
        
        this.add(undo);
        this.add(redo);
        undo.setFocusable(false);
        redo.setFocusable(false);
    }

    private void undo(ActionEvent evt) {
        frame.window.undo();
    }
    private void redo(ActionEvent evt) {
        frame.window.redo();
     }

}
