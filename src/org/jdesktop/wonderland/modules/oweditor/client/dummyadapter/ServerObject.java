package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

/**
 * A simple server object class for testing purposes.
 * 
 * @author Patrick
 *
 */
public class ServerObject {
	
	protected int x = 0;
	protected int y = 0;
	protected int z = 0;
	protected double rotation = 0;
	protected double scale = 0;
	protected int width = 0;
	protected int height = 0;
	
	protected long id = 0;
	protected String name = "";
	
	ServerObject(int id, int x, int y, int z, double rotation, 
			double scale, int width, int height, String name){
		this.x = x;
		this.y = y;
		this.z = z;
		this.rotation = rotation;
		this.scale = scale;
		this.id = id;
		this.name = name;	
		this.height = height;
		this.width = width;
	}
	

	
}
