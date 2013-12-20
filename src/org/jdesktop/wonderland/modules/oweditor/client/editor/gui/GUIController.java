package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.Frame;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.FrameInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.Input;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.InputInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.DataObjectObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.EnvironmentObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.GUIControllerInterface;

/**
 * The main controller unit for the GUI package.
 * It implements the controllerinterface and is the main communication
 * unit between 
 * @author Patrick
 *
 */
public class GUIController implements GUIControllerInterface{

    protected DataObjectObserver domo = null;
    protected EnvironmentObserver eo = null;
    protected FrameInterface frame = null;
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
        
        frame = new Frame(ac);
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
    }

    @Override
    public void registerGUIObserver(GUIObserverInterface cui) {
        ac.registerObserver(cui);
    }

    

    @Override
    public DataObjectObserverInterface getDataObjectObserver() {
        return domo;
    }

    @Override
    public EnvironmentObserverInterface getEnvironmentObserver() {
        return eo;
    }

    public void registerCoordinateTranslator(CoordinateTranslatorInterface coordinateTranslator) {
        frame.setCoordinateTranslator(coordinateTranslator);
    }
    

}
