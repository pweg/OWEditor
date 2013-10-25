package org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces;

public interface DataObjectInterface extends TranslatedObjectInterface{

    /**
     * Returns the x coordinate of the object.
     * 
     * @return the x coordinate.
     */
    public float getXf();
    
    /**
     * Returns the y coordinate of the object.
     * 
     * @return the y coordinate.
     */
    public float getYf();
    
    /**
     * Returns the z coordinate of the object.
     * 
     * @return the z coordinate.
     */
    public float getZf();
    
    /**
     * Returns the width of the object.
     * 
     * @return the width.
     */
    public float getWidthf();
    
    /**
     * Returns the height of the object.
     * 
     * @return the height.
     */
    public float getHeightf();
    
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
     * Returns the x rotation of the object.
     * 
     * @return the rotation value. 
     */
    public double getRotationX();
    
    
    /**
     * Returns the y rotation of the object.
     * 
     * @return the rotation value. 
     */
    public double getRotationY();
    
    /**
     * Returns the z rotation of the object.
     * 
     * @return the rotation value. 
     */
    public double getRotationZ();  
    
    /**
     * Sets the x coordinate of the object.
     * 
     * @param x the x coordinate.
     */
    public void setX(float x);
    
    /**
     * Sets the y coordinate of the object.
     * Please remember that the y coordinate is used for the
     * 2d representation in the editor, so make sure this
     * coordinate is not the height value.
     * 
     * @param y the y coordinate.
     */
    public void setY(float y);
    
    /**
     * Sets the z coordinate of the object.
     * Please remember, this coordinate is used as
     * the height coordinate.
     * 
     * @param z the z coordinate.
     */
    public void setZ(float z);
    
    /**
     * Sets the x rotation of the object.
     * 
     * @param rotation the rotation.
     */
    public void setRotationX(double rotation);
    
    /**
     * Sets the y rotation of the object.
     * 
     * @param rotation the rotation.
     */
    public void setRotationY(double rotation);
    
    /**
     * Sets the z rotation of the object.
     * 
     * @param rotation the rotation.
     */
    public void setRotationZ(double rotation);
    
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
    public void setWidth(float width);
    
    /**
     * Sets the height of the object.
     * 
     * @param height the height.
     */
    public void setHeight(float height);
    
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
    public void setCoordinates(float x, float y, float z);
    
    /**
     * Sets the paramater, wheather the object is 
     * an avatar.
     * 
     * @param type the type of the object
     */
    public void setType(byte type);
    

}
