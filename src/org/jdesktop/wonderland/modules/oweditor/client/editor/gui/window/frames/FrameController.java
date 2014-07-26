package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.event.MouseInputAdapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToFrame;

/**
 * Controller for the frame package.
 * 
 * @author Patrick
 *
 */
public class FrameController implements IFrame{
    
    public ImportFrame importFrame = null;
    public IWindowToFrame window = null;

    protected DrawingPanel drawingPan = null;
    protected MainFrame mainframe = null;
    protected JScrollPane mainScrollPanel = null;
    protected ImageSelectionFrame imageSelection = null;
    protected PropertiesFrame properties = null;
    
    //private DropTarget dropper = null;
    
    public FrameController(){
        buildMainFrame();
    }
    
    /**
     * Builds the mainframe.
     */
    private void buildMainFrame(){
        drawingPan = new DrawingPanel(this);
        mainScrollPanel = new JScrollPane(drawingPan);
        mainScrollPanel.setWheelScrollingEnabled(false);
        
        new DropTarget(drawingPan, new DropListener());
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
            new DropTarget(importFrame, new DropListener());
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
    public void setUndoEnabled(boolean b) {
        mainframe.undoBar.setUndoEnabled(b);
    }

    @Override
    public void setRedoEnabled(boolean b) {
        mainframe.undoBar.setRedoEnabled(b);
        
    }

    @Override
    public void setPropertiesVisible(boolean b) {
        if(properties == null)
            properties = new PropertiesFrame(this);
        
        properties.setVisible(b);
    }
    
    /**
     * Returns the image selection frame.
     * 
     * @return The image selection frame.
     */
    public ImageSelectionFrame getImageSelectionFrame(){
        if(imageSelection == null){
            imageSelection = new ImageSelectionFrame(this);
            imageSelection.setImageLib(window.getImgLib());
        }
        
        return imageSelection;
    }
    
    /**
     * This sets the image selection frame visible or 
     * hides it.
     * 
     * @param b If true, the frame will be shown, if false
     * the frame will be hidden.
     */
    public void setImageSelectionVisible(boolean b){
        if(imageSelection == null){
            imageSelection = new ImageSelectionFrame(this);
            imageSelection.setImageLib(window.getImgLib());
        }
        
        imageSelection.setVisible(b);
    }
    
    /**
     * Inner drop listener, for kmz files to be recognized, when
     * drawn into the main panel
     * 
     * @author Patrick
     *
     */
    class DropListener implements DropTargetListener{

        @Override
        public void dragEnter(DropTargetDragEvent dtde) {}

        @Override
        public void dragOver(DropTargetDragEvent dtde) {}

        @Override
        public void dropActionChanged(DropTargetDragEvent dtde) {}

        @Override
        public void dragExit(DropTargetEvent dte) {}

        @SuppressWarnings("unchecked")
        @Override
        public void drop(DropTargetDropEvent evt) {
            evt.acceptDrop(DnDConstants.ACTION_COPY);
                
            try {
                 List<File> droppedFiles = (List<File>) evt
                        .getTransferable().getTransferData(
                                DataFlavor.javaFileListFlavor);
                    
                if(droppedFiles.size() > 1)
                    return;
                    
                for (File file : droppedFiles) {
                    if(file.getName().endsWith(".kmz")){
                        showImportFrame();
                        importFrame.loadModel(file);
                    }else if(file.getName().endsWith(".wlexport")){
                        window.loadWorld(file.getAbsolutePath());
                    }
                }
                    
            } catch (UnsupportedFlavorException e) {
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void updateRightsComponent(long id) {
        if(properties == null)
            return;
        
        if(properties.isVisible()){
            properties.rightsPane.setObjects();
        }
    }

    @Override
    public JFrame getMainframe() {
        return mainframe;
    }

    @Override
    public void setTransformBarVisible(boolean b) {
        mainframe.transformBar.setVisible(b);
    }

    @Override
    public void setToolbarCoords(String x, String y) {
        mainframe.getBottomToolBar().setCoordinates(x, y);
    }

    @Override
    public JDialog getWaitingDialog(String text) {
        return new WaitingDialog(mainframe, text);
    }
}

