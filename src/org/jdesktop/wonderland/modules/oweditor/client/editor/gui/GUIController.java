package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.util.ArrayList;

import javax.swing.JScrollPane;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;
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

    protected WindowFrame frame = null;
    protected WindowDrawingPanel drawingPan = null;
    protected JScrollPane mainScrollPanel = null;
    protected ShapeManager sm = null;
    protected SelectionManager samm = null;
    protected DataObjectObserver domo = null;
    protected EnvironmentObserver eo = null;
    protected ShapeCopyManager scm = null;
    
    
    private DataObjectManagerGUIInterface dmi = null;
    private AdapterCommunication ac = null;
    
    public GUIController(){
    }
    
    @Override
    public void createFrame() {
        if(frame == null){
            frame = new WindowFrame();
            initiallize();
        }
    }
    
    /**
     * Initializes all GUI components.
     */
    private void initiallize(){
        sm = new ShapeManager();
        drawingPan = new WindowDrawingPanel(this);
        samm = new SelectionManager(this);
        domo = new DataObjectObserver(this);
        eo = new EnvironmentObserver(this);
        ac = new AdapterCommunication();
        scm = new ShapeCopyManager(sm);
       
        mainScrollPanel = new JScrollPane(drawingPan);
        mainScrollPanel.setWheelScrollingEnabled(false);
        frame.getContentPane().add(mainScrollPanel);
    }

    @Override
    public void setVisibility(boolean visibility) {
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
    
    public void setTranslationUpdate(){
        ArrayList<ShapeObject> list = sm.getUpdateShapes();
        
        if(list.isEmpty())
            return;
        
        for(ShapeObject shape : list){
            long id = shape.getID();
            ac.setTranslationUpdate(id, shape.getX(), shape.getY(), dmi.getZ(id));
        }
    }
    
    public void setCopyUpdate(){
        ArrayList<ShapeObject> list = scm.getTranslatedShapes();
        
        for(ShapeObject shape : list){
            long id = shape.getID();
            ac.setCopyUpdate(id, shape.getX(), shape.getY(), dmi.getZ(id));
        }
    }
    
    public void setObjectRemoval(long id){
        ac.setObjectRemoval(id);
    }
    

}
