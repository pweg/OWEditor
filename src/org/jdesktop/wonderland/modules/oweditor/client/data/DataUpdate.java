package org.jdesktop.wonderland.modules.oweditor.client.data;

public class DataUpdate implements DataUpdateInterface{
    
    private DataObjectManager dm = null;

    public DataUpdate(DataObjectManager dm) {
        this.dm = dm;
    }

    @Override
    public void updateObject(DataObjectInterface dataObject) {
        dm.updataData(dataObject);
        
    }

    @Override
    public void createObject(DataObjectInterface dataObject) {
        dm.createNewObject(dataObject);
        
    }

    @Override
    public DataObjectInterface createEmptyObject() {
        
        return dm.getEmptyDataObject();
    }

}
