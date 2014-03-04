package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataToGUI;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.IInputToWindow;

public class WindowToFrame implements IWindowToFrame{

    public WindowController wc = null;
    private IDataToGUI dm = null;
    
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
    public boolean importKMZ(String name, String image_url, double x, double y,
            double z, double rotationX, double rotationY, double rotationZ,
            double scale) {
        return wc.adapter.importKMZ(name, image_url, x, y, z,
                rotationX, rotationY, rotationZ, scale);
    }

    @Override
    public void chooseLocation(int width, int height, double rotation,
            double scale) {
        wc.graphic.createDraggingRect(width, height, 0, 0, rotation, scale);
        
        stateInput state = new stateImport(wc, dm);
        
        state.setBounds(width, height);
        wc.inputInterface.setState(state);
        
        wc.input.setInputMode(IInputToWindow.TRANSLATE);
    }

    public void registerDataManager(IDataToGUI dm) {
        this.dm  = dm;
    }
    
    /*
    @Override
    public void importConflictCopy(long id) {
        wc.adapter.importConflictCopy(id);
    }

    @Override
    public void importConflictOverwrite(long id) {
        wc.adapter.importConflictOverwrite(id);
    }*/

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
        return dm.getServerList();
    }

    public boolean imageExists(String name) {
        return wc.adapter.imageExists(name);
    }


}
