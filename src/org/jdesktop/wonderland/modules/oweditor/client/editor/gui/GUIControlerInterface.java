package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import org.jdesktop.wonderland.modules.oweditor.client.adapter.ClientUpdateInterface;
import org.jdesktop.wonderland.modules.oweditor.client.data.DataObjectManagerInterface;


public interface GUIControlerInterface {
    
    public void createFrame();
    
    public void setVisibility(boolean visibility);

    public void getDataUpdate(int id);
    
    public void setDataManager(DataObjectManagerInterface dm);
    
    public void setClientUpdateAdapter (ClientUpdateInterface cui);

}
