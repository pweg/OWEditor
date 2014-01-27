package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import java.util.ArrayList;
import java.util.ResourceBundle;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

/**
 * This class is used to make updates the client makes in
 * the GUI and forwards it to the server. 
 * 
 * @author Patrick
 *
 */
public class GUIObserver implements GUIObserverInterface{
    
    private DummyAdapterController ac = null;
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    
    private ServerObject tmp = null;
    private int count = 1;
    private int substract = 2;
    
    
    /**
     * Creates a new clientUpdate instance.
     * 
     * @param ac a adpater controller instance.
     */
    public GUIObserver(DummyAdapterController ac){
        this.ac = ac;
    }

    @Override
    public void notifyTranslation(long id, int x, int y) {

        ServerObject object = ac.ses.getObject(id);
        if(object == null)
            return ;

        Vector3D p = ac.ct.transformCoordinatesBack(x, y, 0, 0);
        
        object.x = p.x;
        object.y = p.y;      
        
        ac.sua.serverTranslationChangeEvent(id);
    }

    @Override
    public void notifyRemoval(long id) {
        ac.sua.serverRemovalEvent(id);
    }

    @Override
    public void notifyCopy(ArrayList<Long> object_ids) {
        
        /*
         * This can lead to problems, when the server is not fast enough, when
         * paste is made in the gui and directly afterwards a copy.
         */
        ac.bom.clearBackup();
        
        for(long id : object_ids){
            ServerObject object = ac.ses.getObject(id);
            ac.bom.addObject(object);
        }
    }

    @Override
    public void notifyPaste(long id, int x, int y) {
        
        ServerObject o = ac.ses.getObject(id);
        if(o == null)
            return;
        String name = BUNDLE.getString("CopyName")+o.name;
        
        Vector3D p = ac.ct.transformCoordinatesBack(x, y, 0, 0);
        
        ac.bom.addTranslation(id, name, p.x, p.y, 0);
        
        ServerObject clone = ac.ses.copyObject(id, name);
        ac.sua.serverCopyEvent(clone);
    }

    @Override
    public void notifyRotation(long id, int x, int y, double rotation) {
        ServerObject object = ac.ses.getObject(id);
        
        if(object == null)
            return ;
   
        object.rotationX = rotation;
        notifyTranslation(id, x,y);
        ac.sua.serverRotationEvent(id);
    }

    @Override
    public void notifyScaling(long id, int x, int y, double scale) {
        ServerObject object = ac.ses.getObject(id);
        
        if(object == null)
            return ;
        
        object.scale = scale;        
        notifyTranslation(id, x,y);
        ac.sua.serverScalingEvent(id);
    }

    @Override
    public void notifyCreation() {
        
        if(tmp == null){
            tmp = new ServerObject(596, (float)-12, (float)-14, 0, 1, 
                    0, (float)1.00, (float)1.00, "TMP");
            tmp.isAvatar = false;
        }else{
            count++;
            tmp.name = "TMP" + count;
            tmp.id++;
            tmp.y-=substract;
        }
        ac.ses.addObject(tmp);
        
    }

    @Override
    public int[] loadKMZ(String url) {
        /*
         * unfortunately reading a kmz is difficult, so the
         * dummy adapter returns just the same sizes.
         */
        
        int[] bounds = new int[2];
        bounds[0] = 10;
        bounds[1] = 15;
        
        
        return bounds;
    }

}
