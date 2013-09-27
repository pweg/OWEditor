package org.jdesktop.wonderland.modules.oweditor.client.data;

public interface DataUpdateInterface {
    
    public void updateObject(int id, int x, int y, int z, double rotation, double scale);
    
    public void createObject(int id, int x, int y, int z, double rotation, double scale, int width, int height);

}
