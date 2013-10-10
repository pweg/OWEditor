package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.math.Vector3f;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.cell.Cell;
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
    
    private int startScale = AdapterSettings.initalScale;
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
     * This method should be called, when an object has been transformed
     * on the server. It gets a new data object from the data package
     * and fills it with the data it receives. The data object created 
     * here will be discarded afterwards.
     * 
     * @param cell the cell.
     */
    public void serverTransformEvent(Cell cell){
        if(dui == null){
            System.out.println("DataInterface is not in the adapter");
            return;
        }
        Vector3fInfo vector = null;
        
         if(cell instanceof AvatarCell){
            float avatar_size = AdapterSettings.avatarSizeY/2;
            Vector3f size = new Vector3f(avatar_size, avatar_size, avatar_size);
            vector = ac.ct.transformCoordinatesSpecificSize(cell, size);
        }else{
            vector = ac.ct.transformCoordinates(cell);
        }
        
        int x = (int) vector.x;
        int y = (int) vector.y;
        int z = (int) vector.z;
        
        long id = Long.valueOf(cell.getCellID().toString());
        
        dui.notifyTranslation(id , x, y, z);   
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
        
        /*ServerObject so = ac.ses.getObject(id);
        
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
        
        dui.notifyObjectChange(object);*/
        
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
        long id = idTranslator(cell);
        
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
        
        
        CellTransform transform = cell.getLocalTransform();
        
        long id = idTranslator(cell);
        String name = cell.getName();
        float rotation = 0;//transform.getRotation(null);
        float scale = transform.getScaling();
        
        MovableComponent movableComponent = cell.getComponent(MovableComponent.class);
        if (movableComponent == null) {
            String className = "org.jdesktop.wonderland.server.cell." +
                    "MovableComponentMO";
            CellServerComponentMessage cscm = 
                    CellServerComponentMessage.newAddMessage(
                    cell.getCellID(), className);
            ResponseMessage response = cell.sendCellMessageAndWait(cscm);
            if (response instanceof ErrorMessage) {
            }
        }
        
        DataObjectInterface object = dui.createEmptyObject();
        
        Vector3fInfo vector = null;
        
        if(cell instanceof AvatarCell){
            float avatar_size = AdapterSettings.avatarSizeY/2;
            Vector3f size = new Vector3f(avatar_size, avatar_size, avatar_size);
            vector = ac.ct.transformCoordinatesSpecificSize(cell, size);
            object.setIsAvatar(true);
        }else{
            vector = ac.ct.transformCoordinates(cell);
        }
        int x = (int) vector.x;
        int y = (int) vector.y;
        int z = (int) vector.z;
        int height = (int) vector.height;
        int width = (int) vector.width;
        
        
        object.setID(id);
        object.setCoordinates(x, y, z);
        object.setRotation(rotation);
        object.setScale(scale);
        object.setWidth(width);
        object.setHeight(height);
        object.setName(name);
        
        
        if(!name.equals(""))
            object.setName(name);
        

        dui.notifyObjectCreation(object);
    }
    
    private long idTranslator(Cell cell){
        return Long.valueOf(cell.getCellID().toString());
    }

}
