package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.math.Vector3f;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellComponent;
import org.jdesktop.wonderland.client.cell.ComponentChangeListener;
import org.jdesktop.wonderland.client.cell.MovableComponent;
import org.jdesktop.wonderland.client.cell.view.AvatarCell;
import org.jdesktop.wonderland.client.cell.view.ViewCell;
import org.jdesktop.wonderland.common.cell.messages.CellServerComponentMessage;
import org.jdesktop.wonderland.common.messages.ErrorMessage;
import org.jdesktop.wonderland.common.messages.ResponseMessage;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentRepositoryException;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IAdapterObserver;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.components.IDCellComponent;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.components.ImageCellComponent;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.components.CellChangeListener;
import org.jdesktop.wonderland.modules.oweditor.common.IDCellComponentServerState;
import org.jdesktop.wonderland.modules.security.client.SecurityQueryComponent;

/**
 * This class is used for updating the data package, when
 * there are changes made on the server. 
 * 
 * @author Patrick
 *
 */
public class ServerEventManager {
    
    private WonderlandAdapterController ac = null;
    private ArrayList<IAdapterObserver> observers = null;
    
    private ComponentChangeListener componentListener = null;
    private CellChangeListener imageListener = null;
    
    private ImageMonitor imgMonitor = null;
    
    private static final Logger LOGGER =
            Logger.getLogger(GUIEventManager.class.getName());
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    
    
    /**
     * Creates a new instance.
     * 
     * @param ac: the adapter controller instance.
     */
    public ServerEventManager(WonderlandAdapterController ac){
        this.ac = ac;
        imgMonitor = new ImageMonitor();
        
        observers = new ArrayList<IAdapterObserver>();
        
        componentListener = new ComponentChangeListener() {
            public void componentChanged(Cell cell, 
                    ComponentChangeListener.ChangeType type, 
                    CellComponent component) {     
                if (type == ComponentChangeListener.ChangeType.ADDED && 
                        component instanceof MovableComponent) {  
                    movableComponentCreated(cell);  
                }
                else if(type == ComponentChangeListener.ChangeType.ADDED && 
                        component instanceof ImageCellComponent){
                    //LOGGER.warning("IMAGE COMPONTENT CREATED");
                    imageComponentCreated(cell);
                }else if(type == ComponentChangeListener.ChangeType.ADDED &&
                        component instanceof SecurityQueryComponent){
                    securtyComponentCreated(cell);
                }else if(type == ComponentChangeListener.ChangeType.REMOVED &&
                        component instanceof SecurityQueryComponent){
                    securtyComponentRemoved(cell);
                }
            }
        };
        
        imageListener = new ChangeListener(this);
    }
    
    /**
     * This method should be called, when an object has been transformed
     * on the server. It gets a new data object from the data package
     * and fills it with the data it receives. The data object created 
     * here will be discarded afterwards.
     * 
     * @param cell the cell.
     */
    public void transformEvent(Cell cell){
        
        Vector3fInfo vector = CellInfoReader.getCoordinates(cell);
        Vector3f rotation = CellInfoReader.getRotation(cell); 
        float scale = CellInfoReader.getScale(cell);
        
        float x =  vector.x;
        float y =  vector.y;
        float z =  vector.z;
        
        long id = CellInfoReader.getID(cell);
        
        id = ac.bm.getOriginalID(id);
        
        for(IAdapterObserver observer : observers){
            observer.notifyScaling(id,(double) scale); 
            observer.notifyTranslation(id , x, y, z); 
            observer.notifyRotation(id, rotation.y, rotation.x, rotation.z);
        }
    }

    /**
     * Sets a dataUpdateInterface instance in order to transmit changes
     * and new objects to the data package.
     * 
     * @param observer a adapter observer instance.
     */
    public void registerDataInterface(IAdapterObserver observer) {
        observers.add(observer);
    }

    /**
     * Forwards the serverlist to the data package.
     * 
     * @param serverList A string arry containing the server.
     */
    public void setServerList(String[] serverList) {
        for(IAdapterObserver observer : observers)
            observer.setServerList(serverList);
    }
    
