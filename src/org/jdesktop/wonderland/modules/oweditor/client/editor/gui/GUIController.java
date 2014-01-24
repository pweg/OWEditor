package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.Input;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.InputInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.Window;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.WindowInterface;
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

    protected DataObjectObserver domo = null;
    protected EnvironmentObserver eo = null;
    protected WindowInterface frame = null;
    protected InputInterface input = null;
    
    private DataObjectManagerGUIInterface dmi = null;
    private AdapterCommunication ac = null;
    
    public GUIController(){
    }

    /**
     * Initializes all GUI components.
     */
    @Override
    public void initializeGUI() {
               
        domo = new DataObjectObserver(this);
        eo = new EnvironmentObserver(this);
        ac = new AdapterCommunication();
        
        frame = new Window(ac);
        input = new Input();
        
        frame.addMouseListener(input.getMouseListener());
        frame.addKeyListener(input.getKeyListener());
        frame.addMouseWheelListener(input.getMouseWheelListener());
        
        registerInterfaces();
    }
    
    private void registerInterfaces(){
        frame.registerInputInterface(input.getFrameInterface());
        
        input.registerGraphicInterface(frame.getGraphicInputInterface());
        input.registerFrameInterface(frame.getInputInterface());
        
    }

    @Override
    public void setVisible(boolean visibility) {
        frame.setVisible(visibility);        
    }


    @Override
    public void registerDataManager(DataObjectManagerGUIInterface dm) {
        dmi = dm;
        frame.registerDataManager(dm);
    }

    @Override
    public void registerGUIObserver(GUIObserverInterface cui) {
        ac.registerObserver(cui);
    }

    

    @Override
    public IDataObjectObserver getDataObjectObserver() {
        return domo;
    }

    @Override
    public IEnvironmentObserver getEnvironmentObserver() {
        return eo;
    }
    

}
