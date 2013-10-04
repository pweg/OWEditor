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


public class MainController implements MainControllerDataInterface, 
                MainControllerGUIInterface, MainControllerAdapterInterface{
    
    private GUIControllerInterface gui = null;
    private AdapterControllerMainControllerInterface adapter = null;
    private DataControllerMainControllerInterface data = null;
    
    public MainController(){
        
        gui = new GUIController(this);
        gui.createFrame();
        gui.setVisibility(false);
        
        data = new DataController(this);
        adapter = new AdapterController();
        data.initialize();
        adapter.initialize();

        adapter.setDataUpdateInterface(data.getDataUpdateInterface()); 
        gui.setDataManager(data.getDataManagerInterface());
        gui.setClientUpdateAdapter(adapter.getClientUpdateInterface());
        data.setGUIControler(gui);
        
        adapter.getCurrentWorld();
        
    }    
    

    
    /*public static void main(String[] args) {  
        MainControler app = new MainControler();  
        
        
    }*/

    public void setVisible(boolean visibility) {
        gui.setVisibility(visibility);
    }


}
