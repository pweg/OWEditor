package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

public interface DataObjectManagerInterface {
    
    public DataObjectInterface getObject(long id);
    
    public int getZ(long id);
    
    public DataObjectInterface getEmptyDataObject();

}
