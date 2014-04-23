package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.io.File;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.IInputToWindow;

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
        wc.input.setInputMode(IInputToWindow.ROTATE);
        wc.repaint();
    }

    @Override
    public void scaleShapes() {
        wc.input.setInputMode(IInputToWindow.SCALE);
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
            wc.adapter.setObjectRemoval(wc.graphic.getAllIDs());  
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
            wc.adapter.loadWorld(lastDir);
        }
    }

    @Override
    public void saveWorld() {
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

            wc.adapter.saveWorld(lastDir);
        }
    }

    @Override
    public void close() {
        wc.frame.getMainframe().dispose();
    }

}
