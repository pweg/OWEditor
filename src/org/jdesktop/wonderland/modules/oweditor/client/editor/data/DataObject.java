package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectInterface;


public class DataObject implements DataObjectInterface{
    
    private Vector3D coords = null;
    private double rotation = 0;
    private double scale = 0;
    private long id = 0;
    private int width = 0;
    private int height = 0;
    private String name = "";
    
    DataObject(){
        coords = new Vector3D(0,0,0);
    }
    
    DataObject(long id, int x, int y, int z, double rotation, double scale, int width, int height,
            String name){
        this.id = id;
        coords = new Vector3D(x,y,z);
        this.rotation = rotation;
        this.scale = scale;
        this.width = width;
        this.height = height;
        this.name = name;
    }
    
    @Override
    public void setCoordinates(int x, int y, int z){
        coords.x = x;
        coords.y = y;
        coords.z = z;
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
    
    public long getID(){
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setX(int x) {
        coords.x = x;
        
    }

    @Override
    public void setY(int y) {
        coords.y = y;
        
    }

    @Override
    public void setZ(int z) {
        coords.z = z;
        
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void setID(long id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setRotation(double rotation) {
        this.rotation = rotation;
        
    }

    @Override
    public void setScale(double scale) {
        this.scale = scale;
    }

}
