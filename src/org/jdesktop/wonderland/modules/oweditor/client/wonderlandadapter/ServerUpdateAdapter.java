package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import java.awt.Point;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellComponent;
import org.jdesktop.wonderland.client.cell.ComponentChangeListener;
import org.jdesktop.wonderland.client.cell.MovableComponent;
import org.jdesktop.wonderland.client.cell.view.AvatarCell;
import org.jdesktop.wonderland.common.cell.CellTransform;
import org.jdesktop.wonderland.common.cell.messages.CellServerComponentMessage;
import org.jdesktop.wonderland.common.messages.ErrorMessage;
import org.jdesktop.wonderland.common.messages.ResponseMessage;
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
    
    private WonderlandAdapterController ac = null;
    private AdapterObserverInterface dui = null;
    
    private ComponentChangeListener componentListener = null;
    
    private static final Logger LOGGER =
            Logger.getLogger(GUIObserver.class.getName());

    
    /**
     * Creates a new serverUpdate instance.
     * 
     * @param ac: the adapter controller instance.
     */
    public ServerUpdateAdapter(WonderlandAdapterController ac){
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
    public void serverTransformEvent(Cell cell){
        
        if(dui == null){
            LOGGER.warning("DataInterface is not in the adapter");
            return;
        }
        
        Vector3fInfo vector = CellInfoReader.getCellInfo(cell);
        Vector3f rotation = CellInfoReader.getRotation(cell);    
        
        float x =  vector.x;
        float y =  vector.y;
        float z =  vector.z;
        
        long id = CellInfoReader.getID(cell);
        
        dui.notifyTranslation(id , x, y, z); 
        dui.notifyRotation(id, rotation.x, rotation.y, rotation.z);        
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
    
    public void serverRemovalEvent(Cell cell){
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
    public void createObject(Cell cell){
        
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
        
        /**
         * This is used for translating copied cells, after they are created.
         * This is not the best solution, because Wonderland does not create
         * the possibility to copy cells to a specific location.
         */
        if(ac.bm.translationContainsKey(name)){
            
            Point p = ac.bm.getTranslation(name);
            /*
            * When problems arise, due to the absence of the movable component,
            * a listener is created, for the purpose to translate the copied cell.
            */
            if(!ac.go.translate(id, p.x, p.y)){
                cell.addComponentChangeListener(componentListener);
            }else{
                BackupObject backup = ac.bm.getCell(name);
                
                //rotation = backup.getRotation();
                //scale = backup.getScale();
                ac.go.rotation(id, backup.getRotation());
                ac.bm.removeTranslation(name);
            }
            
            
        }
        
        Vector3fInfo vector = CellInfoReader.getCellInfo(cell);
        Vector3f rotation = CellInfoReader.getRotation(cell);
        float scale = CellInfoReader.getScale(cell);
        
        DataObjectInterface object = dui.createEmptyObject();
        
        
        float x = vector.x;
        float y = vector.y;
        float z = vector.z;
        
        float height = vector.height;
        float width = vector.width;
        
        
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
            object.setType(DataObjectInterface.AVATAR);
            object.setWidth(AdapterSettings.avatarSizeX);
            object.setHeight(AdapterSettings.avatarSizeY);
        }
        
        object.setName(name);
        dui.notifyObjectCreation(object);
        
    }
    
    private void movableComponentCreated(Cell cell){
        
        String name = cell.getName();
        long id = CellInfoReader.getID(cell);
        
        if(ac.bm.translationContainsKey(name)){
            Point p = ac.bm.getTranslation(name);
            ac.go.translate(id, p.x, p.y);
            
            BackupObject backup = ac.bm.getCell(name);
            ac.go.rotation(id, backup.getRotation());
            
            ac.bm.removeTranslation(name);
        }
    }

}
