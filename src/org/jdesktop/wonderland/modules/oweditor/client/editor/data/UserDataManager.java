package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IImage;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IUserObserver;


public class UserDataManager {
    
    private static final Logger LOGGER =
            Logger.getLogger(UserDataManager.class.getName());
    
    private String userDir = null;
    
    private ArrayList<IImage> imageLib = null;
    private ArrayList<IUserObserver> observers = null;
    
    public UserDataManager(){
        imageLib = new ArrayList<IImage>();
        observers = new ArrayList<IUserObserver>();
    }
    
    /**
     * Sets the users directory.
     * 
     * @param userDir
     */
    public void setUserDir(String userDir){
        this.userDir = userDir;
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
        
        if(userDir == null){
            LOGGER.warning("There is no user directory set");
            return;
        }
        
        imageLib.add(new Image(name, img, userDir));
        
        for(IUserObserver observer : observers)
            observer.notifyImageChange();
    }
    
   

}
