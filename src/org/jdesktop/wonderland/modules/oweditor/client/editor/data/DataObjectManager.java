package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.ITransformedObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IDataObjectObserver;

/**
 * Stores, manages anobject creates objectata objects. 
 * 
 * @author Patrick
 *
 */
public class DataObjectManager {
    
    private static final Logger LOGGER =
            Logger.getLogger(DataObjectManager.class.getName());
        
    private DataController dc = null;
    private ArrayList<IDataObjectObserver> observers = null;
    private CoordinateTranslatorInterface ct = null;
    
    private LinkedHashMap<Long, DataObject> data = null;
    
    /**
     * Creates a new instance of the objectata manager.
     * 
     * @param d a objectataController instance.
     */
    public DataObjectManager(DataController d){
        dc = d;
        observers = new ArrayList<IDataObjectObserver>();
        data = new LinkedHashMap<Long, DataObject>();
    }
    
    /**
     * Stores the objectata object given in the parameters.
     * 
     * @param dataObject the objectata object to store.
     */
    public void createNewObject(IDataObject dataObject){
        long id = dataObject.getID();
        
        
        if(dataObject instanceof DataObject){
            data.put(id, (DataObject) dataObject);
            
            ITransformedObject t = createTransformedObject((DataObject) dataObject);
            if(t == null)
                return;
            
            checkDimensionChange(t);
            
            for(IDataObjectObserver observer : observers)
                observer.notifyCreation(t);
        }
    }
    
    public void removeObject(long id){
        data.remove(id);
        for(IDataObjectObserver observer : observers)
            observer.notifyRemoval(id);
    }
    
    public void updateTranslation(long id, float x, float y, float z){
        DataObject object = data.get(id);
        
        if(object == null || ct == null)
            return;
           
       float scale = (float)object.getScale();
       float old_scale = (float) object.getOldScale();
       
       Point p = ct.transformCoordinatesInt(x, y, object.getWidthf()*scale, 
               object.getHeightf()*scale);
       Point p_old = ct.transformCoordinatesInt(object.getX(), object.getY(), 
               object.getWidthf()*old_scale, 
               object.getHeightf()*old_scale);
       
       
       boolean new_coords = x != object.getX() || y != object.getY();
       
       /*
       * Looks for dimension change of the world.
       *
       if(){
           //dc.em.setX(p.x, ct.transformWidth(object.getWidthf()));
           new_coords = true;
       }
       if(p.y != p_old.y){
           //dc.em.setY(p.y, ct.transformHeight(object.getHeightf()));
           new_coords = true;
       }*/
          
       if(!new_coords){
           return;
       }
       
       object.setCoordinates(x, y, z);
       checkDimensionChange(createTransformedObject(object));
       
       for(IDataObjectObserver observer : observers)
            observer.notifyTranslation(id, p.x, p.y);
    }

    public IDataObject getObject(long id){
        return data.get(id);
    }
    
    public ITransformedObject getTransformedObject(long id){
        return createTransformedObject(data.get(id));
    }
    
    private ITransformedObject createTransformedObject(DataObject object){
        float x = object.getXf();
        float y = object.getYf();
        float widthf = object.getWidthf();
        float heightf = object.getHeightf();
        float scale = (float)object.getScale();
        
        if(ct == null)
            return null;
        
        Point p = ct.transformCoordinatesInt(x, y, widthf* scale, heightf* scale);
        int width = ct.transformWidth(widthf);
        int height = ct.transformHeight(heightf);
        
        TransformedObject t = new TransformedObject(object.getID(), p.x, p.y, width, height,
                object.getScale(), ct.getRotation(object), object.getName(),
                object.getType(), object.getImage());
        return t;
    }
    
    /**
     * Forwards coordinates and size of an transformed object,
     * to the environment manager, which has the task of calculating
     * the world bounds.
     * 
     * @param t  A transformed object instance.
     */
    private void checkDimensionChange(ITransformedObject t){
        if(t == null)
            return;
        
        dc.em.setX(t.getX(), (int) Math.round(t.getWidth()*t.getScale()));
        dc.em.setY(t.getY(), (int) Math.round(t.getHeight()*t.getScale()));
    }

    public float getZ(long id) {
        DataObject object = data.get(id);
        
        if(object == null)
            return -1;
        
        return object.getZf();
    }

    /**
     * Returns an empty objectata object.
     * 
     * @return an empty objectata object.
     */
    public IDataObject getEmptyDataObject() {
        return new DataObject();
    }
    
    /**
     * Registers an observer for the objectata manager.
     * Note: There can only be one observer registereobject at a time.
     * 
     * @param observer the observer instance.
     */
    public void registerObserver(IDataObjectObserver observer) {
        observers.add(observer);
    }
    
    /**
     * Registers a coordinate translator instance.
     * 
     * @param ct The translator.
     */
    public void registerCoordinateTranslator(CoordinateTranslatorInterface ct){
        this.ct = ct;
    }

    public void updateRotation(long id, double rotationX, 
            double rotationY, double rotationZ) {
        
        DataObject d = data.get(id);
        
        if(d == null || ct == null) 
            return;
            
        double rotation = ct.getRotation(d);
        d.setRotationX(rotationX);
        d.setRotationY(rotationY);
        d.setRotationZ(rotationZ);
        double rotation_new = ct.getRotation(d);
       
        if(rotation != rotation_new){
            for(IDataObjectObserver observer : observers)
                observer.notifyRotation(id, rotation_new);
            checkDimensionChange(createTransformedObject(d));
        }
    }

    public void updateScale(long id, double scale) {
        DataObject d = data.get(id);
        
        if(d == null || ct == null)
            return;
        
        scale = ct.getScale(scale);
        double old_scale = ct.getScale(d.getScale());
        
        if(scale != old_scale){
            d.setScale(scale);
            for(IDataObjectObserver observer : observers)
                observer.notifyScaling(id, scale);
            
            checkDimensionChange(createTransformedObject(d));
        }
    }

    /**
     * Transforms gui coordinates back into virtual world coordinates.
     * This should be used for stand alone coordinates, not for 
     * shape coordinates.
     * 
     * @param coordinates The gui coordinates.
     * @return The world coordinates.
     */
    public Point2D.Double transformCoordsBack(Point coordinates) {
        return transformCoordsBack(coordinates, 0,0);
    }

    /**
     * Transforms gui coordinates back into virtual world coordinates.
     * This should be used for shape coordinates.
     * 
     * @param coordinates The gui coordinates.
     * @param width The gui width of an object.
     * @param height The gui height of an object.
     * @return The world coordinates.
     */
    public Point2D.Double transformCoordsBack(Point coordinates, int width, int height) {
        if(ct == null)
            return null;
        
        Point2D.Double point = ct.transformCoordinatesBack((float)coordinates.x,
                (float)coordinates.y, (float)width, (float)height);
        return point;
    }

}
