package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IImage;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.IInputToWindow;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IWaitingDialog;

/**
 * The interface implementation for the frame package.
 * 
 * @author Patrick
 *
 */
public class WindowToFrame implements IWindowToFrame{
    
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");

    public WindowController wc = null;
    
    public WindowToFrame(WindowController wc) {
        this.wc = wc;
    }

    @Override
    public int[] loadKMZ(String url) {        
        return wc.adapter.loadKMZ(url);
    }
    
    @Override
    public void cancelImport(){
        wc.adapter.cancelImport();
    }
    
    @Override
    public boolean importCheckName(String moduleName, String server){
        return wc.adapter.importCheckName(moduleName, server);
    }

    @Override
    public boolean importKMZ(String name, String module_name, 
            String imgName, String imgDir, double x, double y,
            double z, double rotationX, double rotationY, double rotationZ,
            double scale) {
        return wc.adapter.importKMZ(name, module_name, imgName,imgDir, x, y, z,
                rotationX, rotationY, rotationZ, scale);
    }

    @Override
    public void chooseLocation(int width, int height, double rotation,
            double scale) {
        wc.graphic.createDraggingRect(width, height, 0, 0, rotation, scale);
        
        stateInput state = new stateImport(wc);
        
        state.setBounds((int)Math.round(width*scale), 
                (int) Math.round(height*scale));
        wc.inputInterface.setState(state);
        
        wc.input.setInputMode(IInputToWindow.TRANSLATE);
    }

    @Override
    public void drawShapes(Graphics2D g2, AffineTransform at) {
        wc.graphic.drawShapes(g2, at);
    }

    @Override
    public void undo() {
        wc.adapter.undo();
    }

    @Override
    public void redo() {
        wc.adapter.redo();
    }

    @Override
    public String[] getServerList() {
        return wc.dm.getServerList();
    }

    @Override
    public boolean imageExists(String name) {
        return wc.adapter.imageExists(name);
    }

    @Override
    public ArrayList<Long> getSelectedIDs() {
        return wc.graphic.getSelectedShapes();
    }

    @Override
    public ArrayList<IImage> getImgLib() {
        return wc.dm.getImgLib();
    }

    @Override
    public IDataObject getDataObject(long id) {
        return wc.dm.getDataObject(id);
    }

    @Override
    public void uploadImage(String imgUrl) {
        wc.adapter.uploadImage(imgUrl);
    }

    @Override
    public void setProperties(ArrayList<Long> ids, ArrayList<String> names,
            ArrayList<Float> coordsX, ArrayList<Float> coordsY,
            ArrayList<Float> coordsZ, ArrayList<Double> rotX,
            ArrayList<Double> rotY, ArrayList<Double> rotZ,
            ArrayList<Double> scale, ArrayList<String> imgName,
            ArrayList<String> imgDir,
            ArrayList<Object> furtherActions) {
        wc.adapter.setProperties(ids, names, coordsX, coordsY, coordsZ, 
                rotX, rotY, rotZ, scale, imgName, imgDir, furtherActions);
    }

    @Override
    public void addRightsComponent(ArrayList<Long> ids) {
        wc.adapter.addRightsComponent(ids);
    }

    @Override
    public void loadWorld(final String file) {
        int dialogResult = JOptionPane.showConfirmDialog (
                null, BUNDLE.getString("DialogWarningDeleteAll"),
                BUNDLE.getString("Warning"),
                JOptionPane.YES_NO_OPTION);
        
        if(dialogResult == JOptionPane.NO_OPTION)
            return;

        wc.adapter.setObjectRemoval(wc.graphic.getAllIDs());  
        
        final JDialog dialog = 
                wc.frame.getWaitingDialog(BUNDLE.getString("Dialog_Loading"));
        
        new Thread(){
            @Override
            public void run(){
                wc.adapter.loadWorld(file, (IWaitingDialog) dialog);
                
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

    @Override
    public void inputKeyPressed(int key) {
        wc.input.keyPressed(key);
    }

    @Override
    public String getUserName() {
        return wc.dm.getUserName();
    }
}
