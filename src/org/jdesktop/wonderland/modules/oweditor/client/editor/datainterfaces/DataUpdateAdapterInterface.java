package org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces;


public interface DataUpdateAdapterInterface {
    
    public void updateObject(DataObjectInterface data);
    
    public void createObject(DataObjectInterface data);
    
    public DataObjectInterface createEmptyObject();

}
