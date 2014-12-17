package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public interface IWindowToGraphic {
    
    /**
     * Repaints the drawing panel.
     */
    public void repaint();

    
    /**
     * Calls the adapter for an object removal.
     * 
     * @param ids The object ids.
     */
    public void setObjectRemoval(ArrayList<Long> ids);
    
    /**
     * Calls the adapter for a translation update.
     * 
     * @param ids The object ids making the translation.
     * @param coordinates The coordinates of each object. 
     * The list should organized like the ids, so that the position
     * of the id is the same position in the coordinates.
     */
    public void setTranslationUpdate(ArrayList<Long> ids, ArrayList<Point> coordinates);
    
    /**
     * Calls the adapter for a copy update, which means
     * the objects will be backed up.
     * 
     * @param object_ids The objects from which a backup
     * should be made.
     */
    public void setCopyUpdate(ArrayList<Long> object_ids);
    
    /**
     * Calls the adapter for a paste update, which means
     * the given id will be copied to the given 
     * coordinates.
     * 
     * @param ids The ids of the object which should be copied.
     * @param coordinates The coordinates of each paste object. 
     * The list should organized like the ids, so that the position
     * of the id is the same position in the coordinates.
     */
    public void setPasteUpdate(ArrayList<Long> ids, ArrayList<Point> coordinates);
    
    /**
     * Calls the adapter for a rotation update.
     * 
     * @param ids The ids of the objects to rotate.
     * @param coordinates The coordinates of each object. 
     * The list should organized like the ids, so that the position
     * of the id is the same position in the coordinates.
     * @param rotation The new rotation values of the objects, which should
     * be organized like the coordinates.
     */
    public void setRotationUpdate(ArrayList<Long> ids, ArrayList<Point> coordinates, 
            ArrayList<Double> rotation);

    /**
     * Calls the adapter for a scale update.
     *  
     * @param ids The id of the object to scale.
     * @param coordinates The coordinates of each object. 
     * The list should organized like the ids, so that the position
     * of the id is the same position in the coordinates.
     * @param scale The scale of each object, which should be 
     * organized like the coordinates.
     */
    public void setScaleUpdate(ArrayList<Long> ids, ArrayList<Point> coordinates, 
            ArrayList<Double> scale);

    /**
     * Searches for an image.
     *  
     * @param name The name of the image.
     * @param dir The users directory of the image.
     * @return The image, or null if it was not found.
     */
    public BufferedImage getImage(String name, String dir);

    

}
