package org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces;

public interface DataObjectInterface {

	/**
	 * Returns the x coordinate of the object.
	 * 
	 * @return the x coordinate.
	 */
    public int getX();
    
	/**
	 * Returns the y coordinate of the object.
	 * 
	 * @return the y coordinate.
	 */
    public int getY();
    
	/**
	 * Returns the z coordinate of the object.
	 * 
	 * @return the z coordinate.
	 */
    public int getZ();
    
	/**
	 * Returns the rotation of the object.
	 * 
	 * @return the rotation.
	 */
    public double getRotation();
    
	/**
	 * Returns the scale of the object.
	 * 
	 * @return the scale.
	 */
    public double getScale();
    
    /**
	 * Returns the width of the object.
	 * 
	 * @return the width.
	 */
    public int getWidth();
    
    /**
	 * Returns the height of the object.
	 * 
	 * @return the height.
	 */
    public int getHeight();
    
    /**
	 * Returns the id of the object.
	 * 
	 * @return the id.
	 */
    public long getID();
    
    /**
	 * Returns the name of the object.
	 * 
	 * @return the name.
	 */
    public String getName();
    
    /**
     * Sets the x coordinate of the object.
     * 
     * @param x the x coordinate.
     */
    public void setX(int x);
    
    /**
     * Sets the y coordinate of the object.
     * Please remember that the y coordinate is used for the
     * 2d representation in the editor, so make sure this
     * coordinate is not the height value.
     * 
     * @param y the y coordinate.
     */
    public void setY(int y);
    
    /**
     * Sets the z coordinate of the object.
     * Please remember, this coordinate is used as
     * the height coordinate.
     * 
     * @param z the z coordinate.
     */
    public void setZ(int z);
    
    /**
     * Sets the rotation of the object.
     * 
     * @param rotation the rotation.
     */
    public void setRotation(double rotation);
    
    /**
     * Sets the scale of the object.
     * 
     * @param scale the scale.
     */
    public void setScale(double scale);
    
    /**
     * Sets the width of the object.
     * 
     * @param width the width.
     */
    public void setWidth(int width);
    
    /**
     * Sets the height of the object.
     * 
     * @param height the height.
     */
    public void setHeight(int height);
    
    /**
     * Sets the id of the object.
     * 
     * @param id the id.
     */
    public void setID(long id);
    
    /**
     * Sets the name of the object.
     * 
     * @param name the name.
     */
    public void setName(String name);
    
    /**
     * Sets the coordinates of the object.
     * 
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param z the z coordinate.
     */
    public void setCoordinates(int x, int y, int z);
    
    /**
     * Sets the paramater, wheather the object is 
     * an avatar.
     * 
     * @param isAvatar true, if the object is an avatar, 
     * false otherwise
     */
    public void setIsAvatar(boolean isAvatar);
    
    /**
     * Returns weather the object is an avatar or not.
     * 
     * @return true, if the object is an avatar, false
     * otherwise.
     */
    public boolean isAvatar();
    

}
