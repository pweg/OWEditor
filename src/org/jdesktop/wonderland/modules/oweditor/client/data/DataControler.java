package org.jdesktop.wonderland.modules.oweditor.client.data;

import org.jdesktop.wonderland.modules.oweditor.client.editor.controler.MainControlerDataInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUIControlerInterface;

public class DataControler implements DataControlerInterface {
    
    private DataUpdate updater = null;
    private DataObjectManager dm = null;
    private MainControlerDataInterface mc = null;
    private GUIControlerInterface gci = null;
    
    public DataControler(MainControlerDataInterface mc){
        this.mc = mc;
    }
    
    @Override
    public void initialize() {
        dm = new DataObjectManager(this);
        updater = new DataUpdate(dm);
        
    }

    public void setGUIUpdate(int id) {
       gci.getDataUpdate(id);
        
    }

    @Override
    public DataUpdateInterface getDataUpdateInterface() {
        return updater;
    }

    @Override
    public DataObjectManagerInterface getDataManagerInterface() {
        return dm;
    }

    @Override
    public void setGUIControler(GUIControlerInterface gui) {
        gci = gui;
    }


}
