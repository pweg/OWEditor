package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.util.ArrayList;

public interface AdapterCommunicationInterface {
    
    public void setObjectRemoval(long id);
    
    public void setTranslationUpdate(long id, int x, int y);
    
    public void setCopyUpdate(ArrayList<Long> object_ids);
    
    public void setPasteUpdate(long id, int x, int y);
    
    public void setRotationUpdate(long id, int x, int y, double rotation);

}
