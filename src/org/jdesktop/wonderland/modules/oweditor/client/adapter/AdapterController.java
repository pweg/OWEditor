package org.jdesktop.wonderland.modules.oweditor.client.adapter;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.AdapterControllerMainControllerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.AdapterObserverInterface;

/**
 * Adapter controller class, which is used to initialize the whole adapter.
 * @author Patrick
 *
 */
public class AdapterController implements AdapterControllerMainControllerInterface{
    
    
    protected ClientUpdateAdapter cua = null;
    protected ServerUpdateAdapter sua = null;
    private boolean isRunning = false;
    
    /**
     * Creates a new adapter controller instance.
     */
    public AdapterController(){
        
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
    public void setDataUpdateInterface(AdapterObserverInterface i) {
       sua.setDataUpdateInterface(i);
        
    }

    @Override
    public GUIObserverInterface getClientUpdateInterface() {
        return cua;
    }

    @Override
    public void getCurrentWorld() {

        AdapterWorldBuilder builder = new AdapterWorldBuilder(sua);
        builder.build();
        
        isRunning = true;
        
    }

}
