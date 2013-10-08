package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import java.util.HashMap;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.DataObjectObserverInterface;

/**
 * Stores, manages and creates data objects. 
 * 
 * @author Patrick
 *
 */
public class DataObjectManager implements DataObjectManagerGUIInterface{

    private DataController dc = null;
    private DataObjectObserverInterface domo = null;
    
    private HashMap<Long, DataObject> data = null;
    
    /**
     * Creates a new instance of the data manager.
     * 
     * @param d a dataController instance.
     */
    DataObjectManager(DataController d){
        dc = d;
        data = new HashMap<Long, DataObject>();
    }
    
    /**
     * Stores the data object given in the parameters.
     * 
     * @param dataObject the data object to store.
     */
    public void createNewObject(DataObjectInterface dataObject){
        long id = dataObject.getID();
        
        dc.em.setX(dataObject.getX(), dataObject.getWidth());
        dc.em.setY(dataObject.getY(), dataObject.getHeight());
        
        if(dataObject instanceof DataObject){
            data.put(id, (DataObject) dataObject);
            domo.notify(dataObject);
        }
    }
    
    public void updateTranslation(long id, int x, int y, int z){
        DataObject d = data.get(id);
        
        if(d == null)
            return;
        
        d.setCoordinates(x, y, z);
        domo.notify(d);
    }
    
    /**
     * Updates the data from the given dataObject, but 
     * does not store this object. Instead it searches for
     * the id and changes the objects data which is found.
     * If the id is not found, createNewObject is called with 
     * the dataObject.
     * 
     * @param dataObject the dataObject which should contain
     * 		all data.
     */
    public void updataData(DataObjectInterface dataObject){
        
        long id = dataObject.getID();
        
        if(!data.containsKey(id))
        	createNewObject(dataObject);
        else{
	        
	        int x = dataObject.getX();
	        int y = dataObject.getY();
	        int z = dataObject.getZ();
	        double rotation = dataObject.getRotation();
	        double scale = dataObject.getScale();
	        String name = dataObject.getName();
	        
	        DataObject d = data.get(id);
	        
	        if(d.getX() != x)
	        	 dc.em.setX(x, d.getWidth());
	        if(d.getX() != y)
	       	 	dc.em.setY(y, d.getHeight());
	        
	        d.setCoordinates(x, y, z);
	        d.setRotation(rotation);
	        d.setScale(scale);
	        d.setName(name);
	
	        domo.notify(d);
        }
    }
    
    @Override
    public DataObjectInterface getObject(long id){
        return data.get(id);
    }

    @Override
    public int getZ(long id) {
        DataObject object = data.get(id);
        
        if(object == null)
            return -1;
        
        return object.getZ();
    }

    /**
     * Returns an empty data object.
     * 
     * @return an empty data object.
     */
    public DataObjectInterface getEmptyDataObject() {
        return new DataObject();
    }
    
    /**
     * Registers an observer for the data manager.
     * Note: There can only be one observer registered at a time.
     * 
     * @param domo the observer instance.
     */
    public void registerObserver(DataObjectObserverInterface domo) {
    	this.domo = domo;
    }

}
