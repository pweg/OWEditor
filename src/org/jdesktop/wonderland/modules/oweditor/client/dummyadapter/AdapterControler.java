package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.AdapterControlerMainControlerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.ClientUpdateGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataUpdateAdapterInterface;

public class AdapterControler implements AdapterControlerMainControlerInterface{
    
    
    private ClientUpdateAdapter cua = null;
    private ServerUpdateAdapter sua = null;
    private boolean isRunning = false;
    
    public AdapterControler(){
        
    }

    @Override
    public void initialize() {
        cua = new ClientUpdateAdapter(this);
        sua = new ServerUpdateAdapter(this);
        
        
    }
    
    public ClientUpdateAdapter getCUA(){
        return cua;
    }
    
    public ServerUpdateAdapter getSUA(){
        return sua;
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

        AdapterWorldBuilder builder = new AdapterWorldBuilder(sua);
        builder.build();
        
        isRunning = true;
        
    }

}
