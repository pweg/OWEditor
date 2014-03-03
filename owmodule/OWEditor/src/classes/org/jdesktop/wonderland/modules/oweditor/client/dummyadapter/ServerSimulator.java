package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
    
    public ServerSimulator(){
        objects = new ArrayList<ServerObject> ();
        
        initializeObjects();
    }
    
    public void registerServerUpdate(ServerEventManager sua){
        this.sua = sua;
    }
    
    private void initializeObjects(){
        /*
        Sphere name: 5, 1.00001 0.0 1.0000589 3.4142137
        
        WARNING 10:40:39 org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.WorldBuilder createDataObject
        Sphere Virtuelles Telefon 1.00001 -8.178859 0.060000006 -5.4412746
        
        WARNING 10:40:39 org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.WorldBuilder createDataObject
        Sphere name: 12, 1.00001 -4.331105 1.4452587 -12.422435
        
        WARNING 10:40:39 org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.WorldBuilder createDataObject
        Sphere name: Audiorekorder 1.00001 -7.0 -1.0 -10.0
        
        WARNING 10:40:39 org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.WorldBuilder createDataObject
        Sphere Block3 1.80001 -20.0 0.0 2.0
        
        WARNING 10:40:39 org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.WorldBuilder createDataObject
        Sphere 1.00001 0.0 0.0 0.0
        
        WARNING 10:40:39 org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.WorldBuilder createDataObject
        Sphere Block2, 1.00001 2.9455893 1.0000591 1.7263716
        */
        

        createObject( 
                (float)0.0, (float)3.4142137,(float)1.0000589, 
                42, 150,58,
                1, 
                (float)1.00, (float)4.00, 
                "Block1", false);
        createObject( 
                (float)-9.0, (float)3.4142137,(float)1.0000589, 
                10, 15,10,
                1.3, 
                (float)3.00, (float)3.00, 
                "5", false);
        createObject(
                (float)-8.178859,  (float)-5.4412746, (float)0.060006, 
                90, 50,10,
                0.8, 
                (float)2.00, (float)0.20, "Virtuelles Telefon", false);
        createObject( 
                (float)-4.331105,  (float)-12.422435, (float)1.4452857,
                180, 0,0,
                1, 
                (float)1.00, (float)1.00, 
                "12", true);
        createObject(
                (float)-7.0,(float)-10.0, (float)-1.0,  
                0, 0,0,
                1, 
                (float)1.00, (float)1.00, 
                "Audiorekorder", false);
        createObject(
                (float)-20, (float)2.0,(float)0.0,  
                0, 180,355,
                1.3, 
                (float)1.80, (float)1.80, 
                "Block 3", false);
        createObject( 
                (float)0.0, (float)0.0, (float)0.0, 
                0, 20,90,
                1, 
                (float)1.00, (float)2.00, 
                "nothing", false);
        createObject(
                (float)2.9455893, (float)1.7263716,(float)1.000591,  
                0,10,20, 
                1, 
                (float)3.00, (float)3.00, 
                "Block2", false);

        createObject(
                (float)-2.9455893, (float)0.7263716,(float)1.000591, 
                0, 0,0,
                1, 
                (float)0.50, (float)2.00, 
                "Block2", false);
        
        
        /*
        createObject(0, 160, 160, 0, 0, 1, 70, 70, "Chair");
        createObject(2, 260, 260, 0, 0, 1, 80, 80, "ChairofTestinghugelength");
        createObject(1, 400, 400, 0, 0, 1, 200, 200, "Desk");
        createObject(3, 0, 0, 0, 0, 1, 10, 10, "Tiny");
        createObject(4, 100, 100, 0, 0, 1, 150, 25, "TinyVeryLong");*/
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
            BufferedImage image){
        ServerObject o = new ServerObject(currentID, x, y, z, rotationX, 
                rotationY, rotationZ,
                scale, width, height, name, image);

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
