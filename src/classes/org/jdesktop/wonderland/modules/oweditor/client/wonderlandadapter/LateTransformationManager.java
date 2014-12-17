/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.math.Vector3f;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class is used when transformations are not possible, or
 * should be stored for the creation of the object.
 * 
 * @author Patrick
 */
public class LateTransformationManager {
    
    private HashMap<Long, Vector3f> translationMap = null;
    private HashMap<Long, Vector3f> rotationMap = null;
    private HashMap<Long, Float> scaleMap = null;
    private HashMap<Long, Image> imageMap = null;
    
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    
    public LateTransformationManager(){
        translationMap = new HashMap<Long, Vector3f>();
        rotationMap = new HashMap<Long, Vector3f>();
        scaleMap = new HashMap<Long, Float>();
        imageMap = new HashMap<Long, Image>();
    }
    
    /**
     * Adds translation for later use.
     * 
     * @param id The cell id, which should be translated
     * @param translation The translation.
     */
    public void addTranslation(long id, Vector3f translation){
        writeLock.lock();
        try{
            translationMap.put(id, translation);
        }finally{
            writeLock.unlock();
        }
    }
    
    /**
     * Adds rotation for later use.
     * 
     * @param id The cell id, which should be translated
     * @param rotation The rotation.
     */
    public void addRotation(long id, Vector3f rotation){
        writeLock.lock();
        try{
            rotationMap.put(id, rotation);
        }finally{
            writeLock.unlock();
        }
    }
    
    /**
     * Adds scale for later use.
     * 
     * @param id The cell id, which should be scale
     * @param scale The scale.
     */
    public void addScale(long id, float scale){
        writeLock.lock();
        try{
            scaleMap.put(id, scale);
        }finally{
            writeLock.unlock();
        }
    }
    
    /**
     * Gets the translation.
     * 
     * @param id The cell id.
     * @return A vector containing the translation, or null
     * if there is no entry.
     */
    private Vector3f getTranslation(long id){
        readLock.lock();
        try{
            return translationMap.get(id);
        }finally{
            readLock.unlock();
        }
    }
    
    /**
     * Gets the rotation.
     * 
     * @param id The cell id.
     * @return A vector containing the rotation, or null
     * if there is no entry.
     */
    private Vector3f getRotation(long id){
        readLock.lock();
        try{
            return rotationMap.get(id);
        }finally{
            readLock.unlock();
        }
    }
    
    /**
     * Gets the scale.
     * 
     * @param id The cell id.
     * @return The scale, or -1 if there is no entry.
     */
    private float getScale(long id){
        readLock.lock();
        try{
            if(scaleMap.containsKey(id)){
                return scaleMap.get(id);
            }
            return -1;
        }finally{
            readLock.unlock();
        }
    }
    
    /**
     * Removes the translation.
     * 
     * @param id The cell id.
     */
    public void removeTranslate(long id){
        writeLock.lock();
        try{
            translationMap.remove(id);
        }finally{
            writeLock.unlock();
        }
    }
    
    /**
     * Removes the rotation.
     * 
     * @param id The cells id.
     */
    private void removeRotate(long id){
        writeLock.lock();
        try{
            rotationMap.remove(id);
        }finally{
            writeLock.unlock();
        }
    }
    
    /**
     * Removes the scale.
     * 
     * @param id The cells id.
     */
    private void removeScale(long id){
        writeLock.lock();
        try{
            scaleMap.remove(id);
        }finally{
            writeLock.unlock();
        }
    }
    
    /**
     * Looks whether the id is stored here.
     * 
     * @param id The id.
     * @return True, if the id or the name was found.
     */    
    public boolean containsCell(long id){
        readLock.lock();
        try{
            return translationMap.containsKey(id) || rotationMap.containsKey(id)
                    || scaleMap.containsKey(id);
        }finally{
            readLock.unlock();
        }
    }
    
    /**
     * Looks whether the id for an image is stored.
     * 
     * @param id The id.
     * @return True, if the id was found, false otherwise.
     */
    public boolean containsImage(long id){
        readLock.lock();
        try{
            return imageMap.containsKey(id);
        }finally{
            readLock.unlock();
        }
    }
    
    /**
     * Invokes a late image, if the name, or id was added before.
     * 
     * @param server The server communication.
     * @param id The cell id.
     * @param dir The user directory.
     * @return True on success, false otherwise.
     */
    public boolean invokeLateImage(ServerCommunication server,long id, 
            String dir){  
        writeLock.lock();
        try{
            Image img = imageMap.get(id);

            if(img == null)
                return true;

            try{
                if(server.changeImage(id, img.name, img.dir))
                    imageMap.remove(id);
            }catch(Exception e){
                return false;
            }
            return true;
        }finally{
            writeLock.unlock();
        }
    }
    
    /**
     * Does a late translation, if the id was stored before.
     * 
     * @param server The server communication.
     * @param id The id of the cell.
     * @return True, if the translation was successful
     */
    public boolean lateTransform(ServerCommunication server, long id){
        
        Vector3f lateTranslation = getTranslation(id);
        Vector3f lateRotation = getRotation(id);
        float lateScale = getScale(id);
        
        boolean b = true;
        
        if(lateScale != -1){
            try{
            server.scale(id, lateScale);
                 b = false;
                removeScale(id);
            }catch(ServerCommException e){
                b = false;
            }
        }
        
        if(lateTranslation != null){
            try{
                server.translate(id, lateTranslation);
                removeTranslate(id);

            }catch(ServerCommException e){
                b = false;
            }
        }
        
        if(lateRotation != null){
            try{
                server.rotate(id, lateRotation);
                removeRotate(id);
            }catch(ServerCommException e){
                b = false;
            }
        }
        
        return b;
    }

    /**
     * Adds an image which has to be linked to a cell later.
     * 
     * @param id The cells id.
     * @param imgName The image name.
     * @param imgDir The image directory
     */
    public void addImage(long id, String imgName, String imgDir) {
        Image img = new Image(imgName, imgDir);
        readLock.lock();
        try{
            imageMap.put(id, img);
        }finally{
            readLock.unlock();
        }
    }
    
    private class Image{
        public String name;
        public String dir;
        
        Image(String imgName, String imgDir){
            this.name = imgName;
            this.dir = imgDir;
        }
    }
    
}
