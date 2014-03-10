package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IImage;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IUserObserver;


public class UserDataManager {
    
    private ArrayList<IImage> imageLib = null;
    private ArrayList<IUserObserver> observers = null;
    
    public UserDataManager(){
        imageLib = new ArrayList<IImage>();
        observers = new ArrayList<IUserObserver>();
    }
    
    /**
     * Returns the user image library.
     * 
     * @return Buffered images in an array list.
     */
    public ArrayList<IImage> getImgLib(){
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
     * @param name 
     */
    public void addNewImage(BufferedImage img, String name){
        imageLib.add(new Image(name, img));
        
        for(IUserObserver observer : observers)
            observer.notifyImageChange();
    }
    
   

}
