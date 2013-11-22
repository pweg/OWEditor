package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import org.jdesktop.wonderland.client.cell.CellManager;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.AdapterControllerMainControllerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.AdapterObserverInterface;

/**
 * The adapter controller class, which initiallizes the adapter.
 * @author Patrick
 *
 */
public class AdapterController implements AdapterControllerMainControllerInterface{
    
    
    protected GUIObserver go = null;
    protected CoordinateTranslator ct = null;
    protected UpdateManager um = null;
    protected TransformListener tl = null;
    protected CellStatusListener csl = null;
    protected BackupManager bm = null;
    protected SessionManager sm = null;
    protected ServerCommunication sc = null;
    
    public AdapterController(){
        
    }

    @Override
    public void initialize() {
        ct = new CoordinateTranslator();
        go = new GUIObserver(this);
        um = new UpdateManager(this);
        sc = new ServerCommunication(this);
        tl = new TransformListener(um);
        csl = new CellStatusListener(um);
        sm = new SessionManager();
        bm = new BackupManager(sm);
        CellManager.getCellManager().addCellStatusChangeListener(csl);
    }

    @Override
    public void registerDataUpdateInterface(AdapterObserverInterface i) {
       um.setDataUpdateInterface(i);
        
    }

    @Override
    public GUIObserverInterface getClientUpdateInterface() {
        return go;
    }

    @Override
    public void getCurrentWorld() {
        WorldBuilder builder = new WorldBuilder(this, um);
        builder.build();
    }

    public CoordinateTranslatorInterface getCoordinateTranslator() {
        return ct;
    }

}
