/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
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
            Logger.getLogger(GUIObserver.class.getName());
    
    private WonderlandAdapterController ac = null;
    
    
    public ServerCommunication(WonderlandAdapterController ac){
        this.ac = ac;
    }
    
    /**
     * @TODO: Should be replaced by other translate method!
     * @param id
     * @param x
     * @param y
     * @return 
     */
    public boolean translate(long id, int x, int y) {
        
        CellCache cache = ac.sm.getCellCache();
        
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            return false;
        }
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        
        if(cell == null)
            return false;
        
        /*
        CellTransform transform = cell.getLocalTransform();
        Vector3f transl = transform.getTranslation(null);
        float z = transl.z;
        */
        
        Vector3fInfo coords = CellInfoReader.getCoordinates(cell);
        
        Vector3f translation = ac.ct.transformCoordinatesBack(cell, (float)x, (float)y, coords.z);
        MovableComponent movableComponent = cell.getComponent(MovableComponent.class);

        if (movableComponent != null) {
            CellTransform cellTransform = cell.getLocalTransform();
            cellTransform.setTranslation(translation);
            movableComponent.localMoveRequest(cellTransform);
            return true;
        }else{
            return false;
        }
    }
    
    public boolean translate(long id, Vector3f translation){
        CellCache cache = ac.sm.getCellCache();
        
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            return false;
        }
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        
        if(cell == null)
            return false;
        
        MovableComponent movableComponent = cell.getComponent(MovableComponent.class);

        if (movableComponent != null) {
            CellTransform cellTransform = cell.getLocalTransform();
            cellTransform.setTranslation(translation);
            movableComponent.localMoveRequest(cellTransform);
            return true;
        }else{
            return false;
        }
        
    }
    
    public void remove(long id){
        
        CellCache cache = ac.sm.getCellCache();
        
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            return;
        }
        
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        CellUtils.deleteCell(cell);
    }
    
    /**
     * Copies a cell.
     * 
     * @param id The id of the cell to copy.
     * @param name The name of the new cell.
     */
    public void paste(long id, String name){
        WonderlandSession session = ac.sm.getSession();
        CellCache cache = ac.sm.getCellCache();
        
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", session);
            return;
        }
        
        try{
            CellID cellid = new CellID(id);
            Cell cell = cache.getCell(cellid);
            
            if(cell == null)
                cell = ac.bm.getCell(id);
            if(cell == null)
                return;
            
            String message = MessageFormat.format(name, cell.getName());

              // If we want to delete, send a message to the server as such
           
            CellEditChannelConnection connection = 
                    (CellEditChannelConnection) session.getConnection(
                    CellEditConnectionType.CLIENT_TYPE);
            CellDuplicateMessage msg =
                    new CellDuplicateMessage(cell.getCellID(), message);
            connection.send(msg);
            return;
        }catch(Exception ex){
            return;
        }
    }
    
    public void rotate(long id, int x, int y, double rotation){
        translate(id, x, y);
        
        CellCache cache = ac.sm.getCellCache();
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            return;
        }
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        
        if(cell == null)
            return;
        
        MovableComponent movableComponent = cell.getComponent(MovableComponent.class);
        
        Quaternion newRotation = ac.ct.setRotation(cell, rotation);
        if (movableComponent != null) {
            CellTransform cellTransform = cell.getLocalTransform();
            cellTransform.setRotation(newRotation);
            movableComponent.localMoveRequest(cellTransform);
        }
    }
    
    public boolean rotate(long id, Vector3f rotation) {
        
        CellCache cache = ac.sm.getCellCache();
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            return false;
        }
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        if(cell == null)
            return false;
        
        MovableComponent movableComponent = cell.getComponent(MovableComponent.class);
        
        Quaternion newRotation = ac.ct.setStandardRotation(cell, rotation);
        if (movableComponent != null) {
            CellTransform cellTransform = cell.getLocalTransform();
            cellTransform.setRotation(newRotation);
            movableComponent.localMoveRequest(cellTransform);
        }else{
            return false;
        }
        return true;
    }
    
    public void scale(long id, int x, int y, double scale){
        
        /*CellCache cache = ac.sm.getCellCache();
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            return;
        }
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        
        if(cell == null)
            return;
        
        MovableComponent movableComponent = cell.getComponent(MovableComponent.class);
        
        if (movableComponent != null) {
            CellTransform cellTransform = cell.getLocalTransform();
            cellTransform.setScaling((float) scale);
            movableComponent.localMoveRequest(cellTransform);
        }*/
        scale(id, scale);
        translate(id, x, y);
    }
    
    
    public boolean scale(long id, double scale){
        
        CellCache cache = ac.sm.getCellCache();
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            return false;
        }
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        
        if(cell == null)
            return false;
        
        MovableComponent movableComponent = cell.getComponent(MovableComponent.class);
        
        if (movableComponent != null) {
            CellTransform cellTransform = cell.getLocalTransform();
            cellTransform.setScaling((float) scale);
            movableComponent.localMoveRequest(cellTransform);
            return true;
        }
        return false;
    }
    
}
