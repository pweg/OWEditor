package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyListener;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUISettings;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames.toolbar.BottomToolBar;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames.toolbar.TransformationBar;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames.toolbar.UndoBar;

/**
 * This is the main window of the editor.
 * It holds the JScrollpane, which houses the drawing panel.
 * 
 * @author Patrick
 *
 */
public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1;
    
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    
    protected BottomToolBar bottomBar = null;
    protected UndoBar undoBar = null;
    protected TransformationBar transformBar = null;
  

    /**
     * Creates a new Window frame instance and sets it up.
     * 
     * @param mainScrollPanel The main scroll panel.
     * @param frame The frame controller.
     */
    public MainFrame(JScrollPane mainScrollPanel, FrameController frame){
        
        Container content = this.getContentPane();
        
        undoBar = new UndoBar(frame);
        bottomBar = new BottomToolBar();
        JToolBar topBar = new JToolBar();
        transformBar = new TransformationBar(frame);

        topBar.add(undoBar);
        topBar.add(transformBar);
        
        content.setFocusable(false);
        undoBar.setFocusable(false);
        topBar.setFloatable(false);
        topBar.setFocusable(false);
        transformBar.setFocusable(false);
        content.add(topBar, BorderLayout.NORTH);
        content.add(bottomBar, BorderLayout.SOUTH);
        content.add(mainScrollPanel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                
        //f.getContentPane().add(app.getControl(), "Last");  
        setSize(GUISettings.MAINWIDTH, GUISettings.MAINHEIGHT);  
        
        setLocationRelativeTo(null);
        setTitle(BUNDLE.getString("FrameTitle"));
        setVisible(false); 
        this.requestFocus();
    }
    
    /**
     * Returns the bottom tool bar.
     * 
     * @return The tool bar.
     */
    public BottomToolBar getBottomToolBar(){
        return bottomBar;
    }
    
    
    @Override
    public synchronized void addKeyListener(KeyListener l){
        super.addKeyListener(l);
        getContentPane().addKeyListener(l);  
        undoBar.addKeyListener(l);
    }

    

}
