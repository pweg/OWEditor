package org.jdesktop.wonderland.modules.oweditor.client.adapter;

import org.jdesktop.wonderland.modules.oweditor.client.data.DataUpdateInterface;

public class AdapterControler implements AdapterControlerInterface{
    
    
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
    public void setDataUpdateInterface(DataUpdateInterface i) {
       sua.setDataUpdateInterface(i);
        
    }

    @Override
    public ClientUpdateInterface getClientUpdateInterface() {
        return cua;
    }

    @Override
    public void getCurrentWorld() {

        AdapterWorldBuilder builder = new AdapterWorldBuilder(sua);
        builder.build();
        
        isRunning = true;
        
    }

}
