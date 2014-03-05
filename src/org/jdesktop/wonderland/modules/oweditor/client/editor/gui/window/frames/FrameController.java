package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.BottomToolBar;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToFrame;

public class FrameController implements IFrame{
    
    public ImportFrame importFrame = null;
    public IWindowToFrame window = null;

    protected DrawingPanel drawingPan = null;
    protected MainFrame mainframe = null;
    protected JScrollPane mainScrollPanel = null;
    
    public FrameController(){
        buildMainFrame();
    }
    
    private void buildMainFrame(){
        drawingPan = new DrawingPanel(this);
        mainScrollPanel = new JScrollPane(drawingPan);
        mainScrollPanel.setWheelScrollingEnabled(false);


        mainframe = new MainFrame(mainScrollPanel, this);
    }
    
    @Override
    public void registerWindow(IWindowToFrame window) {
        this.window = window;
    }

    @Override
    public void showImportFrame() {
        
        if(importFrame == null){
            importFrame = new ImportFrame(this, window.getServerList());
        }
        importFrame.setVisible(true);
    }
    @Override
    public void setImportLocation(double x, double y) {
        if(importFrame == null)
            return;
        
        importFrame.setLocation(x,y);
    }    
    
    @Override
    public void addMouseListener(MouseInputAdapter mkListener){
        drawingPan.addMouseListener(mkListener);
        drawingPan.addMouseMotionListener(mkListener);
    }
    
    @Override
    public void addKeyListener(KeyListener mkListener){
        mainframe.addKeyListener(mkListener);
        drawingPan.addKeyListener(mkListener);
    }
    
    @Override
    public void addMouseWheelListener(MouseWheelListener mouseWheelListener){
        drawingPan.addMouseWheelListener(mouseWheelListener);
    }
    
    @Override
    public void setMainFrameVisible(boolean visibility) {
       mainframe.setVisible(visibility);
    }
    @Override
    public void setNewWidth(int width) {
        drawingPan.setNewWidth(width);
    }

    @Override
    public void setNewHeight(int height) {
        drawingPan.setNewHeight(height);
    }

    @Override
    public void setNewMinX(int x) {
        drawingPan.setNewMinX(x);
    }

    @Override
    public void setNewMinY(int y) {
        drawingPan.setNewMinY(y);
    }

    @Override
    public void repaint() {
        drawingPan.repaint();
    }

    @Override
    public IDrawingPanel getDrawingPan() {
        return drawingPan;
    }

    @Override
    public void setJMenuBar(JMenuBar buildMenubar) {
       mainframe.setJMenuBar(buildMenubar);
    }

    @Override
    public BottomToolBar getBottomToolBar() {
        return mainframe.getBottomToolBar();
    }

    @Override
    public void setUndoEnabled(boolean b) {
        mainframe.undoBar.setUndoEnabled(b);
    }

    @Override
    public void setRedoEnabled(boolean b) {
        mainframe.undoBar.setRedoEnabled(b);
        
    }



}
