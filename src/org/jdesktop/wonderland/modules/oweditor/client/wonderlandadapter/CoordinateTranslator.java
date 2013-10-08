/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.bounding.BoundingVolume;
import com.jme.math.Vector3f;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.common.cell.CellTransform;

/**
 * This class is used to transform coorinates from the server
 * into editor coordinates and back.
 * @author Patrick
 */
public class CoordinateTranslator {
    private final int initialScale = AdapterSettings.initalScale;
    
    /**
     * Transforms the coordinates of a cell into coordinates, the editor
     * will understand. That being said, it calculates the coordinates, which
     * are in the center of wonderland cells to the left top of the cell, because
     * the editor uses this sort of coordinates for shapes.
     * 
     * @param cell: The cell of which the coordinates should be transformed.
     * @return a Vector3fInfo instance, which holds additionaly to x,y,z 
     * coordinates a width and height value as well.
     */
    public Vector3fInfo transformCoordinates(Cell cell){
        
        Vector3fInfo vector = new Vector3fInfo();
        
        CellTransform transform = cell.getLocalTransform();
        Vector3f transl = transform.getTranslation(null);
        
        float x = transl.x;
        float y = transl.y;
        float z = transl.z;
        
        BoundingVolume bounds = cell.getWorldBounds();
        
         /*
         * Note: OW uses y value for height, not for 2d coordinates,
         * but editor uses z for height.
         */
        if(bounds instanceof BoundingBox){
            BoundingBox box = (BoundingBox) bounds;
            float xExtent = box.xExtent;
            float yExtent = box.yExtent;
            float zExtent = box.zExtent;
            vector.width = (int) Math.round(xExtent*4*initialScale);
            vector.height = (int) Math.round(yExtent*4*initialScale);
            vector.x = (int) Math.round((x-2*xExtent)*initialScale);
            vector.z = (int) Math.round((y-2*yExtent)*initialScale);
            vector.y = (int) Math.round((z-2*zExtent)*initialScale);
            
        }else if(bounds instanceof BoundingSphere){
            BoundingSphere sphere = (BoundingSphere) bounds;
            float radius = sphere.radius;
            vector.width = (int) Math.round(radius*4*initialScale);
            vector.height = (int) Math.round(radius*4*initialScale);
            vector.x = (int) Math.round((x-2*radius)*initialScale);
            vector.z = (int) Math.round((y-2*radius)*initialScale);
            vector.y = (int) Math.round((z-2*radius)*initialScale);
        }
        return vector;
    }
    
    public Vector3f transformCoordinatesBack(Cell cell, float x, float y, float z){
        
        Vector3f vector = new Vector3f();
        BoundingVolume bounds = cell.getWorldBounds();
        
         /*
         * Note: OW uses y value for height, not for 2d coordinates,
         * but editor uses z for height.
         */        
        if(bounds instanceof BoundingBox){
            BoundingBox box = (BoundingBox) bounds;
            float xExtent = box.xExtent;
            float yExtent = box.zExtent;
            float zExtent = box.yExtent;
            vector.x = (float)(x/initialScale+2*xExtent);
            vector.z = (float)(y/initialScale+2*yExtent);
            vector.y = (float)(z/initialScale+2*zExtent);
            
        }else if(bounds instanceof BoundingSphere){
            BoundingSphere sphere = (BoundingSphere) bounds;
            float radius = sphere.radius;
            vector.x = (float)(x/initialScale+2*radius);
            vector.z = (float)(y/initialScale+2*radius);
            vector.y = (float)(z/initialScale+2*radius);
        }
        return vector;
    }
    
}
