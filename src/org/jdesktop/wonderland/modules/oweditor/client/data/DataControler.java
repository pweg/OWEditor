package org.jdesktop.wonderland.modules.oweditor.client.data;

import org.jdesktop.wonderland.modules.oweditor.client.editor.controler.MainControlerDataInterface;

public class DataControler implements DataControlerInterface {
    
    private DataUpdate updater = null;
    private DataManager dm = null;
    private MainControlerDataInterface mc = null;
    
    public DataControler(MainControlerDataInterface mc){
        this.mc = mc;
    }
    
    @Override
    public void initialize() {
        dm = new DataManager(this);
        updater = new DataUpdate(dm);
        
    }

    public void setGUIUpdate(int id) {
       mc.setGUIUpdate(id);
        
    }

    @Override
    public DataUpdateInterface getDataUpdateInterface() {
        return updater;
    }

    @Override
    public DataManagerInterface getDataManagerInterface() {
        return dm;
    }


}
