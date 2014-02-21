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
        
        bounds[0] = (float) 1.0;
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
        
        ac.da.serverTranslationChangeEvent(id);
    }

    @Override
    public void notifyRemoval(long id) throws Exception{
        ac.da.serverRemovalEvent(id);
    }
    
    @Override
    public void undoRemoval(long id) throws Exception{
        ac.da.createObject(ac.server.getObject(id));
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
        ac.da.serverCopyEvent(clone);
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
        
        ac.da.createObject(ac.server.getObject(id));
    }

    @Override
    public void notifyRotation(long id, int x, int y, double rotation) throws Exception{
        ServerObject object = ac.server.getObject(id);
        
        if(object == null)
            throw new Exception();
   
        object.rotationX = rotation;
        notifyTranslationXY(id, x,y);
        ac.da.serverRotationEvent(id);
    }

    @Override
    public void notifyScaling(long id, int x, int y, double scale) throws Exception{
        ServerObject object = ac.server.getObject(id);
        
        if(object == null)
            throw new Exception();
        
        object.scale = scale;        
        notifyTranslationXY(id, x,y);
        ac.da.serverScalingEvent(id);
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
    public long importKMZ(String name, String image_url, double x, double y,
            double z, double rotationX, double rotationY, double rotationZ,
            double scale) throws Exception{
        
        BufferedImage img = null;
        
        try {
            img = ImageIO.read(new File(image_url));
            
        } catch (IOException e) {
            System.err.println("Reading image was not possible");
        }

        ServerObject tmp = ac.server.createObject((float)x, (float)y, (float)z, 
            rotationX, rotationY, rotationZ, scale, 
            (float)bounds[0], (float)bounds[1], name, false, img);
        ac.bom.addCreatedID(tmp.id);
        ac.da.createObject(tmp);
        
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

    @Override
    public void undoDeletion(long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
