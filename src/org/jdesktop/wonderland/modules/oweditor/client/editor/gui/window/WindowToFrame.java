package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.IInputToWindow;

public class WindowToFrame implements IWindowToFrame{

    public WindowController wc = null;
    private DataObjectManagerGUIInterface dm = null;
    
    public WindowToFrame(WindowController wc) {
        this.wc = wc;
    }

    @Override
    public int[] loadKMZ(String url) {        
        return wc.adapter.loadKMZ(url);
    }

    @Override
    public long importKMZ(String name, String image_url, double x, double y,
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

    public void registerDataManager(DataObjectManagerGUIInterface dm) {
        this.dm  = dm;
    }

    @Override
    public void copyKMZ(long id, String image_url, double x, double y,
            double z, double rot_x, double rot_y, double rot_z, double scale) {
        wc.adapter.copyKMZ(id, image_url, x, y, z,
                rot_x,rot_y,rot_z,scale);
    }

    @Override
    public void overwriteKMZ(long id, String name, String image_url, double x,
            double y, double z, double rot_x, double rot_y, double rot_z,
            double scale) {
        wc.adapter.overwriteKMZ(id, image_url, x, y, z,
                rot_x,rot_y,rot_z,scale);
    }

}
