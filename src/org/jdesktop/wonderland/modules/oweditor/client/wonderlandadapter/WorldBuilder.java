package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.bounding.BoundingVolume;
import com.jme.math.Vector3f;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.ClientContext;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellCache;
import org.jdesktop.wonderland.client.comms.WonderlandSession;
import org.jdesktop.wonderland.client.login.LoginManager;
import org.jdesktop.wonderland.common.cell.CellTransform;


/**
 * Gets every object on the server and sends the data from the object
 * to the ServerUpdateAdapter. This is used to build the world, when
 * it is not empty.
 * 
 * @author Patrick
 *
 */
public class WorldBuilder {
    
    private ServerUpdateAdapter sua = null;
    private AdapterController ac = null;
    private ArrayList<Cell> cells = null;
    private final int initialScale = AdapterSettings.initalScale;
    
    /**
     * Creates a new WorldBuilder instance.
     * 
     * @param ac a adapterController instance.
     * @param sua a serverUpdate instance.
     */
    public WorldBuilder( AdapterController ac, ServerUpdateAdapter sua){
        this.sua = sua;
        this.ac = ac;
        cells = new ArrayList<Cell>();
    }
    
    /**
     * Gets the world from the server and extracts data from it.
     */
    public void build(){
        
        WonderlandSession session = LoginManager.getPrimary().getPrimarySession();
        
        CellCache cache = ClientContext.getCellCache(session);
        if (cache == null) {
            //LOGGER.warning("Unable to find Cell cache for session " + session);
            return;
        }
        
        Collection<Cell> rootCells = cache.getRootCells();
        for (Cell rootCell : rootCells) {
            iterateChilds(rootCell, false);
        }
        
        for(Cell cell : cells){
            createDataObject(cell);
            cell.addTransformChangeListener(ac.tl);
        }
    }
    
    private void iterateChilds(Cell cell, boolean b){
        
        Collection<Cell> childs = cell.getChildren();
        cells.add(cell);
   
        for(Cell child : childs)
        {
            iterateChilds(child, true);
        }
        
    }
    
    private void createDataObject(Cell cell){
        
        CellTransform transform = cell.getLocalTransform();
        Vector3f transl = transform.getTranslation(null);
        
        float x1 = (int) Math.round(transl.x);
        float y1 = (int) Math.round(transl.y);
        float z1 = (int) Math.round(transl.z);
        long id = Long.valueOf(cell.getCellID().toString());
        int height = 0;
        int width = 0;
        String name = cell.getName();
        float rotation = 0;//transform.getRotation(null);
        float scale = transform.getScaling();
        
        int x = 0;
        int y = 0;
        int z = 0;
        
        BoundingVolume bounds = cell.getWorldBounds();
        
        if(bounds instanceof BoundingBox){
            BoundingBox box = (BoundingBox) bounds;
            float xExtent = box.xExtent;
            float yExtent = box.zExtent;
            float zExtent = box.yExtent;
            width = (int) Math.round(xExtent*2*initialScale);
            height = (int) Math.round(yExtent*2*initialScale);
            x = (int) Math.round((x1-xExtent)*initialScale);
            y = (int) Math.round((y1-yExtent)*initialScale);
            z = (int) Math.round((z1-zExtent)*initialScale);
            
        }else if(bounds instanceof BoundingSphere){
            BoundingSphere sphere = (BoundingSphere) bounds;
            float radius = sphere.radius;
            width = (int) Math.round(radius*2*initialScale);
            height = (int) Math.round(radius*2*initialScale);
            x = (int) Math.round((x1-radius)*initialScale);
            y = (int) Math.round((y1-radius)*initialScale);
            z = (int) Math.round((z1-radius)*initialScale);
        }
        
        sua.createObject(id, x, y, z, rotation, scale, width, height, name);
    }
}
