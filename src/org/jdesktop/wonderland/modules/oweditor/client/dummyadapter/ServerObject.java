package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import java.awt.image.BufferedImage;

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
    
    protected double rotationX = 0;
    protected double rotationY = 0;
    protected double rotationZ = 0;

    protected double scale = 0;
    
    protected float width = 0;
    protected float height = 0;
    
    protected long id = 0;
    protected String name = "";
    protected boolean isAvatar = false;
    protected BufferedImage image = null;
    protected String imgName = null;
    
    ServerObject(long id, float x, float y, float z, double rotationX, 
            double rotationY, double rotationZ,
            double scale, float width, float height, String name){
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotationX = rotationX;
        this.rotationY = rotationY;
        this.rotationZ = rotationZ;
        this.scale = scale;
        this.id = id;
        this.name = name;    
        this.height = height;
        this.width = width;
    }
    
    ServerObject(long id, float x, float y, float z, double rotationX, 
            double rotationY, double rotationZ,
            double scale, float width, float height, String name,
            BufferedImage image, String imgName){
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotationX = rotationX;
        this.rotationY = rotationY;
        this.rotationZ = rotationZ;
        this.scale = scale;
        this.id = id;
        this.name = name;    
        this.height = height;
        this.width = width;
        this.image = image;
        this.imgName = imgName;
    }
    
    public ServerObject clone(){
        return new ServerObject(this.id, this.x,this.y,this.z, this.rotationX,
                this.rotationY, this.rotationZ,
                this.scale, this.width, this.height, this.name, this.image,
                this.imgName);
    }
    

    
}
