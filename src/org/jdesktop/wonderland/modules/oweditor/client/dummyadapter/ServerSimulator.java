package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

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
    
    public ServerSimulator(){
        objects = new ArrayList<ServerObject> ();
        
        initializeObjects();
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
        

        createObject(0, (float)0.0, (float)1.0000589, (float)3.4142137, 0, 0, 
                (float)1.00, (float)1.00, "Block1", false);
        createObject(1, (float)0.0, (float)1.0000589, (float)3.4142137, 0, 0, 
                (float)1.00, (float)1.00, "5", false);
        createObject(2, (float)-8.178859, (float)0.060006, (float)-5.4412746, 0, 0, 
                (float)1.00, (float)1.00, "Virtuelles Telefon", false);
        createObject(3, (float)-4.331105, (float)1.4452857, (float)-12.422435, 0, 0, 
                (float)1.00, (float)1.00, "12", true);
        createObject(4, (float)-7.0, (float)-1.0, (float)-10.0, 0, 0, 
                (float)1.00, (float)1.00, "Audiorekorder", false);
        createObject(5, (float)-20, (float)0.0, (float)2.0, 0, 0, 
                (float)1.80, (float)1.80, "Block 3", false);
        createObject(6, (float)0.0, (float)0.0, (float)0.0, 0, 0, 
                (float)1.00, (float)1.00, "nothing", false);
        createObject(7, (float)2.9455893, (float)1.000591, (float)1.7263716, 0, 0, 
                (float)1.00, (float)1.00, "Block2", false);
        
        /*
        createObject(0, 160, 160, 0, 0, 1, 70, 70, "Chair");
        createObject(2, 260, 260, 0, 0, 1, 80, 80, "ChairofTestinghugelength");
        createObject(1, 400, 400, 0, 0, 1, 200, 200, "Desk");
        createObject(3, 0, 0, 0, 0, 1, 10, 10, "Tiny");
        createObject(4, 100, 100, 0, 0, 1, 150, 25, "TinyVeryLong");*/
    }

    public void createObject(int id, float x, float y, float z, double rotation, 
            double scale, float width, float height, String name,
            boolean isAvatar){
        ServerObject o = new ServerObject(id, x, y, z, rotation, 
                scale, width, height, name);
        
        o.isAvatar = isAvatar;
        objects.add(o);
    }
    
    public ServerObject getObject(long id){
        for(ServerObject object : objects){
            if(id == object.id)
                return object;
        }
        return null;
    }
    
    public ArrayList<ServerObject> getObjects(){
        return objects;
    }

}
