package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.math.Vector3f;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellCache;
import org.jdesktop.wonderland.common.cell.CellID;
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
        CellCache cache = ac.sm.getCellCache();
        
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            return;
        }
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        
        if(cell == null)
            return;
        
        Vector3fInfo coords = CellInfoReader.getCoordinates(cell);
        
        Vector3f translation = ac.ct.transformCoordinatesBack(cell, (float)x, (float)y, coords.z);
        ac.sc.translate(id, translation);
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
            LOGGER.warning("Import to server failed.");
            return false;
        }
        
        long id = importer.getLastID();
        if(id == -1){
            LOGGER.warning("No id was generated!");
            return false;
        }
        
        //Remember: z and y are reversed
        Vector3f translate = new Vector3f((float)x, (float)z, (float)y);
        if(!ac.sc.translate(id, translate)){
            LOGGER.warning("TRANalslla");
            ac.ltm.putTranslation(id, translate);
        }
        
        Vector3f rotate = new Vector3f((float)rotationX, (float)rotationZ,
                (float)rotationY);
        if(!ac.sc.rotate(id, rotate)){
            LOGGER.warning("TRANalslla2");
            ac.ltm.putRotation(id, rotate);
        }
            
        if(!ac.sc.scale(id, (float)scale)){
            
            LOGGER.warning("TRANalslla3");
            ac.ltm.putScale(id, (float) scale);
        }
        
        
        return true;
    }

    /*public void importConflictCopy(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void importConflictOverwrite(long id) {
        importer.deployToServer();
    }*/

    public void cancelImport() {
        importer.clearModel();
    }
}
