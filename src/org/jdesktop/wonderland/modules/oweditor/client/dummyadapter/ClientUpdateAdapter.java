package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.ClientUpdateGUIInterface;

/**
 * This class is used to make updates the client makes in
 * the GUI and forwards it to the server. 
 * 
 * @author Patrick
 *
 */
public class ClientUpdateAdapter implements ClientUpdateGUIInterface{
    
    private AdapterController ac = null;
    
    /**
     * Creates a new clientUpdate instance.
     * 
     * @param ac a adpater controller instance.
     */
    public ClientUpdateAdapter(AdapterController ac){
        this.ac = ac;
    }

    @Override
    public void updateTranslation(long id, int x, int y, int z) {
        ac.sua.serverChangeEvent(id, x, y, 0, 0, 1, "");
        
    }

}
