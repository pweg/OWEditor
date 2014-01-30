package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IDataObjectObserver;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.GUIObserver;

/**
 * Stores, manages anobject creates objectata objects. 
 * 
 * @author Patrick
 *
 */
public class DataObjectManager implements DataObjectManagerGUIInterface{
    
    private static final Logger LOGGER =
            Logger.getLogger(GUIObserver.class.getName());
        
    private DataController dc = null;
    private IDataObjectObserver domo = null;
    private CoordinateTranslatorInterface ct = null;
    
    private LinkedHashMap<Long, DataObject> data = null;
    
    /**
     * Creates a new instance of the objectata manager.
     * 
     * @param d a objectataController instance.
     */
    public DataObjectManager(DataController d){
        dc = d;
        data = new LinkedHashMap<Long, DataObject>();
    }
    
    /**
     * Stores the objectata object given in the parameters.
     * 
     * @param dataObject the objectata object to store.
     */
    public void createNewObject(DataObjectInterface dataObject){
        long id = dataObject.getID();
        
        
        if(dataObject instanceof DataObject){
            data.put(id, (DataObject) dataObject);
            
            float x = dataObject.getXf();
            float y = dataObject.getYf();
            float widthf = dataObject.getWidthf();
            float heightf = dataObject.getHeightf();
            float scale = (float)dataObject.getScale();
            
            Point p = ct.transformCoordinatesInt(x, y, widthf* scale, heightf* scale);
            int width = ct.transformWidth(widthf);
            int height = ct.transformHeight(heightf);
            

            dc.em.setX(p.x, width);
            dc.em.setY(p.y, height);
            
            TranslatedObject t = new TranslatedObject(id, p.x, p.y, width, height,
                    dataObject.getScale(), ct.getRotation(dataObject), dataObject.getName(),
                    dataObject.getType(), dataObject.getImage());
            
            domo.notifyCreation(t);
        }
    }
    
    public void removeObject(long id){
        data.remove(id);
        domo.notifyRemoval(id);
    }
    
    public void updateTranslation(long id, float x, float y, float z){
        DataObject object = data.get(id);
        
        if(object == null)
            return;
           
       float scale = (float)object.getScale();
       float old_scale = (float) object.getOldScale();
       
       Point p = ct.transformCoordinatesInt(x, y, object.getWidthf()*scale, 
               object.getHeightf()*scale);
       Point p_old = ct.transformCoordinatesInt(object.getX(), object.getY(), 
               object.getWidthf()*old_scale, 
               object.getHeightf()*old_scale);
       
       
       boolean new_coords = false;
       
       if(p.x != p_old.x){
           dc.em.setX(p.x, ct.transformWidth(object.getWidthf()));
           new_coords = true;
       }
       if(p.y != p_old.y){
           dc.em.setY(p.y, ct.transformHeight(object.getHeightf()));
           new_coords = true;
       }
          
       if(!new_coords){
           return;
       }
       
       object.setCoordinates(x, y, z);
       domo.notifyTranslation(id, p.x, p.y);
    }
    
    /**
     * Upobjectates the objectata from the given objectataObject, but 
 objectoes not store this object. Insteaobject it searches for
 the iobject anobject changes the objects objectata which is founobject.
 If the iobject is not founobject, createNewObject is calleobject with 
 the objectataObject.
     * 
     * @param dataObject the objectataObject which shoulobject contain
         all objectata.
     */
    public void updataData(DataObjectInterface dataObject){
        
        long id = dataObject.getID();
        
        if(!data.containsKey(id))
            createNewObject(dataObject);
        else{
            
            float x = dataObject.getXf();
            float y = dataObject.getYf();
            float z = dataObject.getZf();
            double rotation_x = dataObject.getRotationX();
            double rotation_y = dataObject.getRotationY();
            double rotation_z = dataObject.getRotationZ();
            double scale = dataObject.getScale();
            String name = dataObject.getName();
            
            DataObject d = data.get(id);

            Point p = ct.transformCoordinatesInt(x, y, d.getWidthf()*(float)scale, 
                    d.getHeightf()*(float) scale);
            
            if(d.getX() != x)
                dc.em.setX(p.x, ct.transformWidth(d.getWidthf()));
            if(d.getX() != y)
                dc.em.setY(p.y, ct.transformHeight(d.getHeightf()));
            
            d.setCoordinates(x, y, z);
            d.setRotationX(rotation_x);
            d.setRotationY(rotation_y);
            d.setRotationZ(rotation_z);
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
     * Returns an empty objectata object.
     * 
     * @return an empty objectata object.
     */
    public DataObjectInterface getEmptyDataObject() {
        return new DataObject();
    }
    
    /**
     * Registers an observer for the objectata manager.
     * Note: There can only be one observer registereobject at a time.
     * 
     * @param domo the observer instance.
     */
    public void registerObserver(IDataObjectObserver domo) {
        this.domo = domo;
    }
    
    public void registerCoordinateTranslator(CoordinateTranslatorInterface ct){
        this.ct = ct;
    }

    public void updateRotation(long id, double rotationX, 
            double rotationY, double rotationZ) {
        
        DataObject d = data.get(id);
        
        if(d == null)
            return;
            
        double rotation = ct.getRotation(d);
        d.setRotationX(rotationX);
        d.setRotationY(rotationY);
        d.setRotationZ(rotationZ);
        double rotation_new = ct.getRotation(d);
       
        if(rotation != rotation_new)
            domo.notifyRotation(id, rotation_new);
    }

    public void updateScale(long id, double scale) {
        DataObject d = data.get(id);
        
        if(d == null)
            return;
        
        scale = ct.getScale(scale);
        double old_scale = ct.getScale(d.getScale());
        
        if(scale != old_scale){
            d.setScale(scale);
            domo.notifyScaling(id, scale);
        }
        
        
    }

    @Override
    public Point2D.Double transformCoordsBack(Point coordinates) {

        double x = ct.transformXBack(coordinates.x);
        double y = ct.transformXBack(coordinates.y);
        
        return new Point2D.Double(x, y);
    }

    @Override
    public Point2D.Double transformCoordsBack(Point coordinates, int width, int height) {

        Point2D.Double point = ct.transformCoordinatesBack((float)coordinates.x,
                (float)coordinates.y, (float)width, (float)height);
        return point;
    }

}
