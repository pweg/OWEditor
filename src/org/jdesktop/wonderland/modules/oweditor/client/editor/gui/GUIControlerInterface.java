package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import javax.swing.JFrame;
import org.jdesktop.wonderland.modules.oweditor.client.dummyadapter.ClientUpdateInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.data.DataObjectManagerInterface;


public interface GUIControlerInterface {
    
    public void createFrame();
    
    public void setVisibility(boolean visibility);

    public void getDataUpdate(long id);
    
    public void setDataManager(DataObjectManagerInterface dm);
    
    public void setClientUpdateAdapter (ClientUpdateInterface clientUpdateInterface);
    
    public JFrame getFrame();

}
