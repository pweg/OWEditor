package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

/**
 * This class is used to make updates the client makes in
 * the GUI and forwards it to the server. 
 * 
 * @author Patrick
 *
 */
public class GUIEventManager implements GUIObserverInterface{
    
    private DummyAdapterController ac = null;
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    
    //This server object is used for import conflict resolution
    
    //dummy bounds for new objects
    private float[] bounds = new float[2];
    
    
    /**
     * Creates a new clientUpdate instance.
     * 
     * @param ac a adpater controller instance.
     */
    public GUIEventManager(DummyAdapterController ac){
        this.ac = ac;
        
        bounds[0] = (float) 2.0;
        bounds[1] = (float) 1.0;
    }

    @Override
    public void notifyTranslationXY(long id, int x, int y) throws Exception{
        ServerObject object = ac.server.getObject(id);
        if(object == null)
            throw new Exception();

        Vector3D p = ac.ct.transformCoordinatesBack(x, y, 0, 0);
        
        object.x = p.x;
        object.y = p.y;      
        
        ac.sem.serverTranslationChangeEvent(id);
    }


    @Override
    public void notifyTranslation(Long id, double x, double y, double z)
            throws Exception {
        ServerObject object = ac.server.getObject(id);
        if(object == null)
            throw new Exception();
        
        object.x = (float)x;
        object.y = (float)y;
        object.z = (float)z;
        
        ac.sem.serverTranslationChangeEvent(id);
    }

    @Override
    public void notifyRemoval(long id) throws Exception{
        ac.sem.serverRemovalEvent(id);
    }
    
    @Override
    public void undoRemoval(long id) throws Exception{
        ac.sem.createObject(ac.server.getObject(id));
    }

    @Override
    public void notifyCopy(ArrayList<Long> object_ids) {
        
        /*
         * This can lead to problems, when the server is not fast enough, when
         * paste is made in the gui and directly afterwards a copy.
         */
        ac.bom.clearBackup();
        
        for(long id : object_ids){
            ServerObject object = ac.server.getObject(id);
            ac.bom.addObject(object);
        }
    }

    @Override
    public void notifyPaste(long id, int x, int y) throws Exception{
        
        ServerObject o = ac.server.getObject(id);
        if(o == null)
            throw new Exception();
        String name = BUNDLE.getString("CopyName")+o.name;
        
        Vector3D p = ac.ct.transformCoordinatesBack(x, y, 0, 0);
        
        ac.bom.addTranslation(id, name, p.x, p.y, 0);
        
        ServerObject clone = ac.server.copyObject(id, name);
        ac.bom.addCreatedID(clone.id);
        ac.sem.serverCopyEvent(clone);
    }

    @Override
    public void undoObjectCreation() throws Exception{
        long id = ac.bom.getCreationUndoID();

        if(id == -1)
            throw new Exception();
        
        notifyRemoval(id);
    }
    
    @Override
    public void redoObjectCreation() throws Exception{
        long id = ac.bom.getCreationRedoID();
        
        if(id == -1)
            throw new Exception();
        
        ac.sem.createObject(ac.server.getObject(id));
    }


    @Override
    public void notifyRotation(long id, double rot_x, double rot_y, double rot_z)
            throws Exception {
        
        ServerObject object = ac.server.getObject(id);
        
        if(object == null)
            throw new Exception();
        object.rotationX = rot_x;
        object.rotationY = rot_y;
        object.rotationZ = rot_z;
        
        ac.sem.serverRotationEvent(id);
        
    }

    @Override
    public void notifyScaling(long id, double scale) throws Exception {
        ServerObject object = ac.server.getObject(id);
        
        if(object == null)
            throw new Exception();
        
        object.scale = scale;
        ac.sem.serverScalingEvent(id);
    }
    @Override
    public void notifyImageChange(long id, String dir, String name) throws Exception{
        ServerObject object = ac.server.getObject(id);
        
        if(object == null)
            throw new Exception();
        
        BufferedImage img = ac.im.getImage(dir, name);
        
        ac.sem.serverImageChangeEvent(id, img, dir, name);
        
        
    }

    @Override
    public void notifyNameChange(Long id, String name) throws Exception{
        ServerObject object = ac.server.getObject(id);
        
        if(object == null)
            throw new Exception();
        
        object.name = name;
        
        ac.sem.serverNameChange(id);
        
    }

    @Override
    public int[] loadKMZ(String url) throws Exception{
        /*
         * unfortunately reading a kmz is difficult, so the
         * dummy adapter returns just the same sizes.
         */
        
        int[] transformed = new int [bounds.length];
        
        transformed[0] = ac.ct.transformWidth(bounds[0]);
        transformed[1] = ac.ct.transformHeight(bounds[1]);        
        
        return transformed;
    }

    @Override
    public long importKMZ(String name, String module_name, 
            String imgName, double x, double y,
            double z, double rotationX, double rotationY, double rotationZ,
            double scale) throws Exception{
        
        BufferedImage img = null;
        
        if(imgName != null){
            img = ac.im.getImage(AdapterSettings.IMAGEDIR, imgName);
        }

        ServerObject tmp = ac.server.createObject((float)x, (float)y, (float)z, 
            rotationX, rotationY, rotationZ, scale, 
            (float)bounds[0], (float)bounds[1], name, false, img, imgName);
        ac.bom.addCreatedID(tmp.id);
        ac.sem.createObject(tmp);
        
        return tmp.id;
    }    

    @Override
    public boolean importCheckName(String moduleName, String server) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cancelImport() {
        //There is nothing to do here.
    }

    public boolean imageFileExists(String name) {
        return ac.im.imageExists(AdapterSettings.IMAGEDIR, name);
    }

    @Override
    public void uploadImage(String imageUrl) {
        
        BufferedImage img = null;
        String imgName = "";
        
        if(imageUrl != null){
            try {
                File imgFile = new File(imageUrl);
                img = ImageIO.read(imgFile);
                imgName = imgFile.getName();
                ac.im.addImage(AdapterSettings.IMAGEDIR, imgName, img);
            } catch (IOException e) {
                System.err.println("Reading image was not possible");
            }
        }
        if(img != null)
            ac.sem.updateImgLib(img, imgName);
    }

    @Override
    public void notifyComponentCreation(long id, byte component) {
        switch(component){
            case 1:
                ac.sem.addRightsComponent(id);
                break;
        }
        
    }

    @Override
    public void notifyComponentRemoval(long id, byte component) {
        switch(component){
            case 1:
                ac.sem.removeRightsComponent(id);
                break;
        }
    }

    @Override
    public void setRight(long id, String oldType, String oldName,
            String type, String name, boolean owner,
            boolean addSubObjects, boolean changeAbilities, boolean move,
            boolean view) {
        ac.sem.notifyRightsChange(id, oldType, oldName,
                type, name, owner, addSubObjects, changeAbilities, move, view);
    }

    @Override
    public void removeRight(long id, String type, String name) {
        ac.sem.notifyRightsRemoval(id, type, name);
    }

}
