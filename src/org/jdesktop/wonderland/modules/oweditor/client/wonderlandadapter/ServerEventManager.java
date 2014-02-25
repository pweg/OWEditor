package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.math.Vector3f;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.Marshaller;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellComponent;
import org.jdesktop.wonderland.client.cell.ComponentChangeListener;
import org.jdesktop.wonderland.client.cell.MovableComponent;
import org.jdesktop.wonderland.client.cell.view.AvatarCell;
import org.jdesktop.wonderland.common.cell.messages.CellServerComponentMessage;
import org.jdesktop.wonderland.common.messages.ErrorMessage;
import org.jdesktop.wonderland.common.messages.ResponseMessage;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IAdapterObserver;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.components.IDCellComponent;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.components.ImageCellComponent;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.components.ImageChangeListener;
import org.jdesktop.wonderland.modules.oweditor.common.IDCellComponentServerState;

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
    private ImageChangeListener imageListener = null;
    
    private static final Logger LOGGER =
            Logger.getLogger(GUIEventManager.class.getName());

    
    /**
     * Creates a new instance.
     * 
     * @param ac: the adapter controller instance.
     */
    public ServerEventManager(WonderlandAdapterController ac){
        this.ac = ac;
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
                    LOGGER.warning("IMAGE COMPONTENT CREATED");
                    imageComponentCreated(cell);
                }
            }
        };
        
        imageListener = new ImageListener();
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
            observer.notifyRotation(id, rotation.x, rotation.y, rotation.z);
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
        if(ac.ltm.containsImage(id)){
            ac.ltm.invokeLateImage(ac.sc, id);
        }
        
        String oldName="";
        if(ac.ltm.containsCell(id, name)){
            //copied names
            if(ac.ltm.containsCell(name)){
                ac.bm.addNewCopyID(id, name);
                LOGGER.warning("ADDED COPY ID "+ id);
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
        
        BufferedImage img = null;
        
        ImageCellComponent imageComponent = cell.getComponent(ImageCellComponent.class);
            
        if(imageComponent != null){
            //LOGGER.warning(imageComponent.getID());
            img = imageComponent.getImage();
        }
        
        IDCellComponent idComponent = cell.getComponent(IDCellComponent.class);
        
        if(idComponent != null){
            long oldid = idComponent.getID();
            LOGGER.warning("old id" + oldid);
            
            if(!ac.bm.isActive(oldid) && oldid != -1){
                id = oldid;

                ac.bm.addOriginalCell(id, cell);
            }else if (ac.bm.isActive(oldid)){
                try {
                    if(ac.bm.isOnWhiteList(oldName))
                        ac.sc.deleteComponent(id, IDCellComponentServerState.class);
                    else{
                        ac.sc.remove(id);
                        return;
                    }
                } catch (ServerCommException ex) {
                    LOGGER.log(Level.WARNING,"Could not delete cell with the same "
                    + "original id, which is already used. Current ID:"
                    + id + ", original id "+oldid, ex);
                }
            }else{
                try {
                    ac.sc.deleteComponent(id, IDCellComponentServerState.class);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
            
            LOGGER.warning("cur id" + id);
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
            object.setRotationX(rotation.x);
            object.setRotationY(rotation.y);
            object.setRotationZ(rotation.z);
            object.setScale(scale);
            object.setWidth(width);
            object.setHeight(height);
            object.setName(name);
            object.setImage(img);

            if(cell instanceof AvatarCell){
                object.setType(IDataObject.AVATAR);
                object.setWidth(AdapterSettings.avatarSizeX);
                object.setHeight(AdapterSettings.avatarSizeY);
            }

            object.setName(name);
            observer.notifyObjectCreation(object);
        }        
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
    
    public void imageComponentCreated(Cell cell){
        long id = CellInfoReader.getID(cell);
        id = ac.bm.getOriginalID(id);
        
        ImageCellComponent imageComponent = 
                cell.getComponent(ImageCellComponent.class);
        
        if(imageComponent == null){
            return;
        }
        
        imageComponent.registerChangeListener(imageListener);
        BufferedImage img = imageComponent.getImage();                
        
        if(img == null){
            LOGGER.warning("IMAGE IS NULL");
            ac.ltm.invokeLateImage(ac.sc, id);
        }
        else{
            LOGGER.warning("IMAGE IS NOT NULL");
        }
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
    
    class ImageListener extends Marshaller.Listener implements ImageChangeListener{

        public void imageChanged(BufferedImage img, Cell cell) {
            long id = CellInfoReader.getID(cell);
            id = ac.bm.getOriginalID(id);
                
               for(IAdapterObserver observer : observers){
                    LOGGER.warning("image change" + img);
                    observer.notifyImageChange(id, img);
               }
                
               if(img == null)
                   LOGGER.warning("LISTENER IMG == NULL");
               else
                   LOGGER.warning("LISTENER IMG != NULL");
        }
    }
    

}
