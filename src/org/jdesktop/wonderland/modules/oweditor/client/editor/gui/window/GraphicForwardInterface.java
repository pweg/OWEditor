package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.image.BufferedImage;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.ITransformedObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.IGraphicToInput;

/**
 * This is the interface, which forwards data changes  from the gui package
 * to the graphics package.
 * 
 * @author Patrick
 *
 */
public interface GraphicForwardInterface {

    /**
     * Returns the graphic interface for the input package.
     * 
     * @return The interface.
     */
    public IGraphicToInput getGraphicInputInterface();

    /**
     * Creates a new shape object.
     * 
     * @param dataObject A data object containing information
     * about the shape to create.
     */
    public void createShape(ITransformedObject dataObject);

    /**
     * Updates a shape object.
     * 
     * @param id The id of the object.
     * @param x The x coordinate of the object.
     * @param y The y coordinate of the object.
     * @param name The name of the object.
     */
    public void updateShape(long id, int x, int y, String name);

    /**
     * Deletes a shape.
     * 
     * @param id The id of the shape.
     */
    public void removeShape(long id);

    /**
     * Updates the coordinates of a shape.
     * 
     * @param id The id of the shape.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void updateShapeCoordinates(long id, int x, int y);

    /**
     * Updates the rotation of a shape.
     * 
     * @param id The id of the shape.
     * @param rotation The rotation.
     */
    public void updateShapeRotation(long id, double rotation);

    /**
     * Updates the scale of a shape.
     * 
     * @param id The id of the shape.
     * @param scale The scale.
     */
    public void updateShapeScale(long id, double scale);
    
    /**
     * Updates an image of a shape.
     * 
     * @param id The id of the shape.
     * @param img The image.
     */
    public void updateImage(long id, BufferedImage img);

    /**
     * Updates the name of an object.
     * 
     * @param id The id of the object.
     * @param name The new name.
     */
    public void updateName(Long id, String name);
}
