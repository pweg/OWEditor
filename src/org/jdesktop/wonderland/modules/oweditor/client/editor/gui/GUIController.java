package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.util.ArrayList;

import javax.swing.JScrollPane;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.ClientUpdateGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.controllerinterfaces.MainControllerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;
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
    protected GUISelectAndMoveManager samm = null;
    
    private MainControllerGUIInterface mc = null;
    private ClientUpdateGUIInterface cui = null;
    private DataObjectManagerGUIInterface dmi = null;
    
    public GUIController(MainControllerGUIInterface mc){
        this.mc = mc;
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
        samm = new GUISelectAndMoveManager(this);
       
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
           cui.updateTranslation(id, shape.getX(), shape.getY(), dmi.getZ(id));
       }
    }

    @Override
    public void setDataUpdate(long id) {
        sm.setDataUpdate(id);
        drawingPan.repaint();
        
    }

    @Override
    public void setDataManager(DataObjectManagerGUIInterface dm) {
        dmi = dm;
        sm.setDataManager(dm);
    }

    @Override
    public void setClientUpdateAdapter(ClientUpdateGUIInterface cui) {
        this.cui = cui;
    }

	@Override
	public void setWidth(int width) {
		drawingPan.setNewWidth(width);
	}

	@Override
	public void setHeight(int height) {
		drawingPan.setNewHeight(height);
	}
	
	@Override
	public void setMinX(int x) {
		drawingPan.setNewMinX(x);
	}

	@Override
	public void setMinY(int y) {
		drawingPan.setNewMinY(y);
	}

    

}
