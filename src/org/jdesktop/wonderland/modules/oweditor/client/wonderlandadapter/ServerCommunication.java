/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellCache;
import org.jdesktop.wonderland.client.cell.CellEditChannelConnection;
import org.jdesktop.wonderland.client.cell.MovableComponent;
import org.jdesktop.wonderland.client.cell.utils.CellUtils;
import org.jdesktop.wonderland.client.comms.WonderlandSession;
import org.jdesktop.wonderland.common.cell.CellEditConnectionType;
import org.jdesktop.wonderland.common.cell.CellID;
import org.jdesktop.wonderland.common.cell.CellTransform;
import org.jdesktop.wonderland.common.cell.messages.CellDuplicateMessage;

/**
 * This class is used for outgoing communication with the server.
 * 
 * @author Patrick
 */
public class ServerCommunication {
    
    private static final Logger LOGGER =
            Logger.getLogger(GUIEventManager.class.getName());
    
    private WonderlandAdapterController ac = null;
    
    
    public ServerCommunication(WonderlandAdapterController ac){
        this.ac = ac;
    }
    
    /**
     * Translates a cell to the given position.
     * 
     * @param id The id of the cell.
     * @param translation The new wonderland coordinates.
     * @throws org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.ServerCommException 
     */
    public void translate(long id, Vector3f translation) throws ServerCommException{
        CellCache cache = ac.sm.getCellCache();
        
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            throw new ServerCommException();
        }
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        
        if(cell == null)
            throw new ServerCommException();
        
        MovableComponent movableComponent = cell.getComponent(MovableComponent.class);

        if (movableComponent != null) {
            CellTransform cellTransform = cell.getLocalTransform();
            cellTransform.setTranslation(translation);
            movableComponent.localMoveRequest(cellTransform);
        }else{
            throw new ServerCommException();
        }
        
    }
    
    /**
     * Removes a given cell.
     * 
     * @param id The id of the cell to remove.
     * @throws org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.ServerCommException
     */
    public void remove(long id) throws ServerCommException{
        
        CellCache cache = ac.sm.getCellCache();
        
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            throw new ServerCommException();
        }
        
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        if(cell == null)
            throw new ServerCommException();
        
        CellUtils.deleteCell(cell);
    }
    
    /**
     * Copies a cell.
     * 
     * @param cell The cell to copy.
     * @param name The name of the new cell.
     * @throws org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.ServerCommException
     */
    public void paste(Cell cell, String name) throws ServerCommException{
        WonderlandSession session = ac.sm.getSession();
        
           
        String message = MessageFormat.format(name, cell.getName());
           
        CellEditChannelConnection connection = 
                (CellEditChannelConnection) session.getConnection(
                CellEditConnectionType.CLIENT_TYPE);
        CellDuplicateMessage msg =
                new CellDuplicateMessage(cell.getCellID(), message);
        connection.send(msg);

    }
    
    /**
     * Rotates a cell.
     * 
     * @param id The id of the cell
     * @param rotation The rotation as vector. Remember, wonderland uses
     * the y coordinates for rotation around the editors xy axis. 
     * @throws org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.ServerCommException 
     */
    public void rotate(long id, Vector3f rotation) throws ServerCommException{
        
        CellCache cache = ac.sm.getCellCache();
        /*if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            return false;
        }*/
        
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        if(cell == null)
            throw new ServerCommException();
        
        MovableComponent movableComponent = cell.getComponent(MovableComponent.class);
        
        Quaternion newRotation = new Quaternion(new float[] 
                { rotation.x, rotation.y, rotation.z });
        
        if (movableComponent != null) {
            CellTransform cellTransform = cell.getLocalTransform();
            cellTransform.setRotation(newRotation);
            movableComponent.localMoveRequest(cellTransform);
        }else{
            throw new ServerCommException();
        }
    }
    
    /**
     * Scales a cell.
     * 
     * @param id The id of the cell.
     * @param scale The scale value.
     * @throws org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.ServerCommException 
     */
    public void scale (long id, float scale) throws ServerCommException {
        
        CellCache cache = ac.sm.getCellCache();
        
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            throw new ServerCommException();
        }
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        
        /*
        if(cell == null)
            return false;*/
        
        MovableComponent movableComponent = cell.getComponent(MovableComponent.class);
        
        if (movableComponent != null) {
            CellTransform cellTransform = cell.getLocalTransform();
            cellTransform.setScaling((float) scale);
            movableComponent.localMoveRequest(cellTransform);
        }else{
            throw new ServerCommException();
        }
    }
    
}
