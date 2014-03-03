
package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Is used to monitor image changes. This is needed, when an image is 
 * overwritten. The components do not recognize this, so whenever an image is
 * created, it should register the cells id here, in order to invoke a change
 * later on.
 * 
 * @author Patrick
 */
public class ImageMonitor {
    
    private HashMap<String, ArrayList<Long>> images = null;
    private HashMap<Long, String> ids = null;
    
    public ImageMonitor(){
        images = new HashMap<String, ArrayList<Long>>();
        ids = new HashMap<Long, String>();
    }
    
    /**
     * Adds an id to an image. 
     * 
     * @param imageName The name of the image.
     * @param userDir The user direcotroy of the image.
     * @param id The id which should be added.
     */
    public void putID(String imageName, String userDir, long id){
        String name = imageName+"/"+userDir;
        
        removeID(id);
        
        if(!images.containsKey(name)){
            ArrayList<Long> list = new ArrayList<Long>();
            list.add(id);
            images.put(name, list);
        }else{
            images.get(name).add(id);
        } 
        
        ids.put(id, name);   
    }
    
    /**
     * Deletes an id from the list.
     * 
     * @param id The id to delete.
     */
    public void removeID(long id){
        if(ids.containsKey(id)){
            String oldName = ids.get(id);
            ArrayList<Long> list = images.get(oldName);
            list.remove(id);
            ids.remove(id);
        }
    }
    
    /**
     * Checks if an id is saved for the given image.
     * 
     * @param imageName The name of the image.
     * @param userDir The direcotry of the image.
     * @return True, if the name is ascociated to at least one id, false
     * otherwise.
     */
    public boolean idsExist(String imageName, String userDir){
        String name = imageName+"/"+userDir;
        return images.containsKey(name);
    }
    
    /**
     * Returns a list of ids ascociated to an image.
     * 
     * @param imageName The image's name.
     * @param userDir The directory of the image.
     * @return An arraylist containing the ids, or null if none exists.
     */
    public ArrayList<Long> getIds(String imageName, String userDir){
        String name = imageName+"/"+userDir;
        return images.get(name);
    }
    
}
