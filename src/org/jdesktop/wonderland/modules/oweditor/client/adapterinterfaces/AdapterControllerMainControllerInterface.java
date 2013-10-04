package org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataUpdateAdapterInterface;

public interface AdapterControllerMainControllerInterface {
    
    public void initialize();
    
    public void getCurrentWorld();
        
    public void setDataUpdateInterface(DataUpdateAdapterInterface i);
    
    public ClientUpdateGUIInterface getClientUpdateInterface();
    

}
