package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.AdapterSettings;


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
    private final int initialScale = AdapterSettings.initalScale;
    
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
    		int x = (int) Math.round(object.x*initialScale);
    		int y = (int) Math.round(object.y*initialScale);
    		int z = (int) Math.round(object.z*initialScale);    		
    		double rotation = object.rotation;
    		double scale = object.scale;
    		int width = (int) Math.round(object.width*initialScale);
    		int height = (int) Math.round(object.height*initialScale);
    		
    		String name = object.name;
    		//System.out.println(x + " " + y + " " + width + " "+ name);
    		sua.createObject(id, x, z, y, rotation, scale, width, height, name);
    	}
    }
}
