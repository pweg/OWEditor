package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.DraggingObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.ShapeObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.SimpleShapeObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.ITransformationBorder;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToGraphic;

/**
 * package internal mediator, which controls the 
 * communication between the classes.
 * 
 * @author Patrick
 *
 */
public interface IInternalMediator {
    
    /**
     * Registers a shape manager instance.
     * 
     * @param sm The shape manager instance.
     */
    public void registerShapeManager(ShapeManager sm);
    
    /**
     * Registers a selection manager instance.
     * 
     * @param ssm The selection manager instance.
     */
    public void registerSelectionManager(SelectionManager ssm);
    
    /**
     * Returns a list of all normal shapes.
     * 
     * @return An arraylist containing all normal shapes,
     * currently existing.
     */
    public ArrayList<ShapeObject> getAllShapes();
    
    /**
     * Returns all selected shapes.
     * 
     * @return An arraylist containing all currently selected shapes.
     */
    public ArrayList<ShapeObject> getSelectedShapes();
    
    /**
     * Removes all dragging shapes.
     */
    public void clearDraggingShapes();
    
    /**
     * Does the repaint, which means all shapes will be repainted.
     */
    public void repaint();

    /**
     * Creates a selection rectangle.
     * 
     * @param x The x coordinate of the rectangle.
     * @param y The y coordinate of the rectangle.
     * @param width The width coordinate of the rectangle.
     * @param height The height coordinate of the rectangle.
     */
    public void createSelectionRect(int x, int y, int width, int height);

    /**
     * Returns a list of shapes, which are in the
     * selection rectangle.
     * 
     * @return An arraylist containing all shapes 
     * which are in the current selection rectangle.
     */
    public ArrayList<ShapeObject> getShapesInSelectionRect();

    /**
     * Sets an object removal message for the adapter.
     * 
     * @param ids The ids of the shapes, which should be removed.
     */
    public void setObjectRemoval(ArrayList<Long> ids);

    /**
     * Clears the current selection, which means all
     * shapes currently selected are unselected.
     */
    public void clearCurSelection();
    
    /**
     * Returns all currently existing dragging shapes.
     * 
     * @return An arraylist containing all dragging shapes.
     */
    public ArrayList<DraggingObject> getDraggingShapes();
    
    /**
     * Returns the current instance of the selection
     * rectangle.
     * 
     * @return The selection rectangle, or null, if no
     * selection rectangle exists currently.
     */
    public SimpleShapeObject getSelectionRectangle();
    
    /**
     * Returns a shape object.
     * 
     * @param id The id of the object.
     * @return A shape object, or null, if the shape
     * with the id was not found.
     */
    public ShapeObject getShape(long id);
    
    /**
     * Returns the shape border, which is the border
     * used for rotation and scaling.
     * 
     * @return An instance of the shape border,
     * or null if there is no border currently active.
     */
    public ITransformationBorder getShapeBorder();
    
    /**
     * Registers the interface from the window package.
     * 
     * @param frameInterface A windowToGraphic instance.
     */
    public void registerWindowInterface(
            IWindowToGraphic frameInterface);
    
    /**
     * Returns an image for the given parameters.
     * 
     * @param name The name of the image.
     * @param dir The user directory of the image.
     * @return The image, or null if it was not found.
     */
    public BufferedImage getImage(String name, String dir);

}
