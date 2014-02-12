package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.math.Vector3f;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
public class GUIEventManager implements GUIObserverInterface{
    
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    
    private static final Logger LOGGER =
            Logger.getLogger(GUIEventManager.class.getName());
    
    private WonderlandAdapterController ac = null;   
    private KMZImporter importer = null;
    
    
    /**
     * Creates a new clientUpdate instance.
     * 
     * @param ac a adpater controller instance.
     */
    public GUIEventManager(WonderlandAdapterController ac){
        this.ac = ac;
        importer = new KMZImporter();
    }

    @Override
    public void notifyTranslationXY(long id, int x, int y ) throws Exception{
        CellCache cache = ac.sm.getCellCache();
        
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            throw new GUIEventException();
        }
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        
        if(cell == null)
            throw new GUIEventException();
        
        Vector3fInfo coords = CellInfoReader.getCoordinates(cell);
        
        Vector3f translation = ac.ct.transformCoordinatesBack(cell, (float)x, (float)y, coords.z);
        ac.sc.translate(id, translation);
    }
    
    @Override
    public void notifyRotation(long id, int x, int y, double rotation) throws Exception{
        notifyTranslationXY(id,x,y);
        
        CellCache cache = ac.sm.getCellCache();
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            throw new GUIEventException();
        }
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        Vector3f cell_rot = CellInfoReader.getRotation(cell);
        cell_rot = ac.ct.createRotation(cell_rot, rotation);
        
        ac.sc.rotate(id, cell_rot);
    }

    @Override
    public void notifyScaling(long id, int x, int y, double scale) throws Exception{
        ac.sc.scale(id,(float) scale);
        notifyTranslationXY(id,x,y);
    }
    
    @Override
    public void notifyRemoval(long id) throws Exception{
        ac.sc.remove(id);
    }

    @Override
    public void notifyCopy(ArrayList<Long> object_ids) {
        
        ac.bm.clearCopy();
        
        for(long id : object_ids){
            ac.bm.addCopy(id);
        }
    }

    @Override
    public void notifyPaste(long id, int x, int y) throws Exception{
        CellCache cache = ac.sm.getCellCache();
        
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            throw new GUIEventException();
        }
        
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        
        if(cell == null){
            cell = ac.bm.getCell(id);
        }
        if(cell == null){
            throw new GUIEventException();
        }
        
        Vector3f coordinates = CellInfoReader.getCoordinates(cell);
        coordinates.x = x;
        coordinates.y = y;
        
        String name = cell.getName();
        name = ac.cnm.createCopyName(ac.sm, id, name);
        
        Vector3fInfo coords = CellInfoReader.getCoordinates(cell);
        coordinates = ac.ct.transformCoordinatesBack(cell, (float)x, 
                (float)y, coords.z);
        
        //Do not change order of this, or this could lead to some problems
        //if the server is faster than the addTranslation command.
        ac.ltm.addTranslation(name, coordinates);
        ac.sc.paste(cell, name);
        
    }

    @Override
    public void undoRemoval(long id) throws Exception{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void undoObjectCreation() throws Exception{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void redoObjectCreation() throws Exception{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int[] loadKMZ(String url) throws Exception{
        importer.importKMZ(url);
        return ac.ct.transformSize(importer.getModelSize());
    }
    
    @Override
    public boolean importCheckName(String moduleName, String server) 
            throws Exception{
        return importer.checkName(moduleName, server);
    }

    @Override
    public void importKMZ(String name, String image_url, double x, double y, 
            double z, double rotationX, double rotationY, double rotationZ, 
            double scale) throws Exception{
        
        BufferedImage img = null;
        
        try {
            img = ImageIO.read(new File(image_url));
        } catch (IOException ex) {
            LOGGER.log(Level.INFO, "Cannot read image file");
        }
        
        if(!importer.importToServer(name)){
            LOGGER.warning("Import to server failed.");
            throw new GUIEventException();
        }
        
        long id = importer.getLastID();
        if(id == -1){
            LOGGER.warning("No id was generated!");
            throw new GUIEventException();
        }
        
        //Remember: z and y are reversed
        Vector3f translate = new Vector3f((float)x, (float)z, (float)y);
        Vector3f rotate = new Vector3f((float)rotationX, (float)rotationZ,
                (float)rotationY);
        
        try{
            ac.sc.translate(id, translate);
        }catch(ServerCommException e){
            ac.ltm.addTranslation(id, translate);
        }
        
        try{
            ac.sc.rotate(id, rotate);
        }catch(ServerCommException e){
            ac.ltm.addRotation(id, rotate);
        }
            
        try{
            ac.sc.scale(id, (float)scale);
        }catch(ServerCommException e){
            ac.ltm.addScale(id, (float) scale);
        }
        
        if(img != null){
            try{
                ac.sc.addImage(id, img);
            }catch(ServerCommException e){
                ac.ltm.addImage(id, img);
            }
        }
        
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
