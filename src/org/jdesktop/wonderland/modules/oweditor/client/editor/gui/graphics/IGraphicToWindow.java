package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.TranslatedObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToGraphic;

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
    public void createShape(TranslatedObjectInterface dataObject);

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
     */
    public void updateShapeCoordinates(long id, int x, int y);

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

}
