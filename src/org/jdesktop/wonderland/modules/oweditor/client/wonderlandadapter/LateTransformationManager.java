/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.math.Vector3f;
import java.util.HashMap;

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
    
    public LateTransformationManager(){
        translationMap = new HashMap<Long, Vector3f>();
        rotationMap = new HashMap<Long, Vector3f>();
        scaleMap = new HashMap<Long, Float>();
    }
    
    /**
     * Adds translation for later use.
     * 
     * @param id The cell id, which should be translated
     * @param translation The translation.
     */
    public void putTranslation(long id, Vector3f translation){
        translationMap.put(id, translation);
    }
    
    /**
     * Adds rotation for later use.
     * 
     * @param id The cell id, which should be translated
     * @param rotation The rotation.
     */
    public void putRotation(long id, Vector3f rotation){
        rotationMap.put(id, rotation);
    }
    
    /**
     * Adds scale for later use.
     * 
     * @param id The cell id, which should be scale
     * @param scale The scale.
     */
    public void putScale(long id, float scale){
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
    public void removeRotate(long id){
        rotationMap.remove(id);
    }
    public void removeScale(long id){
        scaleMap.remove(id);
    }
    
    
}
