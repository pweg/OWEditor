package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.ExternalFrameFacade;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.ExternalFrameFacadeInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape.ExternalShapeFacade;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape.ExternalShapeFacadeInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape.ShapeDraggingObject;
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
    protected ExternalShapeFacadeInterface esfi = null;
    protected ExternalFrameFacadeInterface effi = null;
    
    private DataObjectManagerGUIInterface dmi = null;
    private AdapterCommunication ac = null;

    protected MouseAndKeyListener mkListener = null;
    
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
        esfi = new ExternalShapeFacade(this);
        effi = new ExternalFrameFacade();
        

        mkListener = new MouseAndKeyListener(this);
        effi.addMouseListenerToDrawingPan(mkListener);
        effi.registerShapeInterface(esfi);
        esfi.registerFrameInterface(effi.getShapeInterface());
    }

    @Override
    public void setVisible(boolean visibility) {
        effi.setVisible(visibility);        
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
    
    public void setTranslationUpdate(ArrayList<ShapeDraggingObject> list){
        
        if(list.isEmpty())
            return;
        
        for(ShapeDraggingObject shape : list){
            long id = shape.getID();
            ac.setTranslationUpdate(id, shape.getX(), shape.getY());
        }
    }
    
    public void setCopyUpdate(ArrayList<ShapeDraggingObject> list){
        
        for(ShapeDraggingObject shape : list){
            long id = shape.getID();
            ac.setCopyUpdate(id, shape.getX(), shape.getY());
        }
    }
    
    public void setObjectRemoval(long id){
        ac.setObjectRemoval(id);
    }
    
    public void setRotationStrategy(){
        mkListener.setRotationStrategy();
    }
    
    public void setRotationCenterStrategy(){
        mkListener.setRotationCenterStrategy();
        
    }

    public void setRotationUpdate(ArrayList<ShapeDraggingObject> list) {

        for(ShapeDraggingObject shape : list){
            long id = shape.getID();
            ac.setRotationUpdate(id, shape.getX(), 
                    shape.getY(), shape.getRotation());
        }
    }

    public double getScale() {
        return effi.getScale();
    }
    

}
