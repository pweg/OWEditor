package org.jdesktop.wonderland.modules.oweditor.client.data;

public interface DataObjectManagerInterface {
    
    public DataObjectInterface getObject(int id);
    
    public int getZ(int id);
    
    public DataObjectInterface getEmptyDataObject();

}
