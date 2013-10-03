package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import org.jdesktop.wonderland.modules.oweditor.client.editor.controlerinterfaces.MainControlerDataInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataControlerMainControlerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataUpdateAdapterInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.GUIControlerInterface;

public class DataControler implements DataControlerMainControlerInterface {
    
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

    public void setGUIUpdate(long id) {
       gci.getDataUpdate(id);
        
    }

    @Override
    public DataUpdateAdapterInterface getDataUpdateInterface() {
        return updater;
    }

    @Override
    public DataObjectManagerGUIInterface getDataManagerInterface() {
        return dm;
    }

    @Override
    public void setGUIControler(GUIControlerInterface gui) {
        gci = gui;
    }


}
