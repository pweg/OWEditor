package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import java.util.ArrayList;
import java.util.logging.Logger;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

/**
 * This class is used to make updates the client makes in
 * the GUI and forwards it to the server. 
 * 
 * @author Patrick
 *
 */
public class GUIObserver implements GUIObserverInterface{
    
    private static final Logger LOGGER =
            Logger.getLogger(GUIObserver.class.getName());
    
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
    public void notifyTranslation(long id, int x, int y ){
        ac.sc.translate(id,x,y);
    }
    
    @Override
    public void notifyRemoval(long id) {
       ac.sc.remove(id);
    }

    @Override
    public void notifyCopy(ArrayList<Long> object_ids) {
        
        ac.bm.clearBackup();
        
        for(long id : object_ids){
            ac.bm.addObject(id);
        }
    }

    @Override
    public void notifyPaste(long id, int x, int y) {
        ac.sc.paste(id, x, y);
    }
    
    @Override
    public void notifyRotation(long id, int x, int y, double rotation) {
        ac.sc.rotate(id, x, y, rotation);
    }
}
