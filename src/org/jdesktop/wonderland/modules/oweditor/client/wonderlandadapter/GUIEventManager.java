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
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IWaitingDialog;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.snapshots.WorldExporter;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.snapshots.WorldImporter;
import org.jdesktop.wonderland.modules.security.client.SecurityQueryComponent;

/**
 * This class is used to receive updates the client does in
 * the GUI and forwards it to the server. 
 * 
 * @author Patrick
 *
 */
public class GUIEventManager implements GUIObserverInterface{
    
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
        id = ac.bm.getActiveID(id);
        Cell cell = ac.sc.getCell(id);
        
        if(cell == null)
            throw new GUIEventException();
        
        Vector3fInfo coords = CellInfoReader.getCoordinates(cell);
        
        Vector3f translation = ac.ct.transformCoordinatesBack(cell, (float)x, (float)y, coords.z);
        ac.sc.translate(id, translation);
    }
    
    @Override
    public void notifyTranslation(Long id, double x, double y, double z) throws Exception {
        id = ac.bm.getActiveID(id);
        Vector3f translation = new Vector3f((float) x,(float) z,(float) y);
        ac.sc.translate(id, translation);
    }
    
    @Override
    public void notifyRotation(long id, double rot_x, double rot_y, double rot_z) throws Exception {      
        id = ac.bm.getActiveID(id);
        Vector3f cell_rot = new Vector3f((float) Math.toRadians(-rot_y),
                (float) Math.toRadians(-rot_x),
                (float) Math.toRadians(-rot_z));
        ac.sc.rotate(id, cell_rot);
    }

    @Override
    public void notifyScaling(long id, double scale) throws Exception {
        id = ac.bm.getActiveID(id);
        ac.sc.scale(id,(float) scale);
    }
    
    @Override
    public void notifyRemoval(long id) throws Exception{
        long curid = ac.bm.getActiveID(id);
        //tires to add an id component, in case the cell gets restored.
        try{
            ac.sc.addIDComponent(curid, id);
        }catch(Exception e){
            LOGGER.log(Level.WARNING, "ID component not created for {0}", id);
        }
        ac.sc.remove(curid);
    }

    @Override
    public void notifyCopy(ArrayList<Long> object_ids) {
        
        ac.bm.clearCopy();
        
        for(long id : object_ids){
            id = ac.bm.getActiveID(id);
            ac.bm.addCopy(id);
        }
    }

    @Override
    public void notifyPaste(long id, int x, int y) throws Exception{
        id = ac.bm.getActiveID(id);
        CellCache cache = ac.sm.getCellCache();
        
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            throw new GUIEventException();
        }
        
        //get cell from the cell cache.
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        
        //if the cell was deleted, take the cell from the backup manager.
        if(cell == null){
            cell = ac.bm.getCell(id);
        }
        //if no cell was found, copy is not possible!
        if(cell == null){
            throw new GUIEventException();
        }
        
        Vector3f coordinates = CellInfoReader.getCoordinates(cell);
        coordinates.x = x;
        coordinates.y = y;
        
        String name = cell.getName();
        name = ac.cnm.createCopyName(ac.sm.getSession(), id, name);
        
        Vector3fInfo coords = CellInfoReader.getCoordinates(cell);
        coordinates = ac.ct.transformCoordinatesBack(cell, (float)x, 
                (float)y, coords.z);
        
        //Do not change order of this, or this could lead to some problems
        //if the server is faster than the addTranslation command.
        ac.ltm.addTranslation(name, coordinates);
        ac.bm.addCopyWhiteList(name);
        ac.sc.paste(cell, name);        
    }

    @Override
    public void undoRemoval(long id) throws Exception{
        
        //if the object currently exists, no udno removal is necessary.
        
        if(ac.bm.isActive(id)){
            LOGGER.warning("UNDOING active removal");
            throw new GUIEventException();
        }
        
        Cell cell = ac.bm.getActiveCell(id);
        
        if(cell == null)
                cell = ac.bm.getCell(id);
        
        if(cell == null){
            LOGGER.warning("UNDO REMVOAL FAILED");
            throw new GUIEventException();
        }
        
        Vector3f coordinates = CellInfoReader.getCoordinates(cell);
        String name = cell.getName() ;
        name = ac.cnm.createUndoName(ac.sm.getSession(), id, name);
        
        float y = coordinates.z;
        float z = coordinates.y;
        
        //swap coordinates in order to not get mixed up later.
        coordinates.y = y;
        coordinates.z = z;
        
        ac.ltm.addTranslation(name, coordinates);        
        ac.sc.paste(cell, name);
    }

    @Override
    public void undoObjectCreation() throws Exception{
        long id = ac.bm.getUndoID();
        
        if(id == -1)
            throw new ServerCommException();
        notifyRemoval(id);
    }

    @Override
    public void redoObjectCreation() throws Exception{
        long id = ac.bm.getRedoID();
        
        if(id == -1)
            throw new ServerCommException();
        
        undoRemoval(id);
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
    public long importKMZ(String name, String module_name, 
            String imgName, String imgDir, double x, double y, 
            double z, double rotationX, double rotationY, double rotationZ, 
            double scale) throws Exception{
        
        
        //Remember: z and y are reversed
        Vector3f translate = new Vector3f((float)x, (float)z, (float)y);
        Vector3f rotate = new Vector3f((float) Math.toRadians(-rotationY),
                (float) Math.toRadians(-rotationX),
                (float) Math.toRadians(-rotationZ));
        
        if(!importer.importToServer(module_name, name)){
            LOGGER.warning("Import to server failed.");
            throw new GUIEventException();
        }
        
        long id = importer.getLastID();
        if(id == -1){
            LOGGER.warning("No id was generated!");
            throw new GUIEventException();
        }
        
        if(imgName != null && !imgName.equals("")){
            ac.ltm.addImage(id, imgName, imgDir);
        }
            
        try{
            ac.sc.scale(id, (float)scale);
        }catch(ServerCommException e){
            ac.ltm.addScale(id, (float) scale);
        }
        
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
        
        return id;        
    }

    @Override
    public void cancelImport() {
        importer.clearModel();
    }    

    @Override
    public boolean imageFileExists(String name) {
        return ac.fm.imageFileExists(name);
    }
    
    @Override
    public void notifyImageChange(long id, String dir, String name) throws Exception {
        id = ac.bm.getActiveID(id);
         ac.sc.changeImage(id, name, dir);
    }

    @Override
    public void notifyNameChange(Long id, String name) throws Exception {
        id = ac.bm.getActiveID(id);
        ac.sc.changeName(id, name);
    }

    @Override
    public void uploadImage(String imageUrl) throws Exception{
        if(imageUrl != null && !imageUrl.equals("")){
            try{
                File imgFile = new File(imageUrl);
                BufferedImage img = ImageIO.read(imgFile);
                FileInfo info = ac.fm.uploadImage(imgFile);
                ac.sem.updateUserLib(img, info.fileName, ac.fm.getUserDir());
            } catch (IOException e) {
                throw new ServerCommException();
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, null, ex);
                throw new ServerCommException();
            }
        }
    }

    @Override
    public void notifyComponentCreation(long id, byte component) throws Exception{
        id = ac.bm.getActiveID(id);
         
        if(component == GUIObserverInterface.RIGHTSCOMPONENT){
            ac.sc.addSecurityComponent(id);
        }
    }

    @Override
    public void notifyComponentRemoval(long id, byte component) throws Exception{
        id = ac.bm.getActiveID(id);
         
        if(component == GUIObserverInterface.RIGHTSCOMPONENT){
            ac.sc.deleteComponent(id, SecurityQueryComponent.class);
        }
    }

    @Override
    public void setRight(long id, String oldType, String oldName, 
            String type, String name, boolean owner, 
            boolean addSubObjects, boolean changeAbilities, 
            boolean move, boolean view) throws Exception{
        id = ac.bm.getActiveID(id);
        
        ac.sc.setSecurity(id, ac.secm.changeRight(ac.sc.getCell(id),
                 oldName, type, name, owner,
                addSubObjects, changeAbilities, move, view));
    }

    @Override
    public void removeRight(long id, String type, String name) throws Exception{
        id = ac.bm.getActiveID(id);
        ac.sc.setSecurity(id,ac.secm.removeSecurity(ac.sc.getCell(id), name));
    }

    @Override
    public void updateObjects(long id) {
        id = ac.bm.getActiveID(id);
        try {
            ac.sem.setSecurity(ac.sc.getCell(id));
        } catch (ServerCommException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void loadWorld(String filepath, IWaitingDialog dialog) {
        LOGGER.warning(filepath);
        if(filepath == null)
            return;
        
        File file = new File(filepath);
        
        if(file.exists() && file.canRead())
            WorldImporter.getInstance().importFile(file, dialog);
    }

    @Override
    public void saveWorld(String filepath, IWaitingDialog dialog) {
        if(filepath == null)
            return;
        File file = new File(filepath); 
        WorldExporter.getInstance().exportAllCells(file, dialog);
    }
}
