package org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public interface IDataObject extends ITransformedObject{

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

    /**
     * Sets the representation image of the object.
     * 
     * @param img A buffered image.
     * @param name The name of the image.
     * @param path The user dir of the image.
     */
    public void setImage(BufferedImage img, String name, String path);
    
    /**
     * Adds one right entry to the object. 
     * 
     * @param type The right type (User/UserGroup).
     * @param name The name of the user/usergroup.
     * @param isOwner Whether this user has ownership.
     * @param permitSubObjects Whether this user is permitted to add/change
     *        sub objects.
     * @param permitAbilityChange Whether this user is permitted to change
     *        abilities.
     * @param permitMove Whether this user is permitted to move the object.
     * @param permitView Whether this user is permitted to view the object.
     * @param isEditable Whether this entry should be editable.
     * @param isEverybody Whether this is an everybody entry.
     */
    public void setRight(String type, String name, boolean isOwner,
            boolean permitSubObjects, boolean permitAbilityChange, 
            boolean permitMove, boolean permitView,
            boolean isEditable, boolean isEverybody);
    
    /**
     * Returns the rights interface of the object.
     * 
     * @return The rights.
     */
    public ArrayList<IRights> getRights();

    /**
     * Returns a specific right setting.
     *  
     * @param name The name of the right.
     * @param type The type of the right.
     */
    public IRights getRight(String name, String type);

}
