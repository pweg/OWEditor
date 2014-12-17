package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IEnvironmentObserver;

/**
 * This class manages the environment of the virtual world.
 * It resizes the worlds bounds for instance.
 * 
 * @author Patrick
 *
 */
public class EnvironmentManager {
    
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();
    
    private IEnvironmentObserver en = null;
    
    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;
    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
    
    private String[] serverList = null;
        
    /**
     * Creates a new EnvironmentManager instance.
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
        writeLock.lock();
        try{
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
        }finally{
            writeLock.unlock();
        }
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

        writeLock.lock();
        try{
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
        }finally{
            writeLock.unlock();
        }
    }
    
    /**
     * Returns the width of the world.
     * 
     * @return the worlds width
     */
    public int getMaxWidth(){
        readLock.lock();
        try{
            return maxX - minX;
        }finally{
            readLock.unlock();
        }
    }
    
    /**
     * Returns the height of the world
     * 
     * @return the worlds height.
     */
    public int getMaxHeight(){
        readLock.lock();
        try{
            return maxY - minY;
        }finally{
            readLock.unlock();
        }
    }

    /**
     * Registers a gui observer, which notifies the gui on 
     * environmental changes. Note: There can be only one 
     * observer registered at a time.
     * 
     * @param en the observer.
     */
    public void registerObserver(IEnvironmentObserver en) {
        this.en = en;
    }

    /**
     * Sets the server list.
     * 
     * @param servers The server names.
     */
    public void setServerList(String[] servers) {
        this.serverList = servers;
    }
    
    /**
     * Returns the server list.
     * 
     * @return String array containing the server names.
     */
    public String[] getServerList(){
        return serverList;
    }
    

}
