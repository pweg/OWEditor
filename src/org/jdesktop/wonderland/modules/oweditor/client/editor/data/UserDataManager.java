package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IImage;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IUserObserver;

/**
 * Manages everything concerning the user.
 * 
 * @author Patrick
 */
public class UserDataManager {
    
    private static final Logger LOGGER =
            Logger.getLogger(UserDataManager.class.getName());
    
    private String userDir = null;
    
    private ArrayList<IImage> imageLib = null;
    private HashMap<String, ArrayList<IImage>> foreignImgLib = null;
    private ArrayList<IUserObserver> observers = null;
    
    public UserDataManager(){
        imageLib = new ArrayList<IImage>();
        observers = new ArrayList<IUserObserver>();
        foreignImgLib = new HashMap<String, ArrayList<IImage>>();
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
        ArrayList<IImage> lib = new ArrayList<IImage>();
        lib.addAll(imageLib);
        
        for(Map.Entry<String, ArrayList<IImage>> entry : foreignImgLib.entrySet()){
            lib.addAll(entry.getValue());
        }
        
        return lib;
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
     * Returns the user directory.
     * 
     * @return The name of the user directory.
     */
    public String getUserDir() {
        return userDir;
    }
    
    /**
     * Adds a new image, either to the foreign library, or to the
     * users library.
     * 
     * @param img The image.
     * @param dir The images user directory
     * @param name The image name.
     */
    protected void addImage(BufferedImage img, String name, String dir){
        if(img == null)
            return;
        
        if(dir.equals(userDir))
            addImgToUserdir(img, name);
        else{
            ArrayList<IImage> lib;
            
            if(foreignImgLib.containsKey(dir))
                lib = foreignImgLib.get(dir);
            else
                lib = new ArrayList<IImage>();
            
            lib.add(new Image(name, img, dir));
            foreignImgLib.put(dir, lib);
        }
    }

    /**
     * Adds a new image to the user library.
     * 
     * @param img An image.
     * @param name 
     */
    private void addImgToUserdir(BufferedImage img, String name){
        if(userDir == null){
            LOGGER.warning("There is no user directory set");
            return;
        }
        
        int i;
        for(i=0; i<imageLib.size(); i++){
            if(imageLib.get(i).getName().equals(name)){
                imageLib.remove(i);
                break;
            }
        }
        
        imageLib.add(i, new Image(name, img, userDir));
        
        for(IUserObserver observer : observers)
            observer.notifyImageChange();
    }
    
    /**
     * Returns the buffered image of an image.
     * 
     * @param dir The directory of the image.
     * @param name The name of the image.
     * @return The buffered image.
     */
    public BufferedImage getImage(String name, String dir){
        
        if(dir.equals(userDir)){
            for(IImage img : imageLib){
                if(img.getName().equals(name))
                    return img.getImage();
            }
        }else{
            if(foreignImgLib.containsKey(dir)){
                ArrayList<IImage> lib = foreignImgLib.get(dir);
                for(IImage img : lib){
                    if(img.getName().equals(name))
                        return img.getImage();
                }
            }
        }
        return null;
    }

}
