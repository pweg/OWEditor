package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.math.Vector3f;
import java.util.ArrayList;
import java.util.logging.Logger;
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

/**
 * This class is used for updating the data package, when
 * there are changes made on the server. 
 * 
 * @author Patrick
 *
 */
public class ServerEventManager {
    
    private WonderlandAdapterController ac = null;
    private IAdapterObserver dui = null;
    
    private ComponentChangeListener componentListener = null;
    
    private static final Logger LOGGER =
            Logger.getLogger(GUIEventManager.class.getName());

    
    /**
     * Creates a new instance.
     * 
     * @param ac: the adapter controller instance.
     */
    public ServerEventManager(WonderlandAdapterController ac){
        this.ac = ac;
        
        componentListener = new ComponentChangeListener() {
            public void componentChanged(Cell cell, 
                    ComponentChangeListener.ChangeType type, 
                    CellComponent component) {     
                if (type == ComponentChangeListener.ChangeType.ADDED && 
                        component instanceof MovableComponent) {  
                    movableComponentCreated(cell);  
                }
            }
        };
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
        
        if(dui == null){
            LOGGER.warning("DataInterface is not in the adapter");
            return;
        }
        
        Vector3fInfo vector = CellInfoReader.getCoordinates(cell);
        Vector3f rotation = CellInfoReader.getRotation(cell); 
        float scale = CellInfoReader.getScale(cell);
        
        float x =  vector.x;
        float y =  vector.y;
        float z =  vector.z;
        
        long id = CellInfoReader.getID(cell);
        
        dui.notifyScaling(id,(double) scale); 
        dui.notifyTranslation(id , x, y, z); 
        dui.notifyRotation(id, rotation.x, rotation.y, rotation.z);
    }

    /**
     * Sets a dataUpdateInterface instance in order to transmit changes
     * and new objects to the data package.
     * 
     * @param dui a dataUpdate instance.
     */
    public void registerDataInterface(IAdapterObserver dui) {
        this.dui = dui;
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
        dui.notifyRemoval(id);
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
        
        if(dui == null)
            return;
        
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
        
        if(ac.ltm.containsCell(id, name)){
            if(!ac.ltm.invokeLateTransform(ac.sc, id, name)){
                cell.addComponentChangeListener(componentListener);
            }
        }
        
        Vector3fInfo coordinates = CellInfoReader.getCoordinates(cell);
        Vector3f rotation = CellInfoReader.getRotation(cell);
        float scale = CellInfoReader.getScale(cell);
        
        IDataObject object= dui.createEmptyObject();
        
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
        
        if(cell instanceof AvatarCell){
            object.setType(IDataObject.AVATAR);
            object.setWidth(AdapterSettings.avatarSizeX);
            object.setHeight(AdapterSettings.avatarSizeY);
        }
        
        object.setName(name);
        dui.notifyObjectCreation(object);
        
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
        
        ac.ltm.invokeLateTransform(ac.sc, id, name);
    }

    /**
     * Forwards the serverlist to the data package.
     * 
     * @param serverList A string arry containing the server.
     */
    void setServerList(String[] serverList) {
        dui.setServerList(serverList);
    }

}
