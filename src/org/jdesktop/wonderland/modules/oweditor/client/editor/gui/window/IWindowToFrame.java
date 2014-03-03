package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public interface IWindowToFrame {
    
    /**
     * Load a kmz throught the adapter.
     * 
     * @param url The kmz url.
     * @return An integer array containing the following information:
     * int[0]: the width of the kml object.
     * int[1]: the height of the kml object.
     */
    public int[] loadKMZ(String url);
    
    /**
     * Checks, whether the name already exists or not.
     * 
     * @param moduleName The name to be checked.
     * @param serverName The name of the selected server.
     * @return True, if a name conflict exists,
     * false otherwise.
     */
    public boolean importCheckName(String moduleName, String serverName);

    /**
     * Imports the loaded kmz model
     * 
     * @param name The name of the new model
     * @param image_url The url of the image representation
     * @param x The x coordinate of the new model.
     * @param y The y coordinate of the new model.
     * @param z The z coordinate of the new model.
     * @param rotationX The x rotation of the new model.
     * @param rotationY The y rotation of the new model.
     * @param rotationZ The z rotation of the new model.
     * @param scale The scale of the new model.
     * 
     * @return Return true, if creation is possible, false,
     * if the same name already exists.
     */
    public boolean importKMZ(String name, String image_url, double x,
            double y, double z, double rotationX, double rotationY, double rotationZ,
            double scale);
    
    /**
     * Cancels the import.
     */
    public void cancelImport();
    
    /**
     * Creates a dragging object which lets the user pick a location with it.
     * 
     * @param width The shapes width.
     * @param height The shapes height.
     * @param rotation The shapes rotation.
     * @param scale The shapes scale.
     */
    public void chooseLocation(int width, int height, double rotation,
            double scale);

    /**
     * Draws all the shapes.
     * 
     * @param g2 A graphics2d instance.
     * @param at A affine transform instance.
     */
    public void drawShapes(Graphics2D g2, AffineTransform at);

    /**
     * Initiates an undo.
     */
    public void undo();
    
    /**
     * Initiates a redo.
     */
    public void redo();

    /**
     * Returns the serverlist.
     * 
     * @return  A string array containing all server.
     */
    public String[] getServerList();

    /**
     * Searches, if an image with the name exists on the servern.
     * 
     * @param name The name of the image file.
     * @return True, if it exists, false otherwise.
     */
    public boolean imageExists(String name);

}
