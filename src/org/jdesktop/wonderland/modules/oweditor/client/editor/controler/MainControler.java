package org.jdesktop.wonderland.modules.oweditor.client.editor.controler;

import javax.swing.JFrame;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.AdapterControlerMainControlerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.ClientUpdateGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.dummyadapter.AdapterControler;
import org.jdesktop.wonderland.modules.oweditor.client.editor.controlerinterfaces.MainControlerAdapterInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.controlerinterfaces.MainControlerDataInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.controlerinterfaces.MainControlerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.data.DataControler;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataControlerMainControlerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUIControler;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.GUIControlerInterface;


public class MainControler implements MainControlerDataInterface, 
                MainControlerGUIInterface, MainControlerAdapterInterface{
    
    private GUIControlerInterface gui = null;
    private AdapterControlerMainControlerInterface adapter = null;
    private DataControlerMainControlerInterface data = null;
    private ClientUpdateGUIInterface cui = null;
    
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