    /**
     * Is called, when a cell is removed on the server. Forwards the 
     * removal event to data.
     * 
     * @param cell The cell to remove.
     */
    public void removalEvent(Cell cell){
        //create backup of the cell.
        ac.bm.addCell(cell);
        
        long id = CellInfoReader.getID(cell);
        id = ac.bm.getOriginalID(id);
        
        ac.bm.setActive(id, false);
        imgMonitor.removeID(id);
        
        for(IAdapterObserver observer : observers)
            observer.notifyRemoval(id);
    }
    
    /**
     * Gets a new data object from the data package and fills it with
     * data. The difference to the serverChangeEvent is that the 
     * data object created here will be stored in the data manager.
     * 
     * @param cell the cell to create
     */
    public void creationEvent(Cell cell){
        
        cell.addTransformChangeListener(ac.tl);
        cell.addComponentChangeListener(componentListener);
        
        String name = cell.getName();
        long id = CellInfoReader.getID(cell);
        
        checkMovableComponent(cell);
        
        lateImage(cell);
        
        /*
        * does transformation (used when copying cells)
        * do late transform before reading cell data in order to get
        * the changes immediately.
        */
        ac.ltm.lateTransform(ac.sc, id);  
        
        ImageClass img = getImage(cell);
        
        
        id = checkID(cell);
        Vector3fInfo coordinates = CellInfoReader.getCoordinates(cell);
        Vector3f rotation = CellInfoReader.getRotation(cell);
        float scale = CellInfoReader.getScale(cell);
        
        for(IAdapterObserver observer : observers){
            IDataObject object= observer.createEmptyObject();

            float x = coordinates.x;
            float y = coordinates.y;
            float z = coordinates.z;

            float height = coordinates.height;
            float width = coordinates.width;

            object.setID(id);
            object.setCoordinates(x, y, z);
            object.setRotationX(rotation.y);
            object.setRotationY(rotation.x);
            object.setRotationZ(rotation.z);
            object.setScale(scale);
            object.setWidth(width);
            object.setHeight(height);
            object.setName(name);
            if(img != null)
                object.setImage(img.img, img.imgName, img.imgDir);
            else
                object.setImage(null, "", "");

            if(cell instanceof AvatarCell){
                name = ((ViewCell) cell).getIdentity().getUsername();
                object.setType(IDataObject.AVATAR);
                object.setWidth(AdapterSettings.avatarSizeX);
                object.setHeight(AdapterSettings.avatarSizeY);
            }else if(CellInfoReader.isSphere(cell))
                object.setType(IDataObject.CIRCLE);
            
            object.setName(name);
            observer.notifyObjectCreation(object);
        } 
    }
    

    /**
    * Adds a new movable component to the cell, if it was not created yet. 
    * 
    * @param cell The cell.
    */
    private void checkMovableComponent(Cell cell){
        
        MovableComponent movableComponent = cell.getComponent(MovableComponent.class);
        if (movableComponent == null) {
            String className = "org.jdesktop.wonderland.server.cell." +
                    "MovableComponentMO";
            CellServerComponentMessage cscm = 
                    CellServerComponentMessage.newAddMessage(
                    cell.getCellID(), className);
            ResponseMessage response = cell.sendCellMessageAndWait(cscm);
            if (response instanceof ErrorMessage) {
                LOGGER.log(Level.WARNING, "ERROR Movable component creation", 
                        response);
            }
        }  
    }
    
    /**
     * Tries to add an image component to the cell.
     * 
     * @param cell The cell. 
     */
    private void lateImage(Cell cell){
        long id = CellInfoReader.getID(cell);
        
        if(ac.ltm.containsImage(id)){
            try {
                ac.ltm.invokeLateImage(ac.sc, id, ac.fm.getUserDir());
            } catch (ContentRepositoryException ex) {
                LOGGER.warning("Chaning image after creation failed");
            }
        }
    }
    
    /**
     * Gets the image from a cell.
     * 
     * @param cell The cell.
     * @return An imageclass containing the image, or null if there is no
     * image component.
     */
    private ImageClass getImage(Cell cell){
        long id = CellInfoReader.getID(cell);
        ImageCellComponent imageComponent = cell.getComponent(ImageCellComponent.class);
            
        if(imageComponent != null){
            imageComponent.registerChangeListener(imageListener);

            String imgName = imageComponent.getImage();
            String imgDir = imageComponent.getDir();
            ImageClass imgClass = new ImageClass();
            imgClass.img = getImage(id, imgName, imgDir);
            imgClass.imgName = imgName;
            imgClass.imgDir = imgDir;
            
            return imgClass;
        }else{
            try {
                ac.sc.changeImage(id, "", "");
                
            } catch (ServerCommException ex) {
                LOGGER.warning( "Changing image failed");
            }
            return null;
        }
    }
    
