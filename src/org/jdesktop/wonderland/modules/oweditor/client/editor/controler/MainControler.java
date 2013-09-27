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
        cui = adapter.getClientUpdateInterface();
        gui.setDataManager(data.getDataManagerInterface());
        
        adapter.getCurrentWorld();

        gui.setVisibility(true);
        
    }

    @Override
    public void setGUIUpdate(int id) {
        gui.getAdapterUpdate(id);
        
    } 
    
    

    
    public static void main(String[] args) {  
        MainControler app = new MainControler();  
        
        
    }

    @Override
    public void setAdapterTranslationUpdate(int id, int x, int y) {
        
        //do something when Z == -1. Can happen if coordinate is -1;
        cui.updateTranslation(id, x, y, data.getDataManagerInterface().getZ(id));
        
    }

}
