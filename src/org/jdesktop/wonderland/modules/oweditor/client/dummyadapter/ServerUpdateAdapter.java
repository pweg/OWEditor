package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.AdapterObserverInterface;

/**
 * This class is used for updating the data package, when
 * there are changes made on the server. 
 * 
 * @author Patrick
 *
 */
public class ServerUpdateAdapter {
    
    private AdapterController ac = null;
    private AdapterObserverInterface dui = null;
    
    /**
     * Creates a new serverUpdate instance.
     * 
     * @param ac: the adapter controller instance.
     */
    public ServerUpdateAdapter(AdapterController ac){
        this.ac = ac;
    }
    
    /**
     * This method should be called, when an object was changed
     * on the server. It gets a new data object from the data package
     * and fills it with the data it receives. The data object created 
     * here will be discarded afterwards.
     * 
     * @param id the object id.
     */
    public void serverChangeEvent(long id){
        if(dui == null){
            System.out.println("DataInterface is not in the adapter");
            return;
        }
        
        ServerObject so = ac.ses.getObject(id);
        
        if(so == null)
        	return;
        
        int x = so.x;
        int y = so.y;
        int z = so.z;
        double rotation = so.rotation;
        double scale = so.scale;
        String name = so.name;
        
        DataObjectInterface object = dui.createEmptyObject();
        object.setID(id);
        object.setCoordinates(x, y, z);
        object.setRotation(rotation);
        object.setScale(scale);
        object.setName(name);
        
        dui.notifyObjectChange(object);
        
    }

    /**
     * Sets a dataUpdateInterface instance in order to transmit changes
     * and new objects to the data package.
     * 
     * @param dui a dataUpdate instance.
     */
    public void setDataUpdateInterface(AdapterObserverInterface dui) {
        this.dui = dui;
        
    }
    
    /**
     * Gets a new data object from the data package and fills it with
     * data. The difference to the serverChangeEvent is that the 
     * data object created here will be stored in the data manager.
     * 
     * @param id the object id.
     * @param x the x coordinate of the object.
     * @param y the y coordinate of the object.
     * @param z the z coordinate of the object.
     * @param rotation the rotation of the object.
     * @param scale the scale of the object.
     * @param width the width of the object.
     * @param height the height of the object.
     * @param name the name of the object.
     */
    public void createObject(long id, int x, int y, int z, 
            double rotation, double scale, int width, int height,
            String name){
        
        DataObjectInterface object = dui.createEmptyObject();
        object.setID(id);
        object.setCoordinates(x, y, z);
        object.setRotation(rotation);
        object.setScale(scale);
        object.setWidth(width);
        object.setHeight(height);
        object.setName(name);
        
        if(name != "")
            object.setName(name);

        dui.notifyObjectCreation(object);
    }

}
