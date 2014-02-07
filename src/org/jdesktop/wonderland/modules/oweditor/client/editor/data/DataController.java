package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataToMainController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataToGUI;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IAdapterObserver;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.ITransformedObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IDataObjectObserver;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IEnvironmentObserver;

/**
 * This is the main data controller class, which is used to 
 * initialize the whole data package.
 * 
 * @author Patrick
 *
 */
public class DataController implements IDataToMainController, 
        IDataToGUI {
    
    protected AdapterObserver du = null;
    protected DataObjectManager dm = null;
    protected EnvironmentManager em = null;
    
    public DataController(){
    }
    
    @Override
    public void initialize() {
        em = new EnvironmentManager();
        dm = new DataObjectManager(this);
        du = new AdapterObserver(dm, em);
        
    }

    @Override
    public IAdapterObserver getDataUpdateInterface() {
        return du;
    }

    @Override
    public IDataToGUI getDataManagerInterface() {
        return this;
    }

    @Override
    public void registerDataObjectObserver(
            IDataObjectObserver domo) {
        dm.registerObserver(domo);
        
    }

    @Override
    public void registerEnvironmentObserver(IEnvironmentObserver en) {
        em.registerObserver(en);
    }

    @Override
    public void registerCoordinateTranslator(CoordinateTranslatorInterface ct) {
        dm.registerCoordinateTranslator(ct);        
    }

    public IDataObject getObject(long id) {
        return dm.getObject(id);
    }

    public ITransformedObject getTransformedObject(long id) {
        return dm.getTransformedObject(id);
    }

    public float getZ(long id) {
        return dm.getZ(id);
    }

    public Point2D.Double transformCoordsBack(Point coordinates) {
        return dm.transformCoordsBack(coordinates);
    }

    public Point2D.Double transformCoordsBack(Point coordinates, 
            int width, int height) {
        return dm.transformCoordsBack(coordinates, width, height);
    }

    public String[] getServerList() {
        return em.getServerList();
    }


}
