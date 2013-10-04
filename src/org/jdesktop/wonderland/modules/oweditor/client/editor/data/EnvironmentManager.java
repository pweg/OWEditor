package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

public class EnvironmentManager {
	
	private int maxX = 0;
	private int maxY = 0;
	private int minX = Integer.MAX_VALUE;
	private int minY = Integer.MAX_VALUE;
	
	private DataController dc = null;
	
	public EnvironmentManager(DataController dc){
		this.dc = dc;
	}
	

	/*
	 * need to implement, when object is deleted, to find new size, or just stay that way?
	 */
	public void setX(int x, int width){
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
	
	public int getMaxWidth(){
		return maxX - minX;
	}
	
	public int getMaxHeight(){
		return maxY - minY;
	}
	

}
