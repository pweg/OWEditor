package org.jdesktop.wonderland.modules.oweditor.client.editor.controler;

import javax.swing.JFrame;
import org.jdesktop.wonderland.modules.oweditor.client.dummyadapter.AdapterControler;
import org.jdesktop.wonderland.modules.oweditor.client.dummyadapter.AdapterControlerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.dummyadapter.ClientUpdateInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.data.DataControler;
import org.jdesktop.wonderland.modules.oweditor.client.editor.data.DataControlerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUIControler;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUIControlerInterface;


public class MainControler implements MainControlerDataInterface, 
                MainControlerGUIInterface, MainControlerAdapterInterface{
    
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
        
    }    
    

    
    /*public static void main(String[] args) {  
        MainControler app = new MainControler();  
        
        
    }*/

    public void setVisible(boolean visibility) {
        gui.setVisibility(visibility);
    }


}
