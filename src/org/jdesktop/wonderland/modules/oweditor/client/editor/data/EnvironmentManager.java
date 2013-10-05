package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

/**
 * This class manages the environment of the virtual world.
 * It resizes the worlds bounds for instance.
 * 
 * @author Patrick
 *
 */
public class EnvironmentManager {
	
	private int maxX = 0;
	private int maxY = 0;
	private int minX = Integer.MAX_VALUE;
	private int minY = Integer.MAX_VALUE;
	
	private DataController dc = null;
	
	/**
	 * Creates a new EnvironmentManager instance.
	 * 
	 * @param dc a dataController instance.
	 */
	public EnvironmentManager(DataController dc){
		this.dc = dc;
	}
	
	/**
	 * Sets the minimal/maximal x bounds of the world.
	 * The minimal bound is set, when the x value is smaller 
	 * than the actual minX value.
	 * The max bound is set, when the x value + width is larger
	 * than the actual maxX value.
	 * 
	 * @param x the x coordinate.
	 * @param width the width of the object.
	 */
	public void setX(int x, int width){
		/*
		 * need to implement, when object is deleted, to find new size, or just stay that way?
		 */
		if(x + width> maxX){
			maxX = x+ width;
			dc.setNewWidth(getMaxWidth());
		}
		else if(x < minX){
			minX = x;
			dc.setNewWidth(getMaxWidth());
			dc.setNewMinX(x);
		}
	}
	
	/**
	 * Sets the minimal/maximal y bounds of the world.
	 * The minimal bound is set, when the y value is smaller 
	 * than the actual minY value.
	 * The max bound is set, when the y value + height is larger
	 * than the actual maxY value.
	 * 
	 * @param y the y coordinate.
	 * @param height the height of the object.
	 */
	public void setY(int y, int heigth){
		if(y + heigth> maxY){
			maxY = y + heigth;
			dc.setNewHeight(getMaxHeight());
		}
		else if(y < minY){
			minY = y;
			dc.setNewHeight(getMaxHeight());
			dc.setNewMinY(y);
		}
	}
	
	/**
	 * Returns the width of the world.
	 * 
	 * @return the worlds width
	 */
	public int getMaxWidth(){
		return maxX - minX;
	}
	
	/**
	 * Returns the height of the world
	 * 
	 * @return the worlds height.
	 */
	public int getMaxHeight(){
		return maxY - minY;
	}
	

}
