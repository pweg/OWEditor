package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.math.Vector3f;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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

    @Override
    public void notifyScaling(long id, int x, int y, double scale) {
        ac.sc.scale(id, x, y, scale);
    }

    @Override
    public void undoRemoval(long id) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void undoObjectCreation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void redoObjectCreation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int[] loadKMZ(String url) {
        importer.importKMZ(url);
        return importer.getModelSize(ac.ct);
    }
    
    @Override
    public boolean importCheckName(String moduleName, String server){
        return importer.checkName(moduleName, server);
    }

    @Override
    public boolean importKMZ(String name, String image_url, double x, double y, 
            double z, double rotationX, double rotationY, double rotationZ, 
            double scale){
        
        BufferedImage img = null;
        
        try {
            img = ImageIO.read(new File(image_url));
        } catch (IOException ex) {
            Logger.getLogger(GUIObserver.class.getName()).log(Level.INFO, 
                    "Cannot read image file");
        }
        
        if(!importer.importToServer(name, image_url, x,y,z, 
                rotationX, rotationY, 
                rotationZ, scale)){
            LOGGER.warning("NIX herer GESCHAFFT ODER WAT?");
            return false;
        }
        
        
        
        long id = importer.getLastID();
        if(id == -1){
            LOGGER.warning("RALALAALALA");
            return false;
        }
        //
        
        if(!ac.sc.translate(id, (float)x, (float)y, (float)z)){
            ac.bm.addTranslation(id, name, new Vector3f((float)x, (float)y, (float)z));
        }
        
        
        
        return true;
    }

    /*public void importConflictCopy(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void importConflictOverwrite(long id) {
        importer.deployToServer();
    }*/
}
