package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import java.util.ArrayList;


/**
 * Gets every object on the server and sends the data from the object
 * to the ServerUpdateAdapter. This is used to build the world, when
 * it is not empty.
 * 
 * @author Patrick
 *
 */
public class WorldBuilder {
    
    private ServerEventManager sua = null;
    private DummyAdapterController ac = null;
    
    /**
     * Creates a new WorldBuilder instance.
     * 
     * @param sua: a serverUpdate instance.
     */
    public WorldBuilder( DummyAdapterController ac, ServerEventManager sua){
        this.sua = sua;
        this.ac = ac;
    }
    
    /**
     * Gets the world from the server and extracts data from it.
     */
    public void build(){
        
        setServerList();
        initShapes();
    }
    
    public void setServerList(){
        
        String[] servers = new String[1];
        servers[0] = ac.server.name;
        
        sua.setServerList(servers);
        
    }
    
    /**
     * This is only used for testing purposes.
     */
    private void initShapes() {  

        ArrayList<ServerObject> objects = ac.server.getObjects();
        
        for(ServerObject object : objects){
            sua.createObject(object);
        }
    }
}
