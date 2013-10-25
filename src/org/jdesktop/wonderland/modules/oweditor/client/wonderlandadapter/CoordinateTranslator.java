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
import java.awt.Point;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.common.cell.CellTransform;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectInterface;

/**
 * This class is used to transform coorinates from the server
 * into editor coordinates and back.
 * @author Patrick
 */
public class CoordinateTranslator implements CoordinateTranslatorInterface{
    
    private final int scale = AdapterSettings.initalScale;
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
    /*public Vector3fInfo transformCoordinates(Cell cell){
        
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
   /* public Vector3fInfo transformCoordinatesSpecificSize(Cell cell, Vector3f size){
        
        Vector3fInfo vector = new Vector3fInfo();
        
        CellTransform transform = cell.getLocalTransform();
        Vector3f transl = transform.getTranslation(null);
                
        float x = transl.x;
        float y = transl.y;
        float z = transl.z;
        
         /*
         * Note: OW uses y value for height, not for 2d coordinates,
         * but editor uses z for height.
         *
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
    }*/
    
    public Vector3f transformCoordinatesBack(Cell cell, float x, float y, float z){
        
        Vector3f vector = new Vector3f();
        BoundingVolume bounds = cell.getLocalBounds();
        
         /*
         * Note: OW uses y value for height, not for 2d coordinates,
         * but editor uses z for height.
         */        
        if(bounds instanceof BoundingBox){
            BoundingBox box = (BoundingBox) bounds;
            float xExtent = box.xExtent;
            float yExtent = box.zExtent;
            float zExtent = box.yExtent;
            vector.x = (float)(x/scale+xExtent);
            vector.z = (float)(y/scale+yExtent);
            vector.y = (float)(z/scale+zExtent);
            
        }else if(bounds instanceof BoundingSphere){
            BoundingSphere sphere = (BoundingSphere) bounds;
            float radius = sphere.radius;
            vector.x = (float)(x/scale+radius);
            vector.z = (float)(y/scale+radius);
            vector.y = (float)(z/scale+radius);
        }
        return vector;
    }

    public Point transformCoordinates(float x, float y, float width, float height) {
        int x_int = (int) Math.round((x-width/2)*scale);
        int y_int = (int) Math.round((y-height/2)*scale);
        
        return new Point(x_int, y_int);
    }

    public int transformWidth(float width) {
        return (int)Math.round(width * scale);
    }

    public int transformHeight(float height) {
        return (int)Math.round(height * scale);
    }

    public double getScale() {
        return scale;
    }

    public double getRotation(DataObjectInterface object) {
        return -object.getRotationY();
    }
    
    public Quaternion setRotation(Cell cell, double rotation){
        LOGGER.warning("xxxxxxxxxxx " + rotation);
        
        CellTransform transform = cell.getLocalTransform();
        
        Quaternion current_rot = transform.getRotation(null);
        float[] angles = current_rot.toAngles(new float[3]);
        
        float rotationX = angles[0];
        float rotationZ = angles[2];
        float rotationY = (float) Math.toRadians(-rotation);
        LOGGER.warning("xxxxxxxxxxx " + rotation + "  " +rotationY + " "  + rotationY);
        
        return new Quaternion(new float[] { rotationX, rotationY, rotationZ });
    }
    
}
