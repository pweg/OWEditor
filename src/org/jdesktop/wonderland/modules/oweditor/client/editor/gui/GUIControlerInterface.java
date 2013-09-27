package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import org.jdesktop.wonderland.modules.oweditor.client.data.DataManagerInterface;


public interface GUIControlerInterface {
    
    public void createFrame();
    
    public void setVisibility(boolean visibility);

    public void getAdapterUpdate(int id);
    
    public void setDataManager(DataManagerInterface dm);

}
