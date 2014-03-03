package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellCache;
import org.jdesktop.wonderland.client.login.LoginManager;
import org.jdesktop.wonderland.client.login.ServerSessionManager;


/**
 * Gets every object currently on the server and sends the data from the object
 to the ServerEventManager. This is used to build the world, when
 * it is not empty.
 * 
 * @author Patrick
 *
 */
public class WorldBuilder {
    
    private ServerEventManager sua = null;
    private WonderlandAdapterController ac = null;
    
    private ArrayList<Cell> cells = null;
    private static final Logger LOGGER =
            Logger.getLogger(WorldBuilder.class.getName());
    
    /**
     * Creates a new WorldBuilder instance.
     * 
     * @param ac a adapterController instance.
     * @param sua a serverUpdate instance.
     */
    public WorldBuilder( WonderlandAdapterController ac, ServerEventManager sua){
        this.sua = sua;
        this.ac = ac;
        cells = new ArrayList<Cell>();
    }
    
    /**
     * Gets the world from the server and extracts data from it.
     */
    public void build(){
        
        setServer();
        
        CellCache cache = ac.sm.getCellCache();
        
        if (cache == null) {
            LOGGER.warning("Unable to find Cell cache for session " + ac.sm.getSession());
            return;
        }
        
        Collection<Cell> rootCells = cache.getRootCells();
        for (Cell rootCell : rootCells) {
            iterateChilds(rootCell, false);
        }
        
        for(Cell cell : cells){
            createDataObject(cell);
        }
    }
    
    private void setServer(){
        
        Collection<ServerSessionManager> servers = LoginManager.getAll();
        
        String[] serverList = new String[servers.size()];
            //@Todo: Make a selection option in the gui!
        int i = 0;
        for (ServerSessionManager server : servers) {
            serverList[i] = server.getServerNameAndPort();
            i++;
        }
        sua.setServerList(serverList);
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
                
        sua.creationEvent(cell);
    }
}