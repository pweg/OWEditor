package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;


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
    
    /**
     * Creates a new WorldBuilder instance.
     * 
     * @param sua: a serverUpdate instance.
     */
    public WorldBuilder(ServerUpdateAdapter sua){
        this.sua = sua;
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

        sua.createObject(0, 160, 160, 0, 0, 1, 70, 70, "Chair");
        sua.createObject(2, 260, 260, 0, 0, 1, 80, 80, "ChairofTestinghugelength");
        sua.createObject(1, 400, 400, 0, 0, 1, 200, 200, "Desk");
        sua.createObject(3, 0, 0, 0, 0, 1, 10, 10, "Tiny");
        sua.createObject(4, 100, 100, 0, 0, 1, 150, 25, "TinyVeryLong");
    }
}
