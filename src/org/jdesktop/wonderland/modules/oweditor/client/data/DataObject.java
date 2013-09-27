package org.jdesktop.wonderland.modules.oweditor.client.data;


public class DataObject implements DataObjectInterface{
    
    private Vector3D coords = null;
    private double rotation = 0;
    private double scale = 0;
    private int id = 0;
    private int width = 0;
    private int height = 0;
    
    DataObject(int id, int x, int y, int z, double rotation, double scale, int width, int height){
        this.id = id;
        coords = new Vector3D(x,y,z);
        this.rotation = rotation;
        this.scale = scale;
        this.width = width;
        this.height = height;
    }
    
    public void setCoordinates(int x, int y, int z){
        coords.x = x;
        coords.y = y;
        coords.z = z;
    }
    
    public void setRotation (double rotation){
        this.rotation = rotation;
    }
    
    public void setScale(double scale){
        this.scale = scale;
    }
    
    public Vector3D getCoordinates(){
        return coords;
    }
    
    public double  getRotation(){
        return rotation;
    }
    
    public double getScale(){
        return scale;
    }
    
    public int getID(){
        return id;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }

    @Override
    public int getX() {
        return coords.x;
    }

    @Override
    public int getY() {
        return coords.y;
    }

    @Override
    public int getZ() {
        return coords.z;
    }

}
