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
public class WonderlandAdapterController implements AdapterControllerMainControllerInterface{
    
    
    protected GUIObserver go = null;
    protected CoordinateTranslator ct = null;
    protected ServerUpdateAdapter sua = null;
    protected TransformListener tl = null;
    protected CellStatusListener csl = null;
    
    public WonderlandAdapterController(){
        
    }

    @Override
    public void initialize() {
        ct = new CoordinateTranslator();
        go = new GUIObserver(this);
        sua = new ServerUpdateAdapter(this);
        tl = new TransformListener(sua);
        csl = new CellStatusListener(sua);
        CellManager.getCellManager().addCellStatusChangeListener(csl);
    }

    @Override
    public void registerDataUpdateInterface(AdapterObserverInterface i) {
       sua.setDataUpdateInterface(i);
        
    }

    @Override
    public GUIObserverInterface getClientUpdateInterface() {
        return go;
    }

    @Override
    public void getCurrentWorld() {
        WorldBuilder builder = new WorldBuilder(this, sua);
        builder.build();
    }

    public CoordinateTranslatorInterface getCoordinateTranslator() {
        return ct;
    }

}
