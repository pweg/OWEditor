package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.AdapterControllerMainControllerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IAdapterObserver;

/**
 * The adapter controller class, which initiallizes the adapter.
 * @author Patrick
 *
 */
public class DummyAdapterController implements AdapterControllerMainControllerInterface{
    
    
    protected GUIEventManager cua = null;
    protected ServerEventManager da = null;
    protected ServerSimulator server = null;
    protected CoordinateTranslator ct = null;
    protected BackupManager bom = null;
    
    public DummyAdapterController(){
        
    }

    @Override
    public void initialize() {
        cua = new GUIEventManager(this);
        da = new ServerEventManager(this);
        server = new ServerSimulator();
        ct = new CoordinateTranslator();
        bom = new BackupManager();
        
        registerComponents();
    }
    
    private void registerComponents(){
        server.registerServerUpdate(da);
    }

    @Override
    public void registerDataUpdateInterface(IAdapterObserver i) {
       da.setDataUpdateInterface(i);
        
    }

    @Override
    public GUIObserverInterface getClientUpdateInterface() {
        return cua;
    }

    @Override
    public void getCurrentWorld() {
        WorldBuilder builder = new WorldBuilder(this, da);
        builder.build();
    }

    @Override
    public CoordinateTranslatorInterface getCoordinateTranslator() {
        return ct;
    }

}