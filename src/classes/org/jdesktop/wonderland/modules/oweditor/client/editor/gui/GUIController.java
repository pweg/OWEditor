package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataToGUI;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.Input;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.IInput;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.Window;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindow;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IDataObjectObserver;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IEnvironmentObserver;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IGUIController;

/**
 * The main controller unit for the GUI package.
 * It implements the controllerinterface and is the main communication
 * unit between 
 * @author Patrick
 *
 */
public class GUIController implements IGUIController{

    protected DataObjectObserver doo = null;
    protected EnvironmentObserver eo = null;
    protected IWindow window = null;
    protected IInput input = null;
    protected DataManager dr = null;
    
    private AdapterCommunication ac = null;
    
    public GUIController(){
    }

    /**
     * Initializes all GUI components.
     */
    @Override
    public void initializeGUI() {
               
        doo = new DataObjectObserver(this);
        eo = new EnvironmentObserver(this);
        ac = new AdapterCommunication();
        dr = new DataManager();
        
        window = new Window(ac);
        input = new Input();
        
        registerInterfaces();
    }
    
    private void registerInterfaces(){
        window.registerInputInterface(input.getWindowInterface());
        ac.registerWindow(window);
        
        input.registerGraphicInterface(window.getGraphicInputInterface());
        input.registerWindowInterface(window.getInputInterface());
    }

    @Override
    public void setVisible(boolean visibility) {
        window.setVisible(visibility);        
    }

    @Override
    public void registerDataManager(IDataToGUI dm) {
        dm.registerDataObjectObserver(doo);
        dm.registerEnvironmentObserver(eo);
        dr.registerDataInterface(dm);
        ac.registerDataManager(dm);
        window.registerDataManager(dr);
    }

    @Override
    public void registerGUIObserver(GUIObserverInterface cui) {
        ac.registerObserver(cui);
    }
    
    @Override
    public IDataObjectObserver getDataObjectObserver() {
        return doo;
    }

    @Override
    public IEnvironmentObserver getEnvironmentObserver() {
        return eo;
    }
}
