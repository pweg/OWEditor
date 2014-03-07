package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IUserObserver;


public class UserDataManager {
    
    private ArrayList<BufferedImage> imageLib = null;
    private ArrayList<IUserObserver> observers = null;
    
    public UserDataManager(){
        imageLib = new ArrayList<BufferedImage>();
        observers = new ArrayList<IUserObserver>();
    }
    
    /**
     * Returns the user image library.
     * 
     * @return Buffered images in an array list.
     */
    public ArrayList<BufferedImage> getImgLib(){
        return imageLib;
    }
    
    /**
     * Registers an observer.
     * 
     * @param observer The observer
     */
    public void registerObserver(IUserObserver observer){
        observers.add(observer);
    }
    
    /**
     * Removes an observer.
     * 
     * @param observer The observer.
     */
    public void removeObserver(IUserObserver observer){
        observers.remove(observer);
    }
    
    /**
     * Adds a new image to the user library.
     * 
     * @param img An image.
     */
    public void addNewImage(BufferedImage img){
        imageLib.add(img);
        
        for(IUserObserver observer : observers)
            observer.notifyImageChange();
    }

}
