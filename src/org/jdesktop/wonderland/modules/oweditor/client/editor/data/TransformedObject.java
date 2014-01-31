package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import java.awt.image.BufferedImage;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.TransformedObjectInterface;

public class TransformedObject implements TransformedObjectInterface{

    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;
    private String name = "";
    
    private double scale = 0;
    private double rotation = 0;
    private long id = 0;
    private byte type = DataObjectInterface.RECTANGLE;
    private BufferedImage img = null;
    
    public TransformedObject(long id, int x, int y, int width, int height,
            double scale, double rotation, String name, byte type, 
            BufferedImage img){
        this.id = id;
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.scale = scale;
        this.width = width;
        this.height = height;
        this.name = name;
        this.type = type;
        this.img = img;
    }
    
    
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public double getRotation() {
        // TODO Auto-generated method stub
        return rotation;
    }

    @Override
    public double getScale() {
        return scale;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getID() {
        return id;
    }

    @Override
    public byte getType() {
        return type;
    }
    
    @Override
    public BufferedImage getImage(){
        return img;
    }

}
