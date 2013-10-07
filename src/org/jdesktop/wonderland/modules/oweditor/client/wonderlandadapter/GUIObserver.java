package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

/**
 * This class is used to make updates the client makes in
 * the GUI and forwards it to the server. 
 * 
 * @author Patrick
 *
 */
public class GUIObserver implements GUIObserverInterface{
    
    private AdapterController ac = null;
    
    /**
     * Creates a new clientUpdate instance.
     * 
     * @param ac a adpater controller instance.
     */
    public GUIObserver(AdapterController ac){
        this.ac = ac;
    }

    @Override
    public void notifyTranslation(long id, int x, int y, int z) {

        /*ServerObject object = ac.ses.getObject(id);
        if(object == null)
        	return;
        
        object.x = x;
        object.y = y;
        object.z = z;
        
        
        ac.sua.serverChangeEvent(id);*/
        
    }

}
