package org.jdesktop.wonderland.modules.oweditor.client.data;

public interface DataObjectInterface {

    public int getX();
    public int getY();
    public int getZ();
    public double getRotation();
    public double getScale();
    public int getWidth();
    public int getHeight();
    public int getID();
    public String getName();
    
    public void setX(int x);
    public void setY(int y);
    public void setZ(int z);
    public void setRotation(double rotation);
    public void setScale(double scale);
    public void setWidth(int width);
    public void setHeight(int height);
    public void setID(int id);
    public void setName(String name);
    public void setCoordinates(int x, int y, int z);
    

}
