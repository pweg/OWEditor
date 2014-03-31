package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IImage;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.ITransformedObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IDataObjectObserver;

/**
 * Stores, manages and creates data objects. 
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
     * Creates a new instance of the object data manager.
     * 
     * @param d a objectataController instance.
     */
    public DataObjectManager(DataController d){
        dc = d;
        observers = new ArrayList<IDataObjectObserver>();
        data = new LinkedHashMap<Long, DataObject>();
    }
    
    /**
     * Stores the data object given in the parameters.
     * 
     * @param dataObject the data object to store.
     */
    public void createNewObject(IDataObject dataObject){
        long id = dataObject.getID();
        
        
        if(dataObject instanceof DataObject){
            data.put(id, (DataObject) dataObject);
            
            ITransformedObject t = createTransformedObject((DataObject) dataObject);
            if(t == null)
                return;
            
            checkDimensionChange(t);
            
            IImage img = dataObject.getImgClass();
            dc.um.addImage(img.getImage(), img.getName(), img.getDir());
            
            for(IDataObjectObserver observer : observers)
                observer.notifyCreation(t);
        }
    }
    
    public void removeObject(long id){
        if(data.get(id) == null)
            return;
        
        data.remove(id);
        for(IDataObjectObserver observer : observers)
            observer.notifyRemoval(id);
    }
    
    /**
     * Updates the translation of an object.
     * 
     * @param id The id of the object.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param z The z coordinate.
     */
    public void updateTranslation(long id, float x, float y, float z){
        DataObject object = data.get(id);
        
        if(object == null || ct == null)
            return;
           
       float scale = (float)object.getScale();
       //float old_scale = (float) object.getOldScale();
       
       Point p = ct.transformCoordinatesInt(x, y, object.getWidthf()*scale, 
               object.getHeightf()*scale);
       /*Point p_old = ct.transformCoordinatesInt(object.getX(), object.getY(), 
               object.getWidthf()*old_scale, 
               object.getHeightf()*old_scale);*/
       
       
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

    /**
     * Returns a data object.
     * 
     * @param id The id of the data object.
     * @return The object, or null if it was not found.
     */
    public IDataObject getObject(long id){
        return data.get(id);
    }
    
    /**
     * Returns a transformed data object, which is mainly 
     * used by the graphic elements of the gui. The coordinates
     * will be transformed via the coordinate translator and the
     * object will be scaled. 
     * 
     * @param id The id of the object.
     * @return The transformed data object.
     */
    public ITransformedObject getTransformedObject(long id){
        return createTransformedObject(data.get(id));
    }
    
    /**
     * Creates the transformed data object. The coordinates
     * will be transformed via the coordinate translator and the
     * object will be scaled. 
     * 
     * @param object The object to transform.
     * @return The transformed object.
     */
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
        
        TransformedObject t = new TransformedObject(object.getID(), p.x, p.y, 
                object.getZf(), width, height,
                object.getScale(), ct.getRotation(object), object.getName(),
                object.getType(), object.getImgClass());
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
        
        double rotation = t.getRotation();
        
        //rotated objects have bigger bounds,
        //therefore they need to be checked separately.
        if(rotation != 0){
            int width = (int) Math.round(t.getWidth()*t.getScale());
            int height = (int) Math.round(t.getHeight()*t.getScale());
            
            Rectangle r = new Rectangle(t.getX(), t.getY(), width, height);
            AffineTransform trans = new AffineTransform();
            trans.rotate(Math.toRadians(rotation), r.getCenterX(), r.getCenterY());
            Shape s = trans.createTransformedShape(r);
            
            dc.em.setX(s.getBounds().x, s.getBounds().width);
            dc.em.setY(s.getBounds().y, s.getBounds().height);
        }else{
            dc.em.setX(t.getX(), (int) Math.round(t.getWidth()*t.getScale()));
            dc.em.setY(t.getY(), (int) Math.round(t.getHeight()*t.getScale()));
        }
    }

    /**
     * Returns the z coordinate of an object.
     * 
     * @param id The id of the object.
     * @return The z value.
     */
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
    public IDataObject getEmptyDataObject() {
        return new DataObject();
    }
    
    /**
     * Registers an observer for the data object manager.
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

    /**
     * Updates the rotation of an object.
     * 
     * @param id The id of the object.
     * @param rotationX The rotation x value.
     * @param rotationY The rotation y value.
     * @param rotationZ The rotation z value.
     */
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

    /**
     * Updates the scaling of an object.
     * 
     * @param id The id of the object.
     * @param scale The new scale.
     */
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

    public void updateName(Long id, String name) {
        DataObject d = data.get(id);
        if(d == null)
            return;
        
        d.setName(name);
        
        for(IDataObjectObserver observer : observers){
            observer.notifyNameChange(id, name);
        }
    }

    /**
     * Updates the image of an object.
     * 
     * @param id The id of the object.
     * @param img The new image. It may also be null, to delete it.
     * @param imgName The name of the image.
     * @param dir The users directory of the image.
     */
    public void updateImage(long id, BufferedImage img, String imgName, String dir) {
        DataObject d = data.get(id);
        if(d == null)
            return;

        dc.um.addImage(img, imgName, dir);
        
        d.setImage(img, imgName, dir);
        
        for(IDataObjectObserver observer : observers){
            observer.notifyImageChange(id, imgName, dir);
        }
    }

    public void rightComponentCreation(long id) {
        DataObject d = data.get(id);
        if(d == null)
            return;
        
        d.rightComponentCreated();
        
        for(IDataObjectObserver observer : observers){
            observer.notifyRightComponentCreated(id);
        }
        
    }

    public void rightComponentRemoval(long id) {

        DataObject d = data.get(id);
        if(d == null)
            return;
        
        d.rightComponentRemoved();
    }

    public void rightChange(long id, String oldType, String oldName,
            String type, String name, boolean owner,
            boolean addSubObjects, boolean changeAbilities, boolean move,
            boolean view, boolean isEditable, boolean isEverybody) {

        DataObject d = data.get(id);
        if(d == null)
            return;
        
        d.setRight(oldType, oldName, type,
                name, owner, addSubObjects, changeAbilities, move,
                view, isEditable, isEverybody);
        
    }

    public void removeRight(long id, String type, String name) {
        DataObject d = data.get(id);
        if(d == null)
            return;
        
        d.removeRight(type, name);
    }

}
