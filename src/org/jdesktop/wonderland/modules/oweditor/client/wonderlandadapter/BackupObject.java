package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.math.Vector3f;
import org.jdesktop.wonderland.client.cell.Cell;

public class BackupObject {
    
    private Vector3f rotation = null;
        
    private float scale = 0;
    
    private boolean toDelete = false;
    
    private Cell cell = null;
    
    public BackupObject(Cell cell, float rotationX, float rotationY,
            float rotationZ, float scale){
        this.cell = cell;
        rotation = new Vector3f(rotationX, rotationY, rotationZ);
        
        this.scale = scale;
    }
    
    public Cell getCell(){
        return cell;
    }
    
    public Vector3f getRotation(){
        return rotation;
    }
    
    public float getScale(){
        return scale;
    }
    
    public void setDeletion(boolean delete){
        toDelete = delete;
    }
    
    public boolean isForDeletion(){
        return toDelete;
    }

}
