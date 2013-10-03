package org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces;

import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.GUIControlerInterface;

public interface DataControlerMainControlerInterface {
    
    public void initialize();
    
    public DataUpdateAdapterInterface getDataUpdateInterface();
    
    public DataObjectManagerGUIInterface getDataManagerInterface();
    
    public void setGUIControler(GUIControlerInterface gui);

}
