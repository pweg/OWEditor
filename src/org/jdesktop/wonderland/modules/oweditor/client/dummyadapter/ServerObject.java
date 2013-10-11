package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

/**
 * A simple server object class for testing purposes.
 * 
 * @author Patrick
 *
 */
public class ServerObject {
    
    protected float x = 0;
    protected float y = 0;
    protected float z = 0;
    protected double rotation = 0;
    protected double scale = 0;
    protected float width = 0;
    protected float height = 0;
    
    protected long id = 0;
    protected String name = "";
    protected boolean isAvatar = false;
    
    ServerObject(long id, float x, float y, float z, double rotation, 
            double scale, float width, float height, String name){
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotation = rotation;
        this.scale = scale;
        this.id = id;
        this.name = name;    
        this.height = height;
        this.width = width;
    }
    
    public ServerObject clone(){
        return new ServerObject(this.id, this.x,this.y,this.z, this.rotation, 
                this.scale, this.width, this.height, this.name);
    }
    

    
}
