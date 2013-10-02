package org.jdesktop.wonderland.modules.oweditor.client.adapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.data.DataUpdateInterface;

public interface AdapterControlerInterface {
    
    public void initialize();
    
    public void getCurrentWorld();
        
    public void setDataUpdateInterface(DataUpdateInterface i);
    
    public ClientUpdateInterface getClientUpdateInterface();
    

}
