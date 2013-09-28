package org.jdesktop.wonderland.modules.oweditor.client.editor.controler;

import org.jdesktop.wonderland.modules.oweditor.client.adapter.AdapterControler;
import org.jdesktop.wonderland.modules.oweditor.client.adapter.AdapterControlerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.adapter.ClientUpdateInterface;
import org.jdesktop.wonderland.modules.oweditor.client.data.DataControler;
import org.jdesktop.wonderland.modules.oweditor.client.data.DataControlerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUIControler;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUIControlerInterface;


public class MainControler implements MainControlerDataInterface, 
                MainControlerGUIInterface{
    
    private GUIControlerInterface gui = null;
    private AdapterControlerInterface adapter = null;
    private DataControlerInterface data = null;
    private ClientUpdateInterface cui = null;
    
    public MainControler(){
        
        gui = new GUIControler(this);
        gui.createFrame();
        gui.setVisibility(false);
        
        data = new DataControler(this);
        adapter = new AdapterControler();
        data.initialize();
        adapter.initialize();

        adapter.setDataUpdateInterface(data.getDataUpdateInterface()); 
        gui.setDataManager(data.getDataManagerInterface());
        gui.setClientUpdateAdapter(adapter.getClientUpdateInterface());
        data.setGUIControler(gui);
        
        adapter.getCurrentWorld();

        gui.setVisibility(true);
        
    }    
    

    
    public static void main(String[] args) {  
        MainControler app = new MainControler();  
        
        
    }


}
