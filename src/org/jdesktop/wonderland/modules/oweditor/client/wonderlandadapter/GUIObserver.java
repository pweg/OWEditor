package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import java.awt.Point;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.ClientContext;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellCache;
import org.jdesktop.wonderland.client.cell.CellEditChannelConnection;
import org.jdesktop.wonderland.client.cell.MovableComponent;
import org.jdesktop.wonderland.client.cell.utils.CellUtils;
import org.jdesktop.wonderland.client.comms.WonderlandSession;
import org.jdesktop.wonderland.client.login.LoginManager;
import org.jdesktop.wonderland.common.cell.CellEditConnectionType;
import org.jdesktop.wonderland.common.cell.CellID;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.common.cell.CellTransform;
import org.jdesktop.wonderland.common.cell.messages.CellDuplicateMessage;

/**
 * This class is used to make updates the client makes in
 * the GUI and forwards it to the server. 
 * 
 * @author Patrick
 *
 */
public class GUIObserver implements GUIObserverInterface{
    
    private WonderlandAdapterController ac = null;    
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    
    private static final Logger LOGGER =
            Logger.getLogger(GUIObserver.class.getName());
    
    private LinkedHashMap<String, Integer> copies = null;
    
    //Backups every deleted cell. Should probably clear it, when new
    //copy, but could be needed for undo purposes.
    private LinkedHashMap<Long, Cell> backupCells = null;
    
    private int maxTries = 20;
    
    /**
     * Creates a new clientUpdate instance.
     * 
     * @param ac a adpater controller instance.
     */
    public GUIObserver(WonderlandAdapterController ac){
        this.ac = ac;
        copies = new LinkedHashMap<String, Integer>();
        backupCells = new LinkedHashMap<Long, Cell> ();
    }

    @Override
    public void notifyTranslation(long id, int x, int y ){
        translate(id,x,y);
    }
    
    protected boolean translate(long id, int x, int y) {
        
        WonderlandSession session = LoginManager.getPrimary().getPrimarySession();
        
        CellCache cache = ClientContext.getCellCache(session);
        if (cache == null) {
            LOGGER.warning("Unable to find Cell cache for session " + session);
            return false;
        }
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        
        if(cell == null)
            return false;
        
        CellTransform transform = cell.getLocalTransform();
        Vector3f transl = transform.getTranslation(null);
                
        float z = transl.z;
        
        Vector3f translation = ac.ct.transformCoordinatesBack(cell, (float)x, (float)y, z);
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

    public void notifyRemoval(long id) {
        WonderlandSession session = LoginManager.getPrimary().getPrimarySession();
        
        CellCache cache = ClientContext.getCellCache(session);
        if (cache == null) {
            LOGGER.warning("Unable to find Cell cache for session " + session);
            return;
        }
        
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        
        backupCells.put(id, cell);
        CellUtils.deleteCell(cell);
    }

    public void notifyCopy(long id, int x, int y) {
          
        WonderlandSession session = LoginManager.getPrimary().getPrimarySession();
        
        CellCache cache = ClientContext.getCellCache(session);
        if (cache == null) {
            LOGGER.warning("Unable to find Cell cache for session " + session);
            return;
        }
        
        try{
            CellID cellid = new CellID(id);
            Cell cell = cache.getCell(cellid);
            
            if(cell == null)
                cell = backupCells.get(id);
            
            String name = cell.getName();
            String count = "1";
            
            if(copies.containsKey(name)){
                int copy_count = copies.get(name)+1;
                count = String.valueOf(copy_count);
                copies.put(name, (copy_count));
            }else{
                copies.put(name, 1);
            }
            
            name = BUNDLE.getString("CopyName")+ name+"_"+count+"ID"+session.getID()+"_"+id;
            
            ac.sua.putCopyTranslation(name, new Point(x,y));
            
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

    public void notifyRotation(long id, int x, int y, double rotation) {
        translate(id, x, y);
        
        WonderlandSession session = LoginManager.getPrimary().getPrimarySession();
        
        CellCache cache = ClientContext.getCellCache(session);
        if (cache == null) {
            LOGGER.warning("Unable to find Cell cache for session " + session);
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
}
