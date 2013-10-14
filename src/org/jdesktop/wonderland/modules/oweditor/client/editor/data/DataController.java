package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataControllerMainControllerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.AdapterObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.DataObjectObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.EnvironmentObserverInterface;

/**
 * This is the main data controller class, which is used to 
 * initialize the whole data package.
 * 
 * @author Patrick
 *
 */
public class DataController implements DataControllerMainControllerInterface {
    
    protected AdapterObserver du = null;
    protected DataObjectManager dm = null;
    protected EnvironmentManager em = null;
    
    public DataController(){
    }
    
    @Override
    public void initialize() {
        em = new EnvironmentManager();
        dm = new DataObjectManager(this);
        du = new AdapterObserver(dm);
        
    }

    @Override
    public AdapterObserverInterface getDataUpdateInterface() {
        return du;
    }

    @Override
    public DataObjectManagerGUIInterface getDataManagerInterface() {
        return dm;
    }

    @Override
    public void registerDataObjectObserver(
            DataObjectObserverInterface domo) {
        dm.registerObserver(domo);
        
    }

    @Override
    public void registerEnvironmentObserver(EnvironmentObserverInterface en) {
        em.registerObserver(en);
    }

    @Override
    public void registerCoordinateTranslator(CoordinateTranslatorInterface ct) {
        dm.registerCoordinateTranslator(ct);        
    }


}
