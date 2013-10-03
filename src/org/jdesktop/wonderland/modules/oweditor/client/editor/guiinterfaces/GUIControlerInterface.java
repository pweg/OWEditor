package org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces;

import javax.swing.JFrame;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.ClientUpdateGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;


public interface GUIControlerInterface {
    
    public void createFrame();
    
    public void setVisibility(boolean visibility);

    public void getDataUpdate(long id);
    
    public void setDataManager(DataObjectManagerGUIInterface dm);
    
    public void setClientUpdateAdapter (ClientUpdateGUIInterface clientUpdateInterface);
    
    public JFrame getFrame();

}
