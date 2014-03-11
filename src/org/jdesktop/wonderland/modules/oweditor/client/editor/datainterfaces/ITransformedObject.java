package org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces;

import java.awt.image.BufferedImage;

/**
 * This interface is used for updates for the GUI, which are
 * handled directly in the drawing panel and therefore only need
 * certain data in it.
 * 
 * @author Patrick
 *
 */
public interface ITransformedObject {
    
    public static final byte AVATAR = 0;
    public static final byte RECTANGLE = 1;
    public static final byte CIRCLE  = 2;

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
     * Returns the z coordinate of the object in double.
     * 
     * @return the z coordinate.
     */
    public float getZf();
    
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
     * Returns the name of the object.
     * 
     * @return the name.
     */
    public String getName();

    /**
     * Returns the id of the object.
     * 
     * @return the id.
     */
    public long getID();

    /**
     * Returns weather the object is an avatar or not.
     * 
     * @return true, if the object is an avatar, false
     * otherwise.
     */
    public byte getType();

    /**
     * Returns the image representation of the object.
     * 
     * @return A buffered image, or null, if no image 
     * is stored.
     */
    public IImage getImgClass();


}
