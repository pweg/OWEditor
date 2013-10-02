package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUIControlerInterface;

public interface DataControlerInterface {
    
    public void initialize();
    
    public DataUpdateInterface getDataUpdateInterface();
    
    public DataObjectManagerInterface getDataManagerInterface();
    
    public void setGUIControler(GUIControlerInterface gui);

}
