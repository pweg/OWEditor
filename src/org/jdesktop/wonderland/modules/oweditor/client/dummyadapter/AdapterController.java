package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.AdapterControllerMainControllerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.ClientUpdateGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataUpdateAdapterInterface;

/**
 * The adapter controller class, which initiallizes the adapter.
 * @author Patrick
 *
 */
public class AdapterController implements AdapterControllerMainControllerInterface{
    
    
    protected ClientUpdateAdapter cua = null;
    protected ServerUpdateAdapter sua = null;
    
    public AdapterController(){
        
    }

    @Override
    public void initialize() {
        cua = new ClientUpdateAdapter(this);
        sua = new ServerUpdateAdapter(this);
        
        
    }

    @Override
    public void setDataUpdateInterface(DataUpdateAdapterInterface i) {
       sua.setDataUpdateInterface(i);
        
    }

    @Override
    public ClientUpdateGUIInterface getClientUpdateInterface() {
        return cua;
    }

    @Override
    public void getCurrentWorld() {

        WorldBuilder builder = new WorldBuilder(sua);
        builder.build();
        
    }

}
