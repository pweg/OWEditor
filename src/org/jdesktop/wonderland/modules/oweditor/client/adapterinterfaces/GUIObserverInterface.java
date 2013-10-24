package org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces;

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
    
    public void notifyCopy(long id, int x, int y);

    public void notifyRotation(long id, int x, int y, double rotation);

}
