package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

public interface DataUpdateInterface {
    
    public void updateObject(DataObjectInterface data);
    
    public void createObject(DataObjectInterface data);
    
    public DataObjectInterface createEmptyObject();

}
