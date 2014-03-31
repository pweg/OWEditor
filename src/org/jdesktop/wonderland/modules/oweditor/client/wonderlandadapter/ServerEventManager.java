package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.math.Vector3f;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.Marshaller;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellComponent;
import org.jdesktop.wonderland.client.cell.ComponentChangeListener;
import org.jdesktop.wonderland.client.cell.MovableComponent;
import org.jdesktop.wonderland.client.cell.view.AvatarCell;
import org.jdesktop.wonderland.common.cell.messages.CellServerComponentMessage;
import org.jdesktop.wonderland.common.cell.state.CellServerState;
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
import org.jdesktop.wonderland.modules.security.common.ActionDTO;
import org.jdesktop.wonderland.modules.security.common.CellPermissions;
import org.jdesktop.wonderland.modules.security.common.Permission;
import org.jdesktop.wonderland.modules.security.common.Principal;
import org.jdesktop.wonderland.modules.security.common.SecurityComponentServerState;

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
        
        imageListener = new ChangeListener();
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
        
        MovableComponent movableComponent = cell.getComponent(MovableComponent.class);
        if (movableComponent == null) {
            String className = "org.jdesktop.wonderland.server.cell." +
                    "MovableComponentMO";
            CellServerComponentMessage cscm = 
                    CellServerComponentMessage.newAddMessage(
                    cell.getCellID(), className);
            ResponseMessage response = cell.sendCellMessageAndWait(cscm);
            if (response instanceof ErrorMessage) {
                LOGGER.warning("ERROR Movable component creation"+response);
            }
        }  
        
        /*
        * do late transform before reading cell data in order to get
        * the changes imideately.
        */
        
        //adds an image component
        if(ac.ltm.containsImage(id)){
            try {
                ac.ltm.invokeLateImage(ac.sc, id, ac.fm.getUserDir());
            } catch (ContentRepositoryException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        
        //does transformation (used when copying cells)
        String oldName="";
        if(ac.ltm.containsCell(id, name)){
            //copied names
            if(ac.ltm.containsCell(name)){
                ac.bm.addNewCopyID(id, name);
            }
            ac.ltm.invokeLateTransform(ac.sc, id, name);
            try {
                String realName = ac.cnm.getRealName(name);
                if(!realName.equals(name)){
                    ac.sc.changeName(id, realName);
                    oldName = name;
                    name = realName;
                }
            } catch (ServerCommException ex) {
                LOGGER.log(Level.SEVERE, "Could not change name back.", ex);
            }
        }
        
        //gets the image
        BufferedImage img = null;
        ImageCellComponent imageComponent = cell.getComponent(ImageCellComponent.class);
        String imgName = null;
        String imgDir = null;
            
        if(imageComponent != null){
            imgName = imageComponent.getImage();
            imgDir = imageComponent.getDir();
            imageComponent.registerChangeListener(imageListener);
            img = getImage(id, imgName, imgDir);
        }else{
            try {
                ac.sc.changeImage(id, "", "");
                
            } catch (ServerCommException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            
        }
        
        //gets the original id, if it was created by undo/redo 
        IDCellComponent idComponent = cell.getComponent(IDCellComponent.class);
        
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
                    if(ac.bm.isOnWhiteList(oldName))
                        ac.sc.deleteComponent(id, IDCellComponentServerState.class);
                    //the original id was not on the whitelist, therefore it 
                    //is the same object which already exists, therefor it
                    //can be deleted.
                    else{
                        ac.sc.remove(id);
                        return;
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
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
        }
        
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
            object.setImage(img, imgName, imgDir);
            
            LinkedHashMap<String, Right> map = getSecurity(cell);
        
            for (Map.Entry<String, Right> entry : map.entrySet()) {
                Right right = entry.getValue();
                
                object.addRights(right.type, right.name, 
                        right.owner, right.permitSubObjects, 
                        right.permitAbilityChange, right.permitMove, 
                        right.permitView, right.isEditable,
                        right.isEverybody);
            }

            
            if(cell instanceof AvatarCell){
                object.setType(IDataObject.AVATAR);
                object.setWidth(AdapterSettings.avatarSizeX);
                object.setHeight(AdapterSettings.avatarSizeY);
            }else if(CellInfoReader.isSphere(cell))
                object.setType(IDataObject.CIRCLE);
            
            object.setName(name);
            observer.notifyObjectCreation(object);
        } 
        
        
        /*SecurityQueryComponent secComp =
                cell.getComponent(SecurityQueryComponent.class);
        if(secComp != null){
            for(IAdapterObserver observer : observers){
                observer.notifyRightComponentCreation(id);
            }
           
        }*/
        
    }
    
    /**
     * Is called through a cell compoment listener. This is needed,
     * when the cell needs transformation, but the movable component
     * was not created yet.
     * 
     * @param cell The cell to transform.
     */
    private void movableComponentCreated(Cell cell){
        
        String name = cell.getName();
        long id = CellInfoReader.getID(cell);
        //id = ac.bm.getOriginalID(id);
        
        ac.ltm.invokeLateTransform(ac.sc, id, name);
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
    
    private void securtyComponentCreated(Cell cell){
        long id = CellInfoReader.getID(cell);
        id = ac.bm.getOriginalID(id);
        
        for(IAdapterObserver observer : observers){
            observer.notifyRightComponentCreation(id);
        }
        LinkedHashMap<String, Right> map = getSecurity(cell);
        
        for (Map.Entry<String, Right> entry : map.entrySet()) {
            Right right = entry.getValue();
            
            for(IAdapterObserver observer : observers){
                
                observer.notifyRightsChange(id, 
                        null, null, right.name, right.type, 
                        right.owner, right.permitSubObjects, 
                        right.permitAbilityChange, 
                        right.permitMove, right.permitView, right.isEditable,
                        right.isEverybody);
            } 
        }
    }
    
    private LinkedHashMap<String, Right> getSecurity(Cell cell){
        
        SecurityQueryComponent component =
                cell.getComponent(SecurityQueryComponent.class);
        
        LinkedHashMap<String, Right> map = new LinkedHashMap<String, Right>();
        
        if(component == null)
            return map;
       
        CellServerState cellServerState = ac.sc.fetchCellServerState(cell);
        
        if(cellServerState == null)
            return map ;
        
        SecurityComponentServerState state =
                (SecurityComponentServerState) cellServerState.getComponentServerState(
                SecurityComponentServerState.class);
        
        if(state == null)
            return map;
        CellPermissions perms = state.getPermissions();
        
        for (Principal p : perms.getOwners()) {
             
            Right right = new Right();
            right.name = p.getId();
             
            if(p.getType() == Principal.Type.EVERYBODY){
                right.name = BUNDLE.getString("Everybody");
                right.type = BUNDLE.getString("Everybody");
                right.isEverybody = true;
            }else if(p.getType() == Principal.Type.GROUP){
                right.type = BUNDLE.getString("Group");
            }else if(p.getType() == Principal.Type.USER){
                right.type = BUNDLE.getString("User");
            }
             
            right.owner = true;
            right.permitAbilityChange = true;
            right.permitMove = true;
            right.permitSubObjects = true;
            right.permitView = true;
             
            map.put(p.getId(), right);
        }
        
        int i=0;
        
        for(Permission p : perms.getPermissions()) {
            String name = p.getPrincipal().getId();
             
            Right right = map.get(name);
             
            if(right == null){
                right = new Right();
                right.name = name;
                
                if(p.getPrincipal().getType() == Principal.Type.EVERYBODY){
                    right.name = BUNDLE.getString("Everybody");
                    right.type = BUNDLE.getString("Everybody");
                    right.isEverybody = true;
                }else if(p.getPrincipal().getType() == Principal.Type.GROUP){
                    right.type = BUNDLE.getString("Group");
                }else if(p.getPrincipal().getType() == Principal.Type.USER){
                    right.type = BUNDLE.getString("User");
                }
                map.put(name, right);  
            }
            
            String action = p.getAction().getAction().getName();
            
            boolean access = true;
            if(p.getAccess() == Permission.Access.DENY )
                access = false;
            
            if(action.equals("Move")){
                right.permitMove = access;
            }else if(action.equals("View")){
                right.permitView = access;
            }else if(action.equals("ChangeCellChildren")){
                right.permitSubObjects = access;
            }else if(action.equals("ChangeCellComponent")){
                right.permitAbilityChange = access;
            }
            i++;
         }
        
        return map;
    }
    
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
    private void imageChangedEvent(String imgName, String imgDir, Cell cell){
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
                ArrayList<Long> ids = imgMonitor.getIds(imgName, imgDir);

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
     * Inner class for the image change listener.
     */
    class ChangeListener extends Marshaller.Listener implements CellChangeListener{

        public void imageChanged(String img, String dir, Cell cell) {
            imageChangedEvent(img, dir, cell);
        }

        public void nameChanged(Cell cell) {
            long id = CellInfoReader.getID(cell);
            id = ac.bm.getOriginalID(id);
            
            for(IAdapterObserver observer : observers){
                observer.notifyNameChange(id, cell.getName());
            }
        }
    }
    
    class Right{
        protected String name = null;
        protected String type = null;
        protected boolean owner = false;
        protected boolean permitSubObjects = true;
        protected boolean permitAbilityChange = true;
        protected boolean permitMove = true;
        protected boolean permitView = true;
        protected boolean isEditable = true;
        protected boolean isEverybody = false;
    }
    

}
