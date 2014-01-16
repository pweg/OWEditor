package org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces;

import java.util.ArrayList;

/**
 * This interface is used between the adapter and the gui,
 * in order to set updates made in the gui.
 * 
 * @author Patrick
 *
 */
public interface GUIObserverInterface {
    
    /**
     * Makes a translation update for an object. When only 
     * one coordinate changes, all other coordinates have to be
     * set as well.
     * 
     * @param id the object id.
     * @param x the x coordinate, the object is moved to.
     * @param y the y coordinate, the object is moved to.
     */
    public void notifyTranslation(long id, int x, int y);
    
    public void notifyRemoval(long id);
    
    public void notifyCopy(ArrayList<Long> object_ids);
    
    public void notifyPaste(long id, int x, int y);

    /**
     * Notifies a simple rotation, which means a rotation around
     * the y axis. This is the rotation done directly in the 
     * 2d environment.
     * 
     * @param id the object id.
     * @param x the x coordinate, the object is moved to.
     * @param y the y coordinate, the object is moved to.
     * @param rotation the rotation of the object.
     */
    public void notifyRotation(long id, int x, int y, double rotation);

    /**
     * Notifies adapter for object scaling.
     * 
     * @param id the object id.
     * @param x the x coordinate, the object is moved to.
     * @param y the y coordinate, the object is moved to.
     * @param scale the new scale.
     */
    public void notifyScaling(long id, int x, int y, double scale);

    
    public void notifyCreation();

}
