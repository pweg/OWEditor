package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.AdapterObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectInterface;

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
    
    
    /**
     * Creates a new serverUpdate instance.
     * 
     * @param ac: the adapter controller instance.
     */
    public ServerUpdateAdapter(DummyAdapterController ac){
        this.ac = ac;
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
        double scale = sObject.scaleX;
        
        String name = sObject.name;
        
        DataObjectInterface object = dui.createEmptyObject();
        object.setID(id);
        object.setCoordinates(sObject.x, sObject.y, sObject.z);
        object.setRotationX(sObject.rotationX);
        object.setRotationY(sObject.rotationY);
        object.setRotationZ(sObject.rotationZ);
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

    public void serverCopyEvent(ServerObject object) {
        String name = object.name;
        BackupManager cm = ac.bom;
        if(cm.translationContainsKey(name)){
            BackupObject backup = cm.getObject(name);

            object.x = backup.getTranslationX();
            object.y = backup.getTranslationY();
            object.z = backup.getTranslationZ();

            object.scaleX = backup.getScaleX();
            object.scaleY = backup.getScaleY();
            object.scaleZ = backup.getScaleZ();

            object.rotationX = backup.getRotationX();
            object.rotationY = backup.getRotationY();
            object.rotationZ = backup.getRotationZ();
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
        
        dui.notifyRotation(id, so.rotationX, so.rotationY ,so.rotationZ);
    }

}
