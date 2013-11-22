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
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    
    private AdapterController ac = null;
    
    private LinkedHashMap<Long, Integer> copies = null;
    
    public ServerCommunication(AdapterController ac){
        this.ac = ac;
        copies = new LinkedHashMap<Long, Integer>();
    }
    
    
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
    
    public void paste(long id, int x, int y){
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
            
            String name = cell.getName();
            String count = "1";
            
            if(copies.containsKey(id)){
                int copy_count = copies.get(id)+1;
                count = String.valueOf(copy_count);
                copies.put(id, (copy_count));
            }else{
                copies.put(id, 1);
            }
            
            Vector3f coordinates = CellInfoReader.getCoordinates(cell);
            coordinates.x = x;
            coordinates.y = y;
            
            name = BUNDLE.getString("CopyName")+ name+"_"+count+"ID"+session.getID()+"_"+id;
                        
            ac.bm.addTranslation(id, name, coordinates);
            
            String message = MessageFormat.format(name, cell.getName());

              // If we want to delete, send a message to the server as such
           
            CellEditChannelConnection connection = 
                    (CellEditChannelConnection) session.getConnection(
                    CellEditConnectionType.CLIENT_TYPE);
            CellDuplicateMessage msg =
                    new CellDuplicateMessage(cell.getCellID(), message);
            connection.send(msg);
        }catch(Exception ex){
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
    public void rotate(long id, Vector3f rotation) {
        
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
        
        Quaternion newRotation = ac.ct.setStandardRotation(cell, rotation);
        if (movableComponent != null) {
            CellTransform cellTransform = cell.getLocalTransform();
            cellTransform.setRotation(newRotation);
            movableComponent.localMoveRequest(cellTransform);
        }
    }
    
}
