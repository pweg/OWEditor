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
        private static final Logger LOGGER =
            Logger.getLogger(WorldBuilder.class.getName());
    
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
                
        sua.createObject(cell);
    }
}
