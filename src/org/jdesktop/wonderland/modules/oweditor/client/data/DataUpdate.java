package org.jdesktop.wonderland.modules.oweditor.client.data;

public class DataUpdate implements DataUpdateInterface{
    
    private DataManager dm = null;

    public DataUpdate(DataManager dm) {
        this.dm = dm;
    }

    @Override
    public void updateObject(int id, int x, int y, int z, double rotation,
            double scale) {
        dm.updataData(id, x, y, z, rotation, scale);
        
    }

    @Override
    public void createObject(int id, int x, int y, int z, double rotation,
            double scale, int width, int height) {
        dm.createNewObject(id, x, y, z, rotation, scale, width, height);
        
    }

}
