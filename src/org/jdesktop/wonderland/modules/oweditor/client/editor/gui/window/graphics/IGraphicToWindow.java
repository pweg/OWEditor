package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.ITransformedObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToGraphic;

/**
 * Interface for the window package.
 * 
 * @author Patrick
 *
 */
public interface IGraphicToWindow {
    
    /**
     * Draws all shape objects. 
     * 
     * @param g2 A Graphics2D instance.
     * @param at A affine transform instance.
     */
    public void drawShapes(Graphics2D g2, AffineTransform at);
    
    /**
     * Registers the frame interface to the graphics package.
     * 
     * @param frameInterface A interface instance.
     */
    public void registerWindowInterface(IWindowToGraphic frameInterface);

    /**
     * Returns the interface for the frame package.
     * 
     * @return The frameinterfaceInstance.
     */
    public IGraphicToWindow getFrameInterface();
    
    /**
     * Returns the interface for the input package.
     * 
     * @return The inputfacadeInstance.
     */
    public IGraphicToInput getInputInterface();

    /**
     * Creates a new shape.
     * 
     * @param dataObject The data object containing all information
     * for the shape to be created.
     */
    public void createShape(ITransformedObject dataObject);
    
    /**
     * Creates a new dragging rectangle. 
     * Note that the shape will not have a valid id, because it is
     * no copy of normal, existing shapes.
     * 
     * @param width The width of the shape.
     * @param height The height of the shape.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param rotation The rotation.
     * @param scale The scale.
     */
    public void createDraggingRect(int width, int height, int x, int y, double rotation,
            double scale);
    
    /**
     * Creates a new dragging circle. 
     * Note that the shape will not have a valid id, because it is
     * no copy of normal, existing shapes.
     * 
     * @param width The width of the shape.
     * @param height The height of the shape.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param rotation The rotation.
     * @param scale The scale.
     */
    public void createDraggingCircle(int width, int height, int x, int y, double rotation,
            double scale);

    /**
     * Removes a specific shape.
     * 
     * @param id The shape id.
     */
    public void removeShape(long id);

    /**
     * Updates only the coordinates of a shape.
     * 
     * @param id The id of the shape.
     * @param x The new x coordinate.
     * @param y The new y coordinate.
     * @param z The new z coordinate.
     */
    public void updateShapeCoordinates(long id, int x, int y, double z);

    /**
     * Updates the name of a shape.
     * 
     * @param id The id of the shape.
     * @param name The new name.
     */
    public void updateShapeName(long id, String name);

    /**
     * Updates the rotation of a specific shape.
     * 
     * @param id The id of the shape.
     * @param rotation The new rotation.
     */
    public void updateShapeRotation(long id, double rotation);

    /**
     * Updates the scale of a specific shape.
     * 
     * @param id The id of the shape.
     * @param scale The new scale of the shape.
     */
    public void updateShapeScale(long id, double scale);

    /**
     * Returns the coordinates of the dragging shapes.
     * 
     * @return Point containing the coordinates.
     */
    public Point getDraggingCoords();

    /**
     * Updates the representation image of a specific shape.
     * 
     * @param id The id of the shape.
     * @param imgName The new image name.
     * @param dir The new images directory.
     */
    public void updateShapeImage(long id, String imgName, String dir);

    /**
     * Selects all existing shapes.
     */
    public void selectAllShapes();
    
    /**
     * Sends a shape to the background or the foreground.
     * 
     * @param b If true, the shape becomes background,
     * if false, it becomes foreground.
     */
    public void setBackground(boolean b);

    /**
     * Returns the ids of all selected shapes.
     * 
     * @return The ids in an array list.
     */
    public ArrayList<Long> getSelectedShapes();


    /**
     * Returns all ids, whether in the background, 
     * or foreground.
     * 
     * @return An arraylist containing all ids.
     */
    public ArrayList<Long> getAllIDs();

    /**
     * Returns the name of a shape.
     * 
     * @param id The id of the shape.
     * @return The name of the shape.
     */
    public String getShapeName(long id);

}
