package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.util.ArrayList;

import javax.swing.JScrollPane;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;
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

    protected WindowFrame frame = null;
    protected WindowDrawingPanel drawingPan = null;
    protected JScrollPane mainScrollPanel = null;
    protected DataObjectObserver domo = null;
    protected EnvironmentObserver eo = null;
    protected ExternalShapeFacadeInterface esmi = null;
    
    protected WindowPopupMenu popupMenu = null;
    private DataObjectManagerGUIInterface dmi = null;
    private AdapterCommunication ac = null;

    protected MouseAndKeyListener mkListener = null;
    
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

       
        drawingPan = new WindowDrawingPanel(this);
        domo = new DataObjectObserver(this);
        eo = new EnvironmentObserver(this);
        ac = new AdapterCommunication();
       
        
        mainScrollPanel = new JScrollPane(drawingPan);
        mainScrollPanel.setWheelScrollingEnabled(false);
        frame.getContentPane().add(mainScrollPanel);
        popupMenu = new WindowPopupMenu(this); 
        esmi = new ExternalShapeFacade(this);

        mkListener = new MouseAndKeyListener(this);
        drawingPan.addMouseListener(mkListener);
        drawingPan.addMouseMotionListener(mkListener);
        drawingPan.addMouseWheelListener(mkListener);
        frame.addKeyListener(mkListener);
    }
    
    public WindowDrawingPanel getDrawingPan(){
        return drawingPan;
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
    

}
