package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This is a simple class which simulates the server objects and
 * will only be used for initialization of the objects.
 * 
 * @author Patrick
 *
 */
public class ServerSimulator {
    
    private ArrayList<ServerObject> objects = null;
    
    public long currentID = 0;
    
    public ServerEventManager sua = null;
    public String name = "DummyServer";
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    public String everybody = BUNDLE.getString("Everybody");
    public String user = BUNDLE.getString("User");
    public String group = BUNDLE.getString("Group");
    
    public ServerSimulator(){
        objects = new ArrayList<ServerObject> ();
        
        initializeObjects();
    }
    
    public void registerServerUpdate(ServerEventManager sua){
        this.sua = sua;
    }
    
    private void initializeObjects(){
        
        ServerObject so;
        so = createObject( 
                (float)0.0, (float)3.4142137,(float)1.0000589, 
                42, 150,58,
                1, 
                (float)1.00, (float)4.00, 
                "Block1", false);
        so = createObject( 
                (float)-9.0, (float)3.4142137,(float)1.0000589, 
                10, 15,10,
                1.3, 
                (float)3.00, (float)3.00, 
                "5", false);
        so = createObject(
                (float)-8.178859,  (float)-5.4412746, (float)0.060006, 
                10, 50,90,
                0.8, 
                (float)2.00, (float)0.20, "Virtuelles Telefon", false);
        so.rights.add(new Rights(user, "U1", true, true,true,true,true,
                false,false));
        so.rights.add(new Rights(everybody, everybody, true, true,true,true,true,
                true,true));
        so = createObject( 
                (float)-4.331105,  (float)-12.422435, (float)1.4452857,
                0, 0,0,
                5, 
                (float)0.50, (float)0.50, 
                "CIRCLE 12", false);
        so.isCircle = true;
        so = createObject(
                (float)-7.0,(float)-10.0, (float)-1.0,  
                0, 0,0,
                1, 
                (float)1.00, (float)1.00, 
                "Audiorekorder", false);
        so.rights.add(new Rights(user, "U1", true, true,true,true,true,
                false,false));
        so.rights.add(new Rights(everybody, everybody, true, true,true,true,true,
                true,true));
        so.rights.add(new Rights(group, "G1", true, true,true,true,true,
                true,false));
        so.rights.add(new Rights(user, "U2", true, false,false,true,false,
                true,false));
        so = createObject(
                (float)-20, (float)2.0,(float)0.0,  
                45, 180,0,
                1.3, 
                (float)1.80, (float)1.80, 
                "Circle long name long", false);
        so.isCircle = true;
        so = createObject(
                (float)-20, (float)2.0,(float)0.0,  
                54, 180,0,
                1.3, 
                (float)1.80, (float)1.80, 
                "Block 3", false);
        so = createObject( 
                (float)0.0, (float)0.0, (float)4.0, 
                0, 20,90,
                1, 
                (float)1.00, (float)2.00, 
                "nothing", false);
        so = createObject(
                (float)2.9455893, (float)1.7263716,(float)1.000591,  
                20,10,0, 
                1, 
                (float)3.00, (float)3.00, 
                "Block2", false);

        so = createObject(
                (float)-2.9455893, (float)0.7263716,(float)2.000591, 
                0, 0,0,
                1, 
                (float)500, (float)2000, 
                "verylongnamehasthisblockfortestingBlock2", false);
        
        so = createObject(
                (float)-6.0, (float)-11.0,(float)1.0,  
                20,10,0, 
                1, 
                (float)3.00, (float)3.00, 
                "Avatar", true);
    }

    public ServerObject createObject(
            float x, float y, float z, 
            double rotationX, double rotationY, double rotationZ, 
            double scale,
            float width, float height, 
            String name,
            boolean isAvatar){
        ServerObject o = new ServerObject(currentID, x, y, z, rotationX, 
                rotationY, rotationZ,
                scale, width, height, name);

        o.rotationY = rotationY;
        o.rotationZ = rotationZ;
        o.isAvatar = isAvatar;
        objects.add(o);
        currentID++;
        
        return o;
    }

    public ServerObject createObject( 
            float x, float y, float z, 
            double rotationX, double rotationY, double rotationZ, 
            double scale,
            float width, float height, 
            String name,
            boolean isAvatar, 
            BufferedImage image,
            String imgName){
        ServerObject o = new ServerObject(currentID, x, y, z, rotationX, 
                rotationY, rotationZ,
                scale, width, height, name, image, imgName, false);

        o.rotationY = rotationY;
        o.rotationZ = rotationZ;
        o.isAvatar = isAvatar;
        objects.add(o);
        currentID++;
        
        return o;
    }
    
    public ServerObject getObject(long id){
        for(ServerObject object : objects){
            if(id == object.id)
                return object;
        }
        return null;
    }
    
    public ServerObject getObject(String name){
        for(ServerObject object: objects){
            if(name.equals(object.name)){
                return object;
            }
        }
        return null;
    }
    
    public ServerObject copyObject(long id, String name){
        ServerObject object = getObject(id);
        
        ServerObject clone = object.clone();
        
        clone.id = currentID;
        currentID++;
        clone.name = name;
        
        objects.add(clone);
        
        return clone;
    }
    
    public ArrayList<ServerObject> getObjects(){
        return objects;
    }
    
    public void addObject(ServerObject so){
        objects.add(so);
        sua.createObject(so);
    }
    

}
