/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.math.Vector3f;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is used when transformations are not possible, or
 * should be stored for the creation of the object.
 * 
 * @author Patrick
 */
public class LateTransformationManager {
    
    private HashMap<Long, Vector3f> translationMap = null;
    private HashMap<String, Vector3f> copyTranslation = null;
    private HashMap<Long, Vector3f> rotationMap = null;
    private HashMap<Long, Float> scaleMap = null;
    private HashMap<Long, BufferedImage> imageMap = null;
    
    public LateTransformationManager(){
        translationMap = new HashMap<Long, Vector3f>();
        rotationMap = new HashMap<Long, Vector3f>();
        scaleMap = new HashMap<Long, Float>();
        copyTranslation  = new HashMap<String, Vector3f>();
        imageMap = new HashMap<Long, BufferedImage>();
    }
    
    /**
     * Adds translation for later use.
     * 
     * @param id The cell id, which should be translated
     * @param translation The translation.
     */
    public void addTranslation(long id, Vector3f translation){
        translationMap.put(id, translation);
    }
    
    /**
     * Adds translation for later use, but it saves it with 
     * the name instead of the id.
     * 
     * @param name The cells name.
     * @param translation The translation.
     */
    public void addTranslation(String name, Vector3f translation){
        copyTranslation.put(name, translation);
    }
    
    /**
     * Adds rotation for later use.
     * 
     * @param id The cell id, which should be translated
     * @param rotation The rotation.
     */
    public void addRotation(long id, Vector3f rotation){
        rotationMap.put(id, rotation);
    }
    
    /**
     * Adds scale for later use.
     * 
     * @param id The cell id, which should be scale
     * @param scale The scale.
     */
    public void addScale(long id, float scale){
        scaleMap.put(id, scale);
    }
    
    /**
     * Gets the translation.
     * 
     * @param id The cell id.
     * @return A vector containing the translation, or null
     * if there is no entry.
     */
    public Vector3f getTranslation(long id){
        return translationMap.get(id);
    }
    
    public Vector3f getTranslation(String name){
        return copyTranslation.get(name);
    }
    
    /**
     * Gets the rotation.
     * 
     * @param id The cell id.
     * @return A vector containing the rotation, or null
     * if there is no entry.
     */
    public Vector3f getRotation(long id){
        return rotationMap.get(id);
    }
    
    /**
     * Gets the scale.
     * 
     * @param id The cell id.
     * @return The scale, or -1 if there is no entry.
     */
    public float getScale(long id){
        if(scaleMap.containsKey(id)){
            return scaleMap.get(id);
        }
        return -1;
    }
    
    public void removeTranslate(long id){
        translationMap.remove(id);
    }
    
    public void removeTranslate(String name){
        copyTranslation.remove(name);
    }
    
    public void removeRotate(long id){
        rotationMap.remove(id);
    }
    public void removeScale(long id){
        scaleMap.remove(id);
    }
    
    public boolean containsCell(long id, String name){
        return containsCell(name) || containsCell(id);
        
    }
    
    public boolean containsCell(String name){
        return copyTranslation.containsKey(name);
    }
    
    public boolean containsCell(long id){
        return translationMap.containsKey(id) || rotationMap.containsKey(id)
                || scaleMap.containsKey(id);
    }
    
    public boolean containsImage(long id){
        return imageMap.containsKey(id);
    }
    
    /**
     * Invokes a tranfsormation, if the name, or the id was added
     * before.
     * 
     * @param server The server communication.
     * @param id The id of the cell.
     * @param name The name of the cell.
     * @return True, if all translation events were successfull,
     *  false otherwise.
     */
    public boolean invokeLateTransform(ServerCommunication server, long id, 
            String name){
        
        if(getTranslation(name) != null)
            return lateTransform(server, id, name);
        else
            return lateTransform(server, id); 
    }
    
    public boolean invokeLateImage(ServerCommunication server,long id){
        
        BufferedImage img = imageMap.get(id);
        Logger.getLogger(LateTransformationManager.class.getName()).warning("IMAGE MAP "+ imageMap.size()+ " " + id);
        
        if(img == null)
            return true;
        
        try{
            if(server.addImage(id, img))
                imageMap.remove(id);
        }catch(Exception e){
            return false;
        }
        Logger.getLogger(LateTransformationManager.class.getName()).warning("IMAGE MAP2 "+ imageMap.size());
       
        return true;
    }
    
    /**
     * Does a translation, if the name was stored before. 
     * 
     * @param server The server communication.
     * @param id The id of the cell.
     * @param name The name of the cell.
     * @return True, if the translation was successful
     */
    public boolean lateTransform(ServerCommunication server, long id,
            String name){
        Vector3f lateTranslation = getTranslation(name);
        
        if(lateTranslation != null){
            try{
                server.translate(id, lateTranslation);
                removeTranslate(id);
                return true;
            }catch(ServerCommException e){
                return false;
            }
            
        }
        return false;
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
        
        if(lateScale != -1){
            try{
            server.scale(id, lateScale);
                 b = false;
                removeScale(id);
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
     * @param img The image.
     */
    public void addImage(long id, BufferedImage img) {
        imageMap.put(id, img);
    }
    
}
