package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.AdapterSettings;

/**
 * This class is used to make updates the client makes in
 * the GUI and forwards it to the server. 
 * 
 * @author Patrick
 *
 */
public class GUIObserver implements GUIObserverInterface{
    
    private DummyAdapterController ac = null;
    private int initialScale = AdapterSettings.initalScale;
    
    /**
     * Creates a new clientUpdate instance.
     * 
     * @param ac a adpater controller instance.
     */
    public GUIObserver(DummyAdapterController ac){
        this.ac = ac;
    }

    @Override
    public void notifyTranslation(long id, int x, int y, int z) {

        ServerObject object = ac.ses.getObject(id);
        if(object == null)
            return;

        object.x = (float)x/initialScale;
        object.y = (float)y/initialScale;
        object.z = (float)z/initialScale;        
        
        ac.sua.serverTranslationChangeEvent(id);
    }

    @Override
    public void notifyRemoval(long id) {
        ac.sua.serverRemovalEvent(id);
    }

    @Override
    public void notifyCopy(long id, int x, int y, int z) {
        
        ServerObject o = ac.ses.getObject(id);
        if(o == null)
            return;
        String name = "Copy_Of_"+o.name;
        
        ac.sua.copyTranslation(name, (float)x/initialScale, (float)y/initialScale, (float)z/initialScale);
        
        ServerObject clone = ac.ses.copyObject(id, name);
        ac.sua.serverCopyEvent(clone);
    }

}
