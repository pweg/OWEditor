package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IImage;

/**
 * Manages everything concerning the user.
 * 
 * @author Patrick
 */
public class UserDataManager {
    
    private static final Logger LOGGER =
            Logger.getLogger(UserDataManager.class.getName());
    
    private String userName = "";
    private String userDir = null;
    
    private ArrayList<IImage> imageLib = null;
    private HashMap<String, ArrayList<IImage>> foreignImgLib = null;
    
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();
    
    public UserDataManager(){
        imageLib = new ArrayList<IImage>();
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
    
    public void setUserName(String name) {
        this.userName = name;
    }
    
    /**
     * Returns the user image library.
     * 
     * @return Buffered images in an array list.
     */
    public ArrayList<IImage> getImgLib(){

        ArrayList<IImage> lib = new ArrayList<IImage>();
        
        readLock.lock();
        try{
            lib.addAll(imageLib);
            
            for(Map.Entry<String, ArrayList<IImage>> entry : foreignImgLib.entrySet()){
                lib.addAll(entry.getValue());
            }
        }finally{
            readLock.unlock();
        }
        
        return lib;
    }
    
    /**
     * Returns the user name.
     * 
     * @return The user name.
     */
    public String getUserName(){
        return userName;
    }
        
    /**
     * Returns the user directory.
     * 
     * @return The name of the user directory.
     */
    public String getUserDir() {
        readLock.lock();
        try{
            return userDir;
        }finally{
            readLock.unlock();
        }
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
        
        writeLock.lock();
        try{
            if(dir.equals(userDir)){
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
            }else{
                ArrayList<IImage> lib;
                
                if(foreignImgLib.containsKey(dir))
                    lib = foreignImgLib.get(dir);
                else
                    lib = new ArrayList<IImage>();
                
                lib.add(new Image(name, img, dir));
                foreignImgLib.put(dir, lib);
            }
        }finally{
            writeLock.unlock();
        }
    }
    
    /**
     * Returns the buffered image of an image.
     * 
     * @param dir The directory of the image.
     * @param name The name of the image.
     * @return The buffered image.
     */
    public BufferedImage getImage(String name, String dir){
        
        if(userDir == null || dir == null)
            return null;
        
        readLock.lock();
        try{
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
        }finally{
            readLock.unlock();
        }
        return null;
    }

}
