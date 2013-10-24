package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import java.awt.Point;
import java.util.LinkedHashMap;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;
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
    private CoordinateTranslatorInterface ct = null;
    
    private LinkedHashMap<Long, DataObject> data = null;
    
    /**
     * Creates a new instance of the data manager.
     * 
     * @param d a dataController instance.
     */
    DataObjectManager(DataController d){
        dc = d;
        data = new LinkedHashMap<Long, DataObject>();
    }
    
    /**
     * Stores the data object given in the parameters.
     * 
     * @param dataObject the data object to store.
     */
    public void createNewObject(DataObjectInterface dataObject){
        long id = dataObject.getID();
        
        
        if(dataObject instanceof DataObject){
            data.put(id, (DataObject) dataObject);
            
            float x = dataObject.getXf();
            float y = dataObject.getYf();
            float widthf = dataObject.getWidthf();
            float heightf = dataObject.getHeightf();
            
            Point p = ct.transformCoordinates(x, y, widthf, heightf);
            int width = ct.transformWidth(widthf);
            int height = ct.transformHeight(heightf);
            

            dc.em.setX(p.x, width);
            dc.em.setY(p.y, height);
            
            TranslatedObject t = new TranslatedObject(id, p.x, p.y, width, height,
                    dataObject.getScale(), dataObject.getRotation(), dataObject.getName(),
                    dataObject.getType());
            
            domo.notifyCreation(t);
        }
    }
    
    public void removeObject(long id){
        data.remove(id);
        domo.notifyRemoval(id);
    }
    
    public void updateTranslation(long id, float x, float y, float z){
        DataObject d = data.get(id);
        
        if(d == null)
            return;
        
       Point p = ct.transformCoordinates(x, y, d.getWidthf(), d.getHeightf());
        
       if(d.getX() != x)
           dc.em.setX(p.x, ct.transformWidth(d.getWidthf()));
       if(d.getX() != y)
           dc.em.setY(p.y, ct.transformHeight(d.getHeightf()));
            
        d.setCoordinates(x, y, z);
        
        domo.notifyTranslation(id, p.x, p.y);
    }
    
    /**
     * Updates the data from the given dataObject, but 
     * does not store this object. Instead it searches for
     * the id and changes the objects data which is found.
     * If the id is not found, createNewObject is called with 
     * the dataObject.
     * 
     * @param dataObject the dataObject which should contain
     *         all data.
     */
    public void updataData(DataObjectInterface dataObject){
        
        long id = dataObject.getID();
        
        if(!data.containsKey(id))
            createNewObject(dataObject);
        else{
            
            float x = dataObject.getXf();
            float y = dataObject.getYf();
            float z = dataObject.getZf();
            double rotation = dataObject.getRotation();
            double scale = dataObject.getScale();
            String name = dataObject.getName();
            
            DataObject d = data.get(id);

            Point p = ct.transformCoordinates(x, y, d.getWidthf(), d.getHeightf());
            
            if(d.getX() != x)
                dc.em.setX(p.x, ct.transformWidth(d.getWidthf()));
            if(d.getX() != y)
                dc.em.setY(p.y, ct.transformHeight(d.getHeightf()));
            
            d.setCoordinates(x, y, z);
            d.setRotation(rotation);
            d.setScale(scale);
            d.setName(name);
    
            domo.notifyChange(id, p.x, p.y, name);
        }
    }
    
    @Override
    public DataObjectInterface getObject(long id){
        return data.get(id);
    }

    @Override
    public float getZ(long id) {
        DataObject object = data.get(id);
        
        if(object == null)
            return -1;
        
        return object.getZf();
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
    
    public void registerCoordinateTranslator(CoordinateTranslatorInterface ct){
        this.ct = ct;
    }

    public void updateRotation(long id, float x, float y, float z,
            double rotation) {
        
        DataObject d = data.get(id);
        
        if(d == null)
            return;
        
       Point p = ct.transformCoordinates(x, y, d.getWidthf(), d.getHeightf());
        
       if(d.getX() != x)
           dc.em.setX(p.x, ct.transformWidth(d.getWidthf()));
       if(d.getX() != y)
           dc.em.setY(p.y, ct.transformHeight(d.getHeightf()));
            
        d.setCoordinates(x, y, z);
        d.setRotation(rotation);
        
        domo.notifyRotation(id, p.x, p.y, rotation);
    }

}
