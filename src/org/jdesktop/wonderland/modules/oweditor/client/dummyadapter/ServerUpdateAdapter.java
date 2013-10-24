package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import java.util.HashMap;
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
    
    private DummyAdapterController ac = null;
    private AdapterObserverInterface dui = null;
    
    private HashMap<String, Vector3D> copyTranslation = null;
    
    /**
     * Creates a new serverUpdate instance.
     * 
     * @param ac: the adapter controller instance.
     */
    public ServerUpdateAdapter(DummyAdapterController ac){
        this.ac = ac;
        
        copyTranslation = new HashMap<String, Vector3D>();
    }
    
    /**
     * This method should be called, when an object was changed
     * on the server. It gets a new data object from the data package
     * and fills it with the data it receives. The data object created 
     * here will be discarded afterwards.
     * 
     * @param id the object id.
     */
    public void serverTranslationChangeEvent(long id){
        if(dui == null){
            System.out.println("DataInterface is not in the adapter");
            return;
        }
        
        ServerObject so = ac.ses.getObject(id);
        
        if(so == null)
            return;
        
        dui.notifyTranslation(id, so.x, so.y, so.z);
    }
    
    /**
     * This is called, when an object on the server was removed.
     * 
     * @param id the id of the removed object.
     */
    public void serverRemovalEvent(long id){
        dui.notifyRemoval(id);
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
     * @param sObject the instance of an server object
     */    
    public void createObject(ServerObject sObject){
        
        long id = sObject.id;            
        double rotation = sObject.rotation;
        double scale = sObject.scale;
        
        String name = sObject.name;
        
        DataObjectInterface object = dui.createEmptyObject();
        object.setID(id);
        object.setCoordinates(sObject.x, sObject.y, sObject.z);
        object.setRotation(rotation);
        object.setScale(scale);
        object.setName(name);
        if(sObject.isAvatar){
            object.setType(DataObjectInterface.AVATAR);
            object.setWidth(AdapterSettings.avatarSizeX);
            object.setHeight(AdapterSettings.avatarSizeY);
        }else{
            object.setWidth(sObject.width);
            object.setHeight(sObject.height);
        }
        
        object.setName(name);

        dui.notifyObjectCreation(object);
    }

    public void copyTranslation(String name, float x, float y , float z) {
        Vector3D v = new Vector3D(x,y,z);
        
        copyTranslation.put(name, v);
        
    }

    public void serverCopyEvent(ServerObject object) {
        String name = object.name;
        if(copyTranslation.containsKey(name)){
            Vector3D v = copyTranslation.get(name);
            
            object.x = v.x;
            object.y = v.y;
            object.z = v.z;
        }
        createObject(object);
    }

    public void serverRotationEvent(long id) {
        if(dui == null){
            System.out.println("DataInterface is not in the adapter");
            return;
        }
        
        ServerObject so = ac.ses.getObject(id);
        
        if(so == null)
            return;
        
        dui.notifyRotation(id, so.x, so.y, so.z, so.rotation);
    }

}
