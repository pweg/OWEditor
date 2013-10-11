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
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.common.cell.CellTransform;

/**
 * This class is used to transform coorinates from the server
 * into editor coordinates and back.
 * @author Patrick
 */
public class CoordinateTranslator {
    
    private final int initialScale = AdapterSettings.initalScale;
    private static final Logger LOGGER =
            Logger.getLogger(WorldBuilder.class.getName());
    
    /**
     * Transforms the coordinates of a cell into coordinates, the editor
     * will understand. That being said, it calculates the coordinates, which
     * are in the center of wonderland cells to the left top of the cell, because
     * the editor uses this sort of coordinates for shapes.
     * 
     * @param cell The cell of which the coordinates should be transformed.
     * @return a Vector3fInfo instance, which holds additionaly to x,y,z 
     * coordinates a width and height value as well.
     */
    public Vector3fInfo transformCoordinates(Cell cell){
        
        BoundingVolume bounds = cell.getWorldBounds();
        
        if(bounds instanceof BoundingBox){
            BoundingBox box = (BoundingBox) bounds;
            float xExtent = box.xExtent;
            float yExtent = box.yExtent;
            float zExtent = box.zExtent;
            Vector3f vector = new Vector3f(xExtent, yExtent, zExtent);
            return transformCoordinatesSpecificSize(cell, vector);
            //LOGGER.warning("Box " + cell.getName() + " "+ xExtent + " " + zExtent + " " + x + " " +z + "\n"
            //+ vector.x + " " + vector.y);
            
        }else if(bounds instanceof BoundingSphere){
            BoundingSphere sphere = (BoundingSphere) bounds;
            float radius = sphere.radius;
            Vector3f vector = new Vector3f(radius, radius, radius);
            return transformCoordinatesSpecificSize(cell, vector);
            //LOGGER.warning("sphere "+ cell.getName() + " " + radius+ " " + x + " " +z + "\n"
            //+ vector.x + " " + vector.y);
        }
        return null;
    }
    
    /**
     * Transforms the coordinates of a cell into coordinates, the editor
     * will understand for a given object size. 
     * That being said, it calculates the coordinates, which
     * are in the center of wonderland cells to the left top of the cell, because
     * the editor uses this sort of coordinates for shapes.
     * 
     * @param cell The cell of which the coordinates should be transformed.
     * @param size The whole sizes of the object in x, y and z direction.
     * @return a Vector3fInfo instance, which holds additionaly to x,y,z 
     * coordinates a width and height value as well.
     */
    public Vector3fInfo transformCoordinatesSpecificSize(Cell cell, Vector3f size){
        
        Vector3fInfo vector = new Vector3fInfo();
        
        CellTransform transform = cell.getLocalTransform();
        Vector3f transl = transform.getTranslation(null);
                
        float x = transl.x;
        float y = transl.y;
        float z = transl.z;
        
         /*
         * Note: OW uses y value for height, not for 2d coordinates,
         * but editor uses z for height.
         */
        float xExtent = size.x;
        float yExtent = size.y;
        float zExtent = size.z;
        vector.width = (int) Math.round(xExtent*2*initialScale);
        vector.height = (int) Math.round(zExtent*2*initialScale);
        vector.x = (int) Math.round((x-xExtent)*initialScale);
        vector.z = (int) Math.round((y-yExtent)*initialScale);
        vector.y = (int) Math.round((z-zExtent)*initialScale);
        //LOGGER.warning("Box " + cell.getName() + " "+ xExtent + " " + zExtent + " " + x + " " +z + "\n"
        //+ vector.x + " " + vector.y);
            
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
            vector.x = (float)(x/initialScale+xExtent);
            vector.z = (float)(y/initialScale+yExtent);
            vector.y = (float)(z/initialScale+zExtent);
            
        }else if(bounds instanceof BoundingSphere){
            BoundingSphere sphere = (BoundingSphere) bounds;
            float radius = sphere.radius;
            vector.x = (float)(x/initialScale+radius);
            vector.z = (float)(y/initialScale+radius);
            vector.y = (float)(z/initialScale+radius);
        }
        return vector;
    }
    
}