    /**
     * Checks the id. Gets the original id, if it was created by undo/redo. 
     * 
     * @param cell The cell.
     * @return The editor id.
     */
    private long checkID(Cell cell){
        IDCellComponent idComponent = cell.getComponent(IDCellComponent.class);
        long id = CellInfoReader.getID(cell);
        
        if(idComponent != null){
            long oldid = idComponent.getID();
            
            //there is currently no cell with the original id and the 
            //original id is not -1, so it has to be a recreation of the
            //original cell.
            if(!ac.bm.isActive(oldid) && oldid != -1){
                id = oldid;

                ac.bm.addOriginalCell(id, cell);
            //a cell using the original id already exists.    
            }else if (ac.bm.isActive(oldid)){
                try {
                    //if it is on white list, the cell with the name
                    //already exists, therefore the original id was done
                    //with copy and can be deleted.
                    if(ac.bm.isOnWhiteList(id))
                        ac.sc.deleteComponent(id, IDCellComponentServerState.class);
                    //the original id was not on the whitelist, therefore it 
                    //is the same object which already exists, therefore it
                    //can be deleted.
                    else{
                        ac.sc.remove(id);
                        return id;
                    }
                } catch (ServerCommException ex) {
                    LOGGER.log(Level.WARNING,"Could not delete cell with the same "
                    + "original id, which is already used. Current ID:"
                    + id + ", original id "+oldid, ex);
                }
            //something else, like original id == -1, therefore the id
            //component can be deleted.
            }else{
                try {
                    ac.sc.deleteComponent(id, IDCellComponentServerState.class);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "Deleting cell component failed", ex);
                }
            }
        }
        return id;
    }
    
    
    /**
     * Is called through a cell compoment listener. This is needed,
     * when the cell needs transformation, but the movable component
     * was not created yet.
     * 
     * @param cell The cell to transform.
     */
    private void movableComponentCreated(Cell cell){
        long id = CellInfoReader.getID(cell);
        //id = ac.bm.getOriginalID(id);
        
        ac.ltm.lateTransform(ac.sc, id);
    }
    
    /**
     * Is called when the image component is created.
     * 
     * @param cell The cell.
     */
    private void imageComponentCreated(Cell cell){      
        ImageCellComponent imageComponent = 
                cell.getComponent(ImageCellComponent.class);
        
        if(imageComponent == null){
            return;
        }
        
        imageComponent.registerChangeListener(imageListener);      
    }
    
    /**
     * Is called, when a new security component is created.
     * 
     * @param cell The cell where the component was created.
     */
    private void securtyComponentCreated(Cell cell){
        long id = CellInfoReader.getID(cell);
        id = ac.bm.getOriginalID(id);
        
        /*
         * This are the standard rights the security module
         * produces when created.
         */
        for(IAdapterObserver observer : observers){
            observer.notifyRightsChange(id, 
                        BUNDLE.getString("Everybody"), 
                        BUNDLE.getString("Everybody"), 
                        false, true, 
                        true, 
                        true, true, true,
                        true);
            observer.notifyRightsChange(id, 
                        BUNDLE.getString("User"), 
                        ac.sm.getUserName(), 
                        true, true, 
                        true, 
                        true, true, false,
                        false);
            
            observer.notifyRightComponentCreation(id);
        }
    }
    
    /**
     * Sets the security for a cell by notifying the observers from
     * the data package.
     * 
     * @param cell The cell.
     */
    public void setSecurity(Cell cell){
        long id = CellInfoReader.getID(cell);
        id = ac.bm.getOriginalID(id);
        LinkedHashMap<String, SecurityManager.Right> map = 
                ac.secm.getSecurity(cell);
        
        for(IAdapterObserver observer : observers){
            
            observer.clearRights(id);
            
            for (Map.Entry<String, SecurityManager.Right> entry : map.entrySet()) {
                SecurityManager.Right right = entry.getValue();
                
                observer.notifyRightsChange(id, 
                        right.type, right.name, 
                        right.owner, right.permitSubObjects, 
                        right.permitAbilityChange, 
                        right.permitMove, right.permitView, right.isEditable,
                        right.isEverybody);
            } 
        }
    }
    
    /**
     * Is called, when a securtiy component is removed.
     * 
     * @param cell The cell.
     */
    private void securtyComponentRemoved(Cell cell){
        long id = CellInfoReader.getID(cell);
        id = ac.bm.getOriginalID(id);
        
        for(IAdapterObserver observer : observers){
            observer.notifyRightComponentRemoval(id);
        }
    }
    
    /**
     * Is called, when an image is changed.
     * 
     * @param imgName The new name of the image.
     * @param imgDir The new user directory of the image.
     * @param cell  The cell where the image is changed.
     */
    public void imageChangedEvent(String imgName, String imgDir, Cell cell){
        long original_id = CellInfoReader.getID(cell);
        long id = ac.bm.getOriginalID(original_id);
        
        BufferedImage img = getImage(original_id, imgName, imgDir);
        
        for(IAdapterObserver observer : observers){
            observer.notifyImageChange(id, img, imgName, imgDir);
        }
    }
    
    /**
     * Returns an image for a specific id, name and directory. Also notifies
     * other images, whith the same image that a change was done.
     * 
     * @param id The cell id.
     * @param imgName The name of the image.
     * @param imgDir The directory of the image.
     * @return The image.
     */
    private BufferedImage getImage(long id, String imgName, String imgDir){
        BufferedImage img = null;
        
        //get the image
        if(imgName != null && imgDir != null){
            try {
                img = ac.fm.downloadImage(imgName, imgDir);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Could not get image", ex);
                /*try {
                    ac.sc.deleteComponent(id, ImageCellComponent.class);
                } catch (ServerCommException ex1) {
                    LOGGER.log(Level.SEVERE, "Could not delete image component", ex1);
                }*/
            }
            
            //checks if other cells have the same image. This is to
            //register changes, if an image was overwritten.
            if(imgMonitor.idsExist(imgName, imgDir)){
                ArrayList<Long> ids = new ArrayList<Long>();
                ArrayList<Long> ids2 = imgMonitor.getIds(imgName, imgDir);
                if(ids2 == null)
                    return null;
                
                ids.addAll(ids2);

                for(long lid : ids){
                    for(IAdapterObserver observer : observers){
                        long lid_orig = ac.bm.getOriginalID(lid);
                        observer.notifyImageChange(lid_orig, img, imgName, imgDir);
                    }
                }
            }

            imgMonitor.putID(imgName, imgDir, id);
        }
        return img;
    }
    
    /**
     * Name change event.
     * 
     * @param cell The cell which name got changed.
     */
    public void nameChanged(Cell cell) {
        long id = CellInfoReader.getID(cell);
        id = ac.bm.getOriginalID(id);
            
        for(IAdapterObserver observer : observers){
           observer.notifyNameChange(id, cell.getName());
        }
    }
    
    /**
     * Sets the directory of the current user.
     * 
     * @param dir The user directory.
     */
    public void setUserDir(String dir){
        for(IAdapterObserver observer : observers){
            observer.setUserDir(dir);
        }
    }
    
    /**
     * Updates the user image library with a new image.
     * 
     * @param img The image.
     * @param imgName The image name.
     * @param dir  The image user directory.
     */
    public void updateUserLib(BufferedImage img, String imgName, String dir){
        for(IAdapterObserver observer : observers){
            observer.updateImgLib(img, imgName, dir);
        }
    }

    /**
     * Sets the user library.
     * 
     * @param userLib The user library.
     */
    void setImageLib(ArrayList<FileManager.ImageClass> userLib) {
        for(FileManager.ImageClass img : userLib){
            for(IAdapterObserver observer : observers){
                observer.updateImgLib(img.img, img.name, img.dir);
            }
        }
    }

    /**
     * Sets the user name of the current user.
     * 
     * @param username The name.
     */
    void setUserName(String username) {
        for(IAdapterObserver observer : observers){
            observer.setUserName(username);
        }
    }
    
    /**
     * A class used for images, containing an image and it's 
     * directory and name on the server.
     */
    class ImageClass{
        public BufferedImage img = null;
        public String imgName;
        public String imgDir;
    }

}
