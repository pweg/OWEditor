package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.cell.CellManager;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentRepositoryException;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.AdapterControllerMainControllerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IAdapterObserver;

/**
 * The adapter controller class, which initiallizes the adapter.
 * 
 * @author Patrick
 *
 */
public class WonderlandAdapterController implements AdapterControllerMainControllerInterface{
    
    protected FileManager fm = null;
    protected GUIEventManager gem = null;
    protected CoordinateTranslator ct = null;
    protected ServerEventManager sem = null;
    protected TransformListener tl = null;
    protected CellStatusListener csl = null;
    protected BackupManager bm = null;
    protected SessionManager sm = null;
    protected ServerCommunication sc = null;
    protected LateTransformationManager ltm = null;
    protected CopyNameManager cnm = null;
    protected SecurityManager secm =null;
    
    public WonderlandAdapterController(){
        
    }

    @Override
    public void initialize() {
        cnm = new CopyNameManager();
        ltm = new LateTransformationManager();
        ct = new CoordinateTranslator();
        gem = new GUIEventManager(this);
        sem = new ServerEventManager(this);
        sc = new ServerCommunication(this);
        tl = new TransformListener(sem);
        csl = new CellStatusListener(sem);
        sm = new SessionManager();
        bm = new BackupManager(sm);
        fm = new FileManager();
        secm = new SecurityManager(this);
        CellManager.getCellManager().addCellStatusChangeListener(csl);
    }

    @Override
    public void registerDataUpdateInterface(IAdapterObserver i) {
       sem.registerDataInterface(i);
        
    }

    @Override
    public GUIObserverInterface getClientUpdateInterface() {
        return gem;
    }

    @Override
    public void getCurrentWorld() {
        try {
            sem.setUserDir(fm.getUserDir());
            sem.setImageLib(fm.getUserLib());
        } catch (ContentRepositoryException ex) {
            Logger.getLogger(WonderlandAdapterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        WorldBuilder builder = new WorldBuilder(this, sem);
        builder.build();
    }

    public CoordinateTranslatorInterface getCoordinateTranslator() {
        return ct;
    }

}
