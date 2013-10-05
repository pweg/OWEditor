package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import org.jdesktop.wonderland.modules.oweditor.client.editor.controllerinterfaces.MainControllerDataInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataControllerMainControllerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataUpdateAdapterInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.DataObjectManagerObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.GUIControllerInterface;

/**
 * This is the main data controller class, which is used to 
 * initialize the whole data package.
 * 
 * @author Patrick
 *
 */
public class DataController implements DataControllerMainControllerInterface {
    
    protected DataUpdate du = null;
    protected DataObjectManager dm = null;
    protected EnvironmentManager em = null;
    
    private MainControllerDataInterface mc = null;
    private GUIControllerInterface gci = null;
    
    public DataController(MainControllerDataInterface mc){
        this.mc = mc;
    }
    
    @Override
    public void initialize() {
    	em = new EnvironmentManager(this);
        dm = new DataObjectManager(this);
        du = new DataUpdate(dm);
        
    }

    @Override
    public DataUpdateAdapterInterface getDataUpdateInterface() {
        return du;
    }

    @Override
    public DataObjectManagerGUIInterface getDataManagerInterface() {
        return dm;
    }

    @Override
    public void setGUIControler(GUIControllerInterface gui) {
        gci = gui;
    }

    public void setNewWidth(int width){
    	gci.setWidth(width);
    }
    
    public void setNewHeight(int height){
    	gci.setHeight(height);
    }
    public void setNewMinX(int x){
    	gci.setMinX(x);
    }
    
    public void setNewMinY(int y){
    	gci.setMinY(y);
    }

	@Override
	public void registerDataObjectObserver(
			DataObjectManagerObserverInterface domo) {
		dm.registerDataObjectObserver(domo);
		
	}


}
