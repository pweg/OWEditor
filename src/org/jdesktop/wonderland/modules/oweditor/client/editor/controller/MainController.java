package org.jdesktop.wonderland.modules.oweditor.client.editor.controller;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.AdapterControllerMainControllerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.dummyadapter.DummyAdapterController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.controllerinterfaces.MainControllerPluginInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.controllerinterfaces.MainControllerDataInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.controllerinterfaces.MainControllerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.data.DataController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataControllerMainControllerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUIController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.GUIControllerInterface;
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
    
    private GUIControllerInterface gui = null;
    private AdapterControllerMainControllerInterface adapter = null;
    private DataControllerMainControllerInterface data = null;
    
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

        gui.registerDataManager(data.getDataManagerInterface());
        gui.registerGUIObserver(adapter.getClientUpdateInterface());
        gui.registerCoordinateTranslator(adapter.getCoordinateTranslator());
        
        data.registerDataObjectObserver(gui.getDataObjectObserver());
        data.registerEnvironmentObserver(gui.getEnvironmentObserver());
        data.registerCoordinateTranslator(adapter.getCoordinateTranslator());
        
        adapter.registerDataUpdateInterface(data.getDataUpdateInterface());         
        adapter.getCurrentWorld();
    
    }    

}
