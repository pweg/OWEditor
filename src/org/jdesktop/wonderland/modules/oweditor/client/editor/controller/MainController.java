package org.jdesktop.wonderland.modules.oweditor.client.editor.controller;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.AdapterControllerMainControllerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.dummyadapter.AdapterController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.controllerinterfaces.MainControllerAdapterInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.controllerinterfaces.MainControllerDataInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.controllerinterfaces.MainControllerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.data.DataController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataControllerMainControllerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUIController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.GUIControllerInterface;

/**
 * The main controller class is only used to set up the remaining packages
 * and to exchange interfaces between them.
 * Furthermore it is used to set the frame visibile.
 * 
 * @author Patrick
 *
 */
public class MainController implements MainControllerDataInterface, 
                MainControllerGUIInterface, MainControllerAdapterInterface{
    
    private GUIControllerInterface gui = null;
    private AdapterControllerMainControllerInterface adapter = null;
    private DataControllerMainControllerInterface data = null;
    
    /**
     * Creates a new instance of the main controller and
     * sets up every other package.
     */
    public MainController(){
        
        gui = new GUIController();
        gui.createFrame();
        gui.setVisibility(false);
        
        data = new DataController();
        adapter = new AdapterController();
        data.initialize();
        adapter.initialize();

        adapter.registerDataUpdateInterface(data.getDataUpdateInterface()); 
        gui.registerDataManager(data.getDataManagerInterface());
        gui.registerGUIObserver(adapter.getClientUpdateInterface());
        data.registerDataObjectObserver(gui.getDataObjectObserver());
        data.registerEnvironmentObserver(gui.getEnvironmentObserver());
        
        adapter.getCurrentWorld();
    }

	@Override
	public void setVisible(boolean visibility) {
		gui.setVisibility(visibility);
	}    

}
