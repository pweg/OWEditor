package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.AdapterControllerMainControllerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.AdapterObserverInterface;

/**
 * The adapter controller class, which initiallizes the adapter.
 * @author Patrick
 *
 */
public class DummyAdapterController implements AdapterControllerMainControllerInterface{
    
    
    protected GUIObserver cua = null;
    protected ServerUpdateAdapter sua = null;
    protected ServerSimulator ses = null;
    protected CoordinateTranslator ct = null;
    protected BackupManager bom = null;
    
    public DummyAdapterController(){
        
    }

    @Override
    public void initialize() {
        cua = new GUIObserver(this);
        sua = new ServerUpdateAdapter(this);
        ses = new ServerSimulator();
        ct = new CoordinateTranslator();
        bom = new BackupManager();
        
        registerComponents();
    }
    
    private void registerComponents(){
        ses.registerServerUpdate(sua);
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

    @Override
    public CoordinateTranslatorInterface getCoordinateTranslator() {
        return ct;
    }

}
