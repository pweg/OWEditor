package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.EnvironmentObserverInterface;

/**
 * This class manages the environment of the virtual world.
 * It resizes the worlds bounds for instance.
 * 
 * @author Patrick
 *
 */
public class EnvironmentManager {
    
    private EnvironmentObserverInterface en = null;
    
    private int maxX = 0;
    private int maxY = 0;
    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
        
    /**
     * Creates a new EnvironmentManager instance.
     * 
     * @param dc a dataController instance.
     */
    public EnvironmentManager(){
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
        
        boolean change = false;
        if(x + width> maxX){
            maxX = x+ width;
            change = true;
        }
        if(x < minX){
            minX = x;
            change = true;
            if(en != null){
                en.notifyMinXChange(x);
            }
        }
        if(change && en != null)
            en.notifyWidthChange(getMaxWidth());
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
    public void setY(int y, int height){
        
        boolean change = false;
        
        if(y + height> maxY){
            maxY = y + height;
            change = true;
        }
        if(y < minY){
            minY = y;
            change = true;
            if(en != null){
                en.notifyMinYChange(y);
            }
        }
        if(change && en != null)
            en.notifyHeightChange(getMaxHeight());
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

    /**
     * Registers a gui observer, which notifies the gui on 
     * environmental changes. Note: There can be only one 
     * observer registered at a time.
     * 
     * @param en the observer.
     */
    public void registerObserver(EnvironmentObserverInterface en) {
        this.en = en;
    }
    

}
