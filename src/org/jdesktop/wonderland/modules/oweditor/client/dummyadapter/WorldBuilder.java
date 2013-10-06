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
    
    private ServerUpdateAdapter sua = null;
    private AdapterController ac = null;
    
    /**
     * Creates a new WorldBuilder instance.
     * 
     * @param sua: a serverUpdate instance.
     */
    public WorldBuilder( AdapterController ac, ServerUpdateAdapter sua){
        this.sua = sua;
        this.ac = ac;
    }
    
    /**
     * Gets the world from the server and extracts data from it.
     */
    public void build(){
        initShapes();
    }
    
    /**
     * This is only used for testing purposes.
     */
    private void initShapes() {  

    	ArrayList<ServerObject> objects = ac.ses.getObjects();
    	
    	for(ServerObject object : objects){
    		
    		long id = object.id;
    		int x = object.x;
    		int y = object.y;
    		int z = object.z;
    		double rotation = object.rotation;
    		double scale = object.scale;
    		int width = object.width;
    		int height = object.height;
    		String name = object.name;
    		sua.createObject(id, x, y, z, rotation, scale, width, height, name);
    	}
    }
}
