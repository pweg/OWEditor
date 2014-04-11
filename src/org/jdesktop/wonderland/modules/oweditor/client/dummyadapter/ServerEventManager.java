package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import java.awt.image.BufferedImage;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IAdapterObserver;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataObject;

/**
 * This class is used for updating the data package, when
 * there are changes made on the server. 
 * 
 * @author Patrick
 *
 */
public class ServerEventManager {
    
    private DummyAdapterController ac = null;
    private IAdapterObserver dui = null;
    
    
    /**
     * Creates a new serverUpdate instance.
     * 
     * @param ac: the adapter controller instance.
     */
    public ServerEventManager(DummyAdapterController ac){
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
    public void setDataUpdateInterface(IAdapterObserver dui) {
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
        double scale = sObject.scale;
        
        String name = sObject.name;
        
        IDataObject object = dui.createEmptyObject();
        object.setID(id);
        object.setCoordinates(sObject.x, sObject.y, sObject.z);
        object.setRotationX(sObject.rotationX);
        object.setRotationY(sObject.rotationY);
        object.setRotationZ(sObject.rotationZ);
        object.setScale(scale);
        object.setName(name);
        object.setImage(sObject.image, sObject.imgName, "nopath");
        
        if(sObject.isAvatar){
            object.setType(IDataObject.AVATAR);
            object.setWidth(AdapterSettings.AVATARSIZEX);
            object.setHeight(AdapterSettings.AVATARSIZEY);
        }else{
            object.setWidth(sObject.width);
            object.setHeight(sObject.height);
        }
        
        if(sObject.isCircle){
            object.setType(IDataObject.CIRCLE);
        }
        
        for(Rights rights : sObject.rights){
            
            object.setRight(rights.getType(), 
                    rights.getName(), rights.getOwner(), 
                    rights.getPermAddSubObjects(),
                    rights.getPermChangeAbilities(),
                    rights.getPermMove(),
                    rights.getPermView(),
                    rights.isEditable(),
                    rights.isEverybody());
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
        
        ServerObject so = ac.server.getObject(id);
        
        if(so == null)
            return;
        
        dui.notifyTranslation(id, so.x, so.y, so.z);
    }

    /**
     * Called after a copy event.
     * 
     * @param object The new object.
     */
    public void serverCopyEvent(ServerObject object) {
        String name = object.name;
        BackupManager cm = ac.bom;
        if(cm.translationContainsKey(name)){
            BackupObject backup = cm.getObject(name);

            object.x = backup.getTranslationX();
            object.y = backup.getTranslationY();
            object.z = backup.getTranslationZ();

            object.scale = backup.getScale();

            object.rotationX = backup.getRotationX();
            object.rotationY = backup.getRotationY();
            object.rotationZ = backup.getRotationZ();
        }
        createObject(object);
    }

    /**
     * Rotation event of an object.
     * 
     * @param id The id of the object.
     */
    public void serverRotationEvent(long id) {
        if(dui == null){
            System.out.println("DataInterface is not in the adapter");
            return;
        }
        
        ServerObject so = ac.server.getObject(id);
        
        if(so == null)
            return;
        
        dui.notifyRotation(id, so.rotationX, so.rotationY ,so.rotationZ);
    }

    /**
     * Name change event.
     * 
     * @param id Id of the object.
     */
    public void serverNameChange(Long id) {
        if(dui == null){
            System.out.println("DataInterface is not in the adapter");
            return;
        }
        
        ServerObject so = ac.server.getObject(id);
        
        if(so == null)
            return;
        
        dui.notifyNameChange(id, so.name);
    }

    /**
     * Scaling event.
     * 
     * @param id Object id.
     */
    public void serverScalingEvent(long id) {
        if(dui == null){
            System.out.println("DataInterface is not in the adapter");
            return;
        }
        
        ServerObject so = ac.server.getObject(id);
        
        if(so == null)
            return;
        
        dui.notifyScaling(id, so.scale);
    }

    /**
     * Sets the serverlist.
     * 
     * @param servers String array containing server names.
     */
    public void setServerList(String[] servers) {
        dui.setServerList(servers); 
    }
    
    /**
     * Sets up the user directory.
     */
    public void setUserDir(){
        dui.setUserDir(AdapterSettings.IMAGEDIR);
    }

    /**
     * Updates the image library of the user.
     * 
     * @param img A new image.
     * @param name 
     */
    public void updateImgLib(BufferedImage img, String name) {
        dui.updateImgLib(img, name, AdapterSettings.IMAGEDIR);
    }

    /**
     * Image change event.
     * 
     * @param id Object id.
     * @param img The new image.
     * @param dir The directory.
     * @param name  The name of the image.
     */
    public void serverImageChangeEvent(long id, BufferedImage img, String dir,
            String name) {
        dui.notifyImageChange(id, img, name, dir);
    }

    /**
     * Adds the right component.
     * 
     * @param id The objects id.
     */
    public void addRightsComponent(long id) {
        dui.notifyRightComponentCreation(id);
    }
    
    /**
     * Removes the right component.
     * 
     * @param id Object id.
     */
    public void removeRightsComponent(long id) {
        dui.notifyRightComponentRemoval(id);
    }
    
    /*
    not working correctly.
    */
    public void notifyRightsChange(long id, String oldType,
            String oldName, String type, String name, boolean owner,
            boolean addSubObjects, boolean changeAbilities, boolean move,
            boolean view){
        dui.notifyRightsChange(id, type, name, owner, addSubObjects,
                changeAbilities, move, view, false, false);
        
    }
    
    public void notifyRightsRemoval(long id, String type, String name) {
        dui.notifyRightsRemoval(id, type, name);
    }

}
