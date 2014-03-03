package org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces;

/**
 * This interface is used as an semi observer for the environmentManager.
 * Note that there should only be one observer, because only one 
 * gui needs to be updated.
 * 
 * @author Patrick
 *
 */
public interface IEnvironmentObserver {
    
    /**
     * Sets a new width for the drawing panel.
     * This is used for making the panel always get larger
     * when objects are out of the current size.
     * 
     * @param width the new width. This should be the width
     * of the current world.
     */
    public void notifyWidthChange(int width);
    
    /**
     * Sets a new height for the drawing panel.
     * This is used for making the panel always get larger
     * when objects are out of the current size.
     * 
     * @param height the new height. This should be the height
     * of the current world
     */
    public void notifyHeightChange(int height);  
    
    /**
     * Sets a new minimal x coordinate, which is used
     * for centering all objects.
     * 
     * @param x the new value.
     */
    public void notifyMinXChange(int x);
   
    /**
     * Sets a new minimal y coordinate, which is used
     * for centering all objects.
     * 
     * @param y the new value.
     */
    public void notifyMinYChange(int y);

}
