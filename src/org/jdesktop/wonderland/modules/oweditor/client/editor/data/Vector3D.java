package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

/**
 * A simple vector class containing x, y, z coordinates.
 * 
 * @author Patrick
 *
 */
public class Vector3D {
    
    protected float x = 0;
    protected float y = 0;
    protected float z = 0;
    
    /**
     * Creates a new vector instance.
     * 
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param z the z coordinate.
     */
    public Vector3D(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
