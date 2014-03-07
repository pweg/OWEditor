package org.jdesktop.wonderland.modules.oweditor.client.editor.controller;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.AdapterControllerMainControllerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.dummyadapter.DummyAdapterController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.controllerinterfaces.MainControllerPluginInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.controllerinterfaces.MainControllerDataInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.controllerinterfaces.MainControllerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.data.DataController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataToMainController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUIController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IGUIController;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.WonderlandAdapterController;

/**
 * The main controller class is only used to set up the remaining packages
 * and to exchange interfaces between them.
 * Furthermore it is used to set the frame visibile.
 * 
 * @author Patrick
 *
 */
public class MainController implements MainControllerDataInterface, 
                MainControllerGUIInterface, MainControllerPluginInterface{
    
    private IGUIController gui = null;
    private AdapterControllerMainControllerInterface adapter = null;
    private IDataToMainController data = null;
    
    /**
     * Creates a new instance of the main controller and
     * sets up every other package.
     */
    public MainController(){
    }

    @Override
    public void setVisible(boolean visibility) {
        gui.setVisible(visibility);
    }

    @Override
    public void initialize(byte adaptertype) {
        
        gui = new GUIController();
        gui.initializeGUI();
        gui.setVisible(false);
        
        data = new DataController();
        
        //switch to create the right adapter.
        switch(adaptertype){
            case 0: 
                adapter = new DummyAdapterController();
                break;
            case 1: 
                adapter = new WonderlandAdapterController();
                break;
            default:
                throw new IllegalArgumentException(
                        "Unknown type");
        }
        
        data.initialize();
        adapter.initialize();

        //register interfaces to the parts.
        gui.registerDataManager(data.getDataManagerInterface());
        adapter.registerDataUpdateInterface(data.getDataUpdateInterface());  
        data.registerCoordinateTranslator(adapter.getCoordinateTranslator());
        
        //this needs to be done here, because the adapter does not
        //get an interface for the gui.
        gui.registerGUIObserver(adapter.getClientUpdateInterface());
        
        //Starts building the world.      
        adapter.getCurrentWorld();
    
    }    

}
