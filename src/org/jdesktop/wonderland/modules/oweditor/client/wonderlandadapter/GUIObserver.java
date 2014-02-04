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
    
    private WonderlandAdapterController ac = null;   
    private KMZImporter importer = null;
    
    /**
     * Creates a new clientUpdate instance.
     * 
     * @param ac a adpater controller instance.
     */
    public GUIObserver(WonderlandAdapterController ac){
        this.ac = ac;
        importer = new KMZImporter();
    }

    @Override
    public void notifyTranslationXY(long id, int x, int y ){
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

    public void notifyScaling(long id, int x, int y, double scale) {
        ac.sc.scale(id, x, y, scale);
    }

    public void undoRemoval(long id) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void undoObjectCreation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void redoObjectCreation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int[] loadKMZ(String url) {
        importer.importKMZ(url);
        return importer.getModelSize(ac.ct);
    }

    public long importKMZ(String name, String image_url, double x, double y, 
            double z, double rotationX, double rotationY, double rotationZ, 
            double scale){
        boolean ret_val = importer.setProperties(name, image_url, x,y,z, 
                rotationX, rotationY, 
                rotationZ, scale);
        if(ret_val == false)
            return 1;
        else
            return -1;
    }

    public void importConflictCopy(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void importConflictOverwrite(long id) {
        importer.deployToServer();
    }
}
