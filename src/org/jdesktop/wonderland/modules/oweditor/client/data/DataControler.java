package org.jdesktop.wonderland.modules.oweditor.client.data;

import org.jdesktop.wonderland.modules.oweditor.client.editor.controler.MainControlerDataInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUIControlerInterface;

public class DataControler implements DataControlerInterface {
    
    private DataUpdate updater = null;
    private DataManager dm = null;
    private MainControlerDataInterface mc = null;
    private GUIControlerInterface gci = null;
    
    public DataControler(MainControlerDataInterface mc){
        this.mc = mc;
    }
    
    @Override
    public void initialize() {
        dm = new DataManager(this);
        updater = new DataUpdate(dm);
        
    }

    public void setGUIUpdate(int id) {
       gci.getAdapterUpdate(id);
        
    }

    @Override
    public DataUpdateInterface getDataUpdateInterface() {
        return updater;
    }

    @Override
    public DataManagerInterface getDataManagerInterface() {
        return dm;
    }

    @Override
    public void setGUIControler(GUIControlerInterface gui) {
        gci = gui;
    }


}
