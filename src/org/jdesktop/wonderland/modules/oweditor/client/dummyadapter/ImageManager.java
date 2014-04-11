package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;

/**
 * Manages the images for testing purposes.
 * 
 * @author Patrick
 */
public class ImageManager {
    
    LinkedHashMap<String,LinkedHashMap<String, BufferedImage>> imgs = null;
    
    public ImageManager(){
        imgs = new LinkedHashMap<String, LinkedHashMap<String, BufferedImage>>();
    }
    
    public boolean imageExists(String path, String name){
        if(!imgs.containsKey(path))
            return false;
        
        return imgs.get(path).containsKey(name);
    }
    
    /**
     * "Adds" images, but does not store them anywhere on the hdd.
     * 
     * @param path The path.
     * @param name The name of the image.
     * @param img The image itself.
     */
    public void addImage(String path, String name, BufferedImage img){
        LinkedHashMap<String, BufferedImage> map = null;
        if(imgs.containsKey(path))
            imgs.get(path).put(name, img);
        else{
            map = new LinkedHashMap<String, BufferedImage>();
            map.put(name, img);
            imgs.put(path, map);
        }
    }
    
    public BufferedImage getImage(String path, String name){
        if(!imgs.containsKey(path))
            return null;
        return imgs.get(path).get(name);
    }

}
