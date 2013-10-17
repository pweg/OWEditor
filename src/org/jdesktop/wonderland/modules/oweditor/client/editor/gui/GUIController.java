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
    protected ShapeSelectionManager samm = null;
    protected DataObjectObserver domo = null;
    protected EnvironmentObserver eo = null;
    protected ShapeCopyManager scm = null;
    protected ShapeRotationManager srm = null;
    protected ShapeTranslationManager stm = null;
    
    protected WindowPopupMenu popupMenu = null;
    private DataObjectManagerGUIInterface dmi = null;
    private AdapterCommunication ac = null;
    
    public GUIController(){
    }
    
    @Override
    public void createFrame() {
        if(frame == null){
            frame = new WindowFrame(this);
            initiallize();
        }
    }
    
    /**
     * Initializes all GUI components.
     */
    private void initiallize(){
        sm = new ShapeManager(this);
        drawingPan = new WindowDrawingPanel(this);
        samm = new ShapeSelectionManager(this);
        domo = new DataObjectObserver(this);
        eo = new EnvironmentObserver(this);
        ac = new AdapterCommunication();
        scm = new ShapeCopyManager(this);
        srm = new ShapeRotationManager(this);
        stm = new ShapeTranslationManager(this);

        mainScrollPanel = new JScrollPane(drawingPan);
        mainScrollPanel.setWheelScrollingEnabled(false);
        frame.getContentPane().add(mainScrollPanel);
        popupMenu = new WindowPopupMenu(this);     
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
        ArrayList<ShapeObject> list = stm.getUpdateShapes();
        
        if(list.isEmpty())
            return;
        
        for(ShapeObject shape : list){
            long id = shape.getID();
            ac.setTranslationUpdate(id, shape.getX(), shape.getY());
        }
    }
    
    public void setCopyUpdate(){
        ArrayList<ShapeObject> list = scm.getTranslatedShapes();
        
        for(ShapeObject shape : list){
            long id = shape.getID();
            ac.setCopyUpdate(id, shape.getX(), shape.getY());
        }
    }
    
    public void setObjectRemoval(long id){
        ac.setObjectRemoval(id);
    }
    

}
