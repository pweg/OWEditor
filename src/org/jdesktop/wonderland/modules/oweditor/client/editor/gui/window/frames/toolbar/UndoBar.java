package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames.toolbar;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JToolBar;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames.FrameController;

/**
 * This toolbar is used for the undo and redo actions.
 * 
 * @author Patrick
 */
public class UndoBar extends JToolBar{
    
    /**
     * 
     */
    private static final long serialVersionUID = 101;
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
        redo.setEnabled(false);
        undo.setEnabled(false);
        undo.setFocusable(false);
        redo.setFocusable(false);
    }
    
    public void setUndoEnabled(boolean b){
        undo.setEnabled(b);
    }
    
    public void setRedoEnabled(boolean b){
        redo.setEnabled(b);
    }

    private void undo(ActionEvent evt) {
        frame.window.undo();
    }
    private void redo(ActionEvent evt) {
        frame.window.redo();
     }

}
