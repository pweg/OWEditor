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
import java.awt.geom.Point2D;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.common.cell.CellTransform;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataObject;

/**
 * This class is used to transform coorinates from the server
 * into editor coordinates and back.
 * @author Patrick
 */
public class CoordinateTranslator implements CoordinateTranslatorInterface{
    
    private final int globalScale = AdapterSettings.initalScale;
    private static final Logger LOGGER =
            Logger.getLogger(WorldBuilder.class.getName());

    @Override
    public Point transformCoordinatesInt(float x, float y, 
            float width, float height) {        
        int x_int = (int) Math.round((x-width/2)*globalScale);
        int y_int = (int) Math.round((y-height/2)*globalScale);
        
        return new Point(x_int, y_int);
    }
    
    @Override
    public Point2D.Double transformCoordinatesBack(float x, float y, 
            float width, float height) {
        double x_double = (x+width/2)/globalScale;
        double y_double = (y+height/2)/globalScale;
        
        return new Point2D.Double(x_double, y_double);
    }
    /**
     * 
     * @param cell
     * @param x
     * @param y
     * @param z
     * @return 
     */
    public Vector3f transformCoordinatesBack(Cell cell, float x, float y, float z){
        
        Vector3f vector = new Vector3f();
        BoundingVolume bounds = cell.getLocalBounds();
        float scale = CellInfoReader.getScale(cell);
        
         /*
         * Note: OW uses y value for height, not for 2d coordinates,
         * but editor uses z for height.
         */        
        if(bounds instanceof BoundingBox){
            BoundingBox box = (BoundingBox) bounds;
            float xExtent = box.xExtent*scale;
            float yExtent = box.zExtent*scale;
            vector.x = (float)(x/globalScale+xExtent);
            vector.z = (float)(y/globalScale+yExtent);
            vector.y = z;
            
        }else if(bounds instanceof BoundingSphere){
            BoundingSphere sphere = (BoundingSphere) bounds;
            float radius = sphere.radius*scale;
            vector.x = (float)(x/globalScale+radius);
            vector.z = (float)(y/globalScale+radius);
            vector.y = z;
        }
        return vector;
    }
    
    public int[] transformSize(BoundingVolume bounds){
        
        int[] ret_val = new int[2];
        
        /*
         * OW does not use width/heigt, but extend, which is the
         * length from the center to the edge in x,y, or z direction.
         * Therefore *2 is neccessary.
         */
        if(bounds instanceof BoundingBox){
            BoundingBox box = (BoundingBox) bounds;
            float xExtent = box.xExtent*globalScale*2;
            float yExtent = box.zExtent*globalScale*2;
            ret_val[0] = (int) Math.round(xExtent);
            ret_val[1] = (int) Math.round(yExtent);
            return ret_val;
            
        }else if(bounds instanceof BoundingSphere){
            BoundingSphere sphere = (BoundingSphere) bounds;
            float radius = sphere.radius*globalScale*2;
            ret_val[0] = (int) Math.round(radius);
            ret_val[1] = (int) Math.round(radius);
            return ret_val;
            
        }
        return null;
    }
    
    public Vector3f transformVector(Vector3f coords){
        return new Vector3f(coords.x, coords.z, coords.y);
    }

    @Override
    public double transformXBack(double x) {
        return x/globalScale;
    }

    @Override
    public double transformYBack(double y) {
        return y/globalScale;
    }

    @Override
    public int transformWidth(float width) {
        return (int)Math.round(width * globalScale);
    }

    @Override
    public int transformHeight(float height) {
        return (int)Math.round(height * globalScale);
    }

    public double getScale() {
        return globalScale;
    }

    public double getRotation(IDataObject object) {
        return -object.getRotationY();
    }
    
    public Quaternion setRotation(Cell cell, double rotation){
        
        CellTransform transform = cell.getLocalTransform();
        
        Quaternion current_rot = transform.getRotation(null);
        float[] angles = current_rot.toAngles(new float[3]);
        
        float rotationX = angles[0];
        float rotationZ = angles[2];
        float rotationY = (float) Math.toRadians(-rotation);
        
        return new Quaternion(new float[] { rotationX, rotationY, rotationZ });
    }
    
    /**
     * This is used, when the adapter internal classes have to make a
     * rotation. This is needed, because otherwise, the values would
     * be wrong.
     * 
     * @param cell The cell to rotate
     * @param rotation The rotation vector.
     * @return Returns a quaternion containing the rotation.
     */
    protected Quaternion setStandardRotation(Cell cell, Vector3f rotation){
        
        float rotationX = (float) Math.toRadians(rotation.x);
        float rotationZ = (float) Math.toRadians(rotation.z);
        float rotationY = (float) Math.toRadians(rotation.y);
        
        return new Quaternion(new float[] { rotationX, rotationY, rotationZ });
    }

    @Override
    public double getScale(double scale) {
        return scale;
    }
    
}
