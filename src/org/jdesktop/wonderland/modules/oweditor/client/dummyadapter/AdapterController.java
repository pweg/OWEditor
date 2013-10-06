package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.AdapterControllerMainControllerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.AdapterObserverInterface;

/**
 * The adapter controller class, which initiallizes the adapter.
 * @author Patrick
 *
 */
public class AdapterController implements AdapterControllerMainControllerInterface{
    
    
    protected GUIObserver cua = null;
    protected ServerUpdateAdapter sua = null;
    protected ServerSimulator ses = null;
    
    public AdapterController(){
        
    }

    @Override
    public void initialize() {
        cua = new GUIObserver(this);
        sua = new ServerUpdateAdapter(this);
        ses = new ServerSimulator();
        
        
    }

    @Override
    public void registerDataUpdateInterface(AdapterObserverInterface i) {
       sua.setDataUpdateInterface(i);
        
    }

    @Override
    public GUIObserverInterface getClientUpdateInterface() {
        return cua;
    }

    @Override
    public void getCurrentWorld() {
        WorldBuilder builder = new WorldBuilder(this, sua);
        builder.build();
    }

}
