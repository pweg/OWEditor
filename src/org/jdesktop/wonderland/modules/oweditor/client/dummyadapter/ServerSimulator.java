package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import java.util.ArrayList;

/**
 * This is a simple class which simulates the server objects.
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
		
		createObject(0, 160, 160, 0, 0, 1, 70, 70, "Chair");
        createObject(2, 260, 260, 0, 0, 1, 80, 80, "ChairofTestinghugelength");
        createObject(1, 400, 400, 0, 0, 1, 200, 200, "Desk");
        createObject(3, 0, 0, 0, 0, 1, 10, 10, "Tiny");
        createObject(4, 100, 100, 0, 0, 1, 150, 25, "TinyVeryLong");
	}
	
	public void createObject(int id, int x, int y, int z, double rotation, 
			double scale, int width, int height, String name){
		ServerObject o = new ServerObject(id, x, y, z, rotation, 
				scale, width, height, name);
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
