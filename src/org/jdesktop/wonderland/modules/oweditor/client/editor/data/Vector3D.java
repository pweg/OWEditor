package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

/**
 * A simple vector class containing x, y, z coordinates.
 * 
 * @author Patrick
 *
 */
public class Vector3D {
    
    protected int x = 0;
    protected int y = 0;
    protected int z = 0;
    
    /**
     * Creates a new vector instance.
     * 
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param z the z coordinate.
     */
    public Vector3D(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
