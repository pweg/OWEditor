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
    protected SelectAndMoveManager samm = null;
    protected DataObjectObserver domo = null;
    protected EnvironmentObserver eo = null;
    
    private GUIObserverInterface cui = null;
    private DataObjectManagerGUIInterface dmi = null;
    
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
        samm = new SelectAndMoveManager(this);
        domo = new DataObjectObserver(this);
        eo = new EnvironmentObserver(this);
       
        mainScrollPanel = new JScrollPane(drawingPan);
        mainScrollPanel.setWheelScrollingEnabled(false);
        frame.getContentPane().add(mainScrollPanel);
    }

    @Override
    public void setVisibility(boolean visibility) {
        frame.setVisible(visibility);        
    }
    
    /**
     * Sets an update for the adapter
     */
    public void setAdapterUpdate() {
       ArrayList<ShapeObject> list = sm.getUpdateShapes();
       if(list.isEmpty())
           return;
       
       for(ShapeObject shape : list){
           
           long id = shape.getID();
           cui.notifyTranslation(id, shape.getX(), shape.getY(), dmi.getZ(id));
       }
    }

    @Override
    public void registerDataManager(DataObjectManagerGUIInterface dm) {
        dmi = dm;
    }

    @Override
    public void registerGUIObserver(GUIObserverInterface cui) {
        this.cui = cui;
    }

	

	@Override
	public DataObjectObserverInterface getDataObjectObserver() {
		return domo;
	}

	@Override
	public EnvironmentObserverInterface getEnvironmentObserver() {
		return eo;
	}

    

}
