package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.AdapterObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.dummyadapter.AdapterSettings;

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
    private int startScale = AdapterSettings.initalScale;
    
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
    public void serverTranslationChangeEvent(long id){
        if(dui == null){
            System.out.println("DataInterface is not in the adapter");
            return;
        }
        
        ServerObject so = ac.ses.getObject(id);
        
        if(so == null)
            return;
        
        int x = (int)Math.round(so.x*startScale);
        int y = (int)Math.round(so.y*startScale);
        int z = (int)Math.round(so.z*startScale);
        
        dui.notifyTranslation(id, x, y, z);
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
        int x = (int) Math.round(sObject.x*startScale);
        int y = (int) Math.round(sObject.y*startScale);
        int z = (int) Math.round(sObject.z*startScale);            
        double rotation = sObject.rotation;
        double scale = sObject.scale;
        int width = (int) Math.round(sObject.width*startScale);
        int height = (int) Math.round(sObject.height*startScale);
        
        String name = sObject.name;
        
        DataObjectInterface object = dui.createEmptyObject();
        object.setID(id);
        object.setCoordinates(x, z, y);
        object.setRotation(rotation);
        object.setScale(scale);
        object.setName(name);
        object.setIsAvatar(sObject.isAvatar);
        
        if(sObject.isAvatar){
            object.setWidth(AdapterSettings.avatarSizeX);
            object.setHeight(AdapterSettings.avatarSizeY);
        }else{
            object.setWidth(width);
            object.setHeight(height);
        }
        
        if(name != "")
            object.setName(name);

        dui.notifyObjectCreation(object);
    }

}
