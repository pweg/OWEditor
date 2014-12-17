package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.IInputToWindow;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IWaitingDialog;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.snapshots.WorldExporter;

/**
 * The interface implementation for the menu package.
 * 
 * @author Patrick
 *
 */
public class WindowToMenu implements IWindowToMenu{

    private WindowController wc = null;
    private String lastDir = "";
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");

    public WindowToMenu(WindowController wc){
        this.wc  = wc;
    }
    
    @Override
    public void importKmz() {
        wc.frame.showImportFrame();
    }

    @Override
    public void copyShapes() {
        wc.input.setInputMode(IInputToWindow.COPY);
        wc.menu.setItemsEnabledCopy(true);
        wc.repaint();
    }

    @Override
    public void cutShapes() {
        wc.input.setInputMode(IInputToWindow.CUT);
        wc.repaint();
    }

    @Override
    public void pasteShapes() {
        wc.input.setInputMode(IInputToWindow.PASTE);
        wc.repaint();
    }

    @Override
    public void rotateShapes() {
        
        if(!checkRights())
            return;
        
        wc.frame.setTransformBarVisible(true);
        wc.input.setInputMode(IInputToWindow.ROTATE);
        wc.frame.setToolbarText(BUNDLE.getString("TransformText"));
        wc.repaint();
    }
    

    @Override
    public void scaleShapes() {

        if(!checkRights())
            return;
        
        wc.frame.setTransformBarVisible(true);
        wc.input.setInputMode(IInputToWindow.SCALE);
        wc.frame.setToolbarText(BUNDLE.getString("TransformText"));
        wc.repaint();
    }

    @Override
    public void selectAllShapes() {
        wc.graphic.selectAllShapes();
        wc.repaint();
    }

    @Override
    public void setBackground(boolean b) {
        wc.graphic.setBackground(b);
        wc.repaint();
    }

    @Override
    public void setPropertiesVisible(boolean b) {
        wc.adapter.updateObjects(wc.graphic.getSelectedShapes());
        wc.frame.setPropertiesVisible(b);
    }

    @Override
    public void deleteAll() {
        
        int dialogResult = JOptionPane.showConfirmDialog (
                null, BUNDLE.getString("DialogWarningDeleteAll"),
                BUNDLE.getString("Warning"),
                JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
            wc.adapter.removeAll();
        }
    }

    @Override
    public void loadWorld() {
        int dialogResult = JOptionPane.showConfirmDialog (
                null, BUNDLE.getString("DialogWarningDeleteAll"),
                BUNDLE.getString("Warning"),
                JOptionPane.YES_NO_OPTION);
        
        if(dialogResult == JOptionPane.NO_OPTION)
            return;
        
        JFileChooser chooser = new JFileChooser(new File (lastDir));
        chooser.setFileFilter(new FileNameExtensionFilter("Wonderland_Export_File", "wlexport"));
        chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
        chooser.setApproveButtonText(BUNDLE.getString("Load"));
            
        int returnVal = chooser.showOpenDialog(wc.frame.getMainframe());
        chooser.setMultiSelectionEnabled(false);
            
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            lastDir = file.getAbsolutePath();
            
            if(!(lastDir.endsWith(".wlexport"))){
                lastDir += ".wlexport";
            }

            wc.adapter.setObjectRemoval(wc.graphic.getAllIDs());
            
            final JDialog dialog = 
                    wc.frame.getWaitingDialog(BUNDLE.getString("Dialog_Loading"));
            
            new Thread(){
                @Override
                public void run(){
                    wc.adapter.loadWorld(lastDir, (IWaitingDialog) dialog);
                    
                    //wait for loading dialog to be ready.
                    while(!dialog.isVisible()){
                        yield();
                    }
                    dialog.dispose();
                    
                }
            
            }.start();
            dialog.setLocationRelativeTo(wc.frame.getMainframe());
            dialog.setVisible(true);
            
        }
    }

    @Override
    public void saveWorld() {
        final Logger LOGGER = Logger.getLogger(WorldExporter.class.getName());
        JFileChooser chooser = new JFileChooser(new File (lastDir));
        chooser.setApproveButtonText(BUNDLE.getString("Save"));
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileFilter(new FileNameExtensionFilter("Wonderland_Export_File", "wlexport"));
        
        int returnVal = chooser.showOpenDialog(wc.frame.getMainframe());
            
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            lastDir = file.getAbsolutePath();
            if(!(lastDir.endsWith(".wlexport"))){
                lastDir += ".wlexport";
            }

            
            final JDialog dialog = 
                    wc.frame.getWaitingDialog(BUNDLE.getString("Dialog_Saving"));
            
            new Thread(){
                @Override
                public void run(){
                    wc.adapter.saveWorld(lastDir, (IWaitingDialog) dialog);
                    
                    //wait for loading dialog to be ready.
                    while(!dialog.isVisible()){
                        yield();
                    }
                    dialog.dispose();
                    
                }
            
            }.start();
            dialog.setLocationRelativeTo(wc.frame.getMainframe());
            dialog.setVisible(true);
        }else{
            
        LOGGER.warning("disabprove");
        }
    }

    @Override
    public void close() {
        wc.frame.getMainframe().dispose();
    }
    
    /**
     * Checks for the rights, whether or not the shapes can be moved.
     * 
     * @return True, if the user has permission to move the shapes,
     * false otherwise.
     */
    private boolean checkRights(){
        ArrayList<Long> list = wc.graphic.getSelectedShapes();
        wc.adapter.updateObjects(list);
        
        for(long id : list){
            if(!wc.dm.checkRightsMove(id)){

                wc.frame.setToolbarText(BUNDLE.getString("NoPermissionText")+
                        wc.graphic.getShapeName(id));
                return false;
            }
        }
        wc.frame.setToolbarText("");
        return true;
    }

    @Override
    public void deleteShapes() {
        wc.graphic.deleteCurrentSelection();
    }

}
