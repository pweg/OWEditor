package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.bounding.BoundingVolume;
import com.jme.math.Vector3f;
import java.util.Collection;
import org.jdesktop.wonderland.client.ClientContext;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellCache;
import org.jdesktop.wonderland.client.cell.MovableComponent;
import org.jdesktop.wonderland.client.comms.WonderlandSession;
import org.jdesktop.wonderland.client.login.LoginManager;
import org.jdesktop.wonderland.common.cell.CellID;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.common.cell.CellTransform;

/**
 * This class is used to make updates the client makes in
 * the GUI and forwards it to the server. 
 * 
 * @author Patrick
 *
 */
public class GUIObserver implements GUIObserverInterface{
    
    private AdapterController ac = null;
    private int initialScale = AdapterSettings.initalScale;
    
    /**
     * Creates a new clientUpdate instance.
     * 
     * @param ac a adpater controller instance.
     */
    public GUIObserver(AdapterController ac){
        this.ac = ac;
    }

    @Override
    public void notifyTranslation(long id, int x, int y, int z) {
        
        float server_x = x;
        float server_y = z;
        float server_z = y;
        
        WonderlandSession session = LoginManager.getPrimary().getPrimarySession();
        
        CellCache cache = ClientContext.getCellCache(session);
        if (cache == null) {
            //LOGGER.warning("Unable to find Cell cache for session " + session);
            return;
        }
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        
        if(cell == null)
            return;
        
        BoundingVolume bounds = cell.getWorldBounds();
        
        if(bounds instanceof BoundingBox){
            BoundingBox box = (BoundingBox) bounds;
            float xExtent = box.xExtent;
            float yExtent = box.zExtent;
            float zExtent = box.yExtent;
            server_x = (server_x/initialScale+xExtent);
            server_y = (server_y/initialScale+yExtent);
            server_z = (server_z/initialScale+zExtent);
            
        }else if(bounds instanceof BoundingSphere){
            BoundingSphere sphere = (BoundingSphere) bounds;
            float radius = sphere.radius;
            server_x = (server_x/initialScale+radius);
            server_y = (server_y/initialScale+radius);
            server_z = (server_z/initialScale+radius);
        }
        
        Vector3f translation = new Vector3f(server_x, server_y, server_z);
        MovableComponent movableComponent = cell.getComponent(MovableComponent.class);

        
        if (movableComponent != null) {
            CellTransform cellTransform = cell.getLocalTransform();
            cellTransform.setTranslation(translation);
            movableComponent.localMoveRequest(cellTransform);
        }
        
    }
    
    

}
