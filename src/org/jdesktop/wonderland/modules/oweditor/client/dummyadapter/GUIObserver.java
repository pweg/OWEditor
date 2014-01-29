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
public class GUIObserver implements GUIObserverInterface{
    
    private DummyAdapterController ac = null;
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    
    
    //dummy bounds for new objects
    private float[] bounds = new float[2];
    
    
    /**
     * Creates a new clientUpdate instance.
     * 
     * @param ac a adpater controller instance.
     */
    public GUIObserver(DummyAdapterController ac){
        this.ac = ac;
        
        bounds[0] = (float) 1.0;
        bounds[1] = (float) 1.0;
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
    public int[] loadKMZ(String url) {
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
            double scale) {
        
        ServerObject object = ac.ses.getObject(name);
        
        if(object != null){
            return object.id;
        }else{
            BufferedImage img = null;
            
            try {
                img = ImageIO.read(new File(image_url));
                
            } catch (IOException e) {
                System.err.println("Reading image was not possible");
            }
            ServerObject tmp = ac.ses.createObject(0, (float)x, (float)y, (float)z, 
                rotationX, rotationY, rotationZ, scale, 
                (float)bounds[0], (float)bounds[1], name, false, img);
            ac.sua.createObject(tmp);
            return -1;
        }
    }

    @Override
    public void notifyCopy(long id, String image_url, double x, double y,
            double z, double rot_x, double rot_y, double rot_z, double scale) {
        
        ServerObject o = ac.ses.getObject(id);

        BufferedImage img = null;
        
        try {
            img = ImageIO.read(new File(image_url));
            
        } catch (IOException e) {
            System.err.println("Reading image was not possible");
        }
        
        if(o == null)
            return;
        
        String name = BUNDLE.getString("CopyName")+o.name;
        ServerObject clone = ac.ses.copyObject(id, name);
        clone.x=(float) x;
        clone.y=(float) y;
        clone.z=(float) z;
        clone.rotationX=rot_x;
        clone.rotationY=rot_y;
        clone.rotationZ=rot_z;
        clone.scale=scale;
        clone.image=img;
        ac.sua.createObject(clone);
    }

    @Override
    public void notifyOverwrite(long id, String image_url, double x, double y,
            double z, double rot_x, double rot_y, double rot_z, double scale) {
        //No modules are used in this dummy server,
        //so simple copy will suffice.
        
        notifyCopy(id,image_url, x, y,z, rot_x, rot_y, rot_z, scale);
        
    }

}
