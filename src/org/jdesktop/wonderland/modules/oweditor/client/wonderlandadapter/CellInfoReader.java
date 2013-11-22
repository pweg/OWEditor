/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.bounding.BoundingVolume;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.common.cell.CellTransform;

/**
 * This class is used for getting information out of cells, like their
 * coordinates, rotation and scale. The methods are static.
 * 
 * @author Patrick
 */
public class CellInfoReader {
    
    /**
     * Gets the Coordinates, as well as the size of a given cell.
     * 
     * @param cell The cell.
     * @return A Vector3fInfo type, which contains a vector with the 
     * coordinates, as well as the height and width of the object.
     */
    public static Vector3fInfo getCoordinates(Cell cell){
        
        BoundingVolume bounds = cell.getLocalBounds();
        Vector3fInfo v = new Vector3fInfo();
        
        if(bounds instanceof BoundingBox){
            BoundingBox box = (BoundingBox) bounds;
            v.width = box.xExtent*2;
            v.height = box.zExtent*2;            
        }else if(bounds instanceof BoundingSphere){
            BoundingSphere sphere = (BoundingSphere) bounds;
            v.width= sphere.radius*2;
            v.height= sphere.radius*2;
        }
        CellTransform transform = cell.getLocalTransform();
        Vector3f transl = transform.getTranslation(null);
                
        /*
        * Remember: The editor uses y as height value in a 2D environment,
        * so it should be changed with z.
        */
        v.x = transl.x;
        v.z = transl.y;
        v.y = transl.z;
        
        return v;
    }
    
    /**
     * Returns the rotation of a given cell.
     * 
     * @param cell The cell.
     * @return A vecotr, which holds the rotation values of 
     * the x, y and z axis.
     */
    public static Vector3f getRotation(Cell cell){
        Vector3f vector = new Vector3f();
        
        CellTransform transform = cell.getLocalTransform();
        Quaternion rotation = transform.getRotation(null);
        float[] angles = rotation.toAngles(new float[3]);
        
        vector.x = (float) Math.toDegrees(angles[0]);
        vector.y = (float) Math.toDegrees(angles[1]);
        vector.z = (float) Math.toDegrees(angles[2]);
        
        return vector;
    }
    
    /**
     * Returns the scale of a cell.
     * 
     * @param cell The cell.
     * @return A float value, holding the cells scale.
     */
    public static float getScale(Cell cell){
        CellTransform transform = cell.getLocalTransform();
        
        return transform.getScaling();
    }
    
    /**
     * Returns the id of a cell in a long format.
     * 
     * @param cell The cell.
     * @return  The id of the given cell.
     */
    public static long getID(Cell cell){
        return Long.valueOf(cell.getCellID().toString());
    }
    
}
