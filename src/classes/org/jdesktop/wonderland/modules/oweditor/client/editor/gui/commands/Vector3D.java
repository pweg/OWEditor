package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

/**
 * A simple vector class containing x, y, z coordinates.
 * 
 * @author Patrick
 *
 */
public class Vector3D {
    
    protected double x = 0;
    protected double y = 0;
    protected double z = 0;
    
    /**
     * Creates a new vector instance.
     * 
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param z the z coordinate.
     */
    public Vector3D(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
