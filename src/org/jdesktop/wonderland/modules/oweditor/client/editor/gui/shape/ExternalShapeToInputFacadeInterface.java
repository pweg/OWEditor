package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Point;

public interface ExternalShapeToInputFacadeInterface {
    
    
    /**
     * Initializes the translation: dragging shapes are 
     * created (these are the shape borders that can be
     * seen when translating; the actual shapes are only
     * moved when the translation finished)
     * 
     * @param p the point p is important for the selection.
     * if the point is in an unselected shape, only this 
     * shape will be translated, otherwise the whole selection
     * will be moved.
     */
    public void translateIntialize(Point p);
    
    /**
     * This moves the before created dragging shapes from a
     * starting point to the given coordinates. (the distance
     * between those two is calculated)
     * 
     * @param x the new x coordinate.
     * @param y the new y coordinate.
     * @param start the starting point of the translation.
     */
    public void translate(int x, int y, Point start);

    /**
     * This is used, when the translation is finished 
     * (the mouse button is released). A collision check
     * is made, whether the shape can be moved to the position.
     */
    public void translateFinished();

    /**
     * Copies shapes of the current selection and
     * also notifies the adapter to backup the server objects.
     *
     * @return the point in the center of the copy
     */
    public Point copyInitialize();
    
    /**
     * Looks for copy shape, whether there were shapes
     * copied or not.
     * 
     * @return true, when there are copied shapes,
     * false otherwise
     */
    public boolean copyShapesExist();

    /**
     * Creates dragging shapes for placing them.
     */
    public void pasteInitialize();
    
    /**
     * Translates the dragging shapes.
     * This method is similar to the normal translate method,
     * but uses a different kind of collision detection, where
     * all shapes, selection included, are marked for collision.
     * 
     * @param x the new x coordinate.
     * @param y the new y coordinate.
     * @param start the starting point of the translation.
     */
    public void pasteTranslate(int x, int y, Point start);
    
    /**
     * Checks for collision, if no one occurred, it sends an
     * update message to the adapter.
     */
    public void pasteFinished();
    
    /**
     * Initializes a rotation of the selected objects.
     * This includes creating dragging shapes, as well as 
     * the border used for rotation.
     */
    public void rotationInitialize();
    
    /**
     * Rotates to the angle given by the point p
     * and the rotation center.
     * 
     * @param p a point to calculate the rotation angle.
     */
    public void rotate(Point p);
    
    /**
     * Sets the update for the adapter, for 
     * the rotated objects.
     */
    public void rotateFinished();

    /**
     * Moves the rotation center.
     * 
     * @param start the start position.
     * @param end the new position.
     */
    public void rotationCenterTranslate(Point start, Point end);
    
    /**
     * Updates the rotation center for all dragging shapes
     * after it is moved.
     */
    public void rotationCenterUpdate();

    /**
     * This is used for one rotation cycle finished, but the whole
     * rotation is not finished yet. It sets the border to the new
     * position. This is needed in order for the border to not go
     * haywire when the rotation center is moved after a rotation.
     */
    public void rotationUpdate();

    public void scaleInitialize();

    public void scale(Point p);

    public void scaleUpdate();

    public void scaleFinished();
    
    /**
     * Initializes the selection for the 
     * popup menu.
     * 
     * @param p the mouse point, where the popup menu
     * is going to get drawn.
     * 
     * @return true, if there is are shapes selected,
     * false otherwise. This is used for menu items to
     * be enabled/disabled.
     * 
     */
    public boolean popupInitialize(Point p);
    
    /**
     * Clears all additional objects, like helping shapes
     * (selection rectangle and dragging shapes), as well as
     * the border for rotation and resize. 
     * 
     * The selection stays though.
     */
    public void cleanAll();
    
    /**
     * Clears all helping shapes, like selection rectangle,
     * and dragging shapes.
     */
    public void cleanHelpingShapes();

    /**
     * Clears the current selection, which means no shapes
     * are selected after this.
     */
    public void clearCurSelection();

    /**
     * Deletes all shapes, which are currently selected.
     */
    public void deleteCurrentSelection();

    /**
     * Switches the selection of a shape, which surrounds
     * the point p.
     * 
     * @param p the point in question.
     * 
     * @return returns true, if a shape was actually found, 
     * surrounding p, false otherwise.
     */
    public boolean selectionSwitch(Point p);
    
    /**
     * Resizes the selection rectangle.
     * 
     * If no selection rectangle instance is available, a new instance
     * is created.
     * 
     * @param start the top point of the selection rectangle.
     * @param end the bottom point of the selection rectangle.
     * 
     */
    public void selectionRectUpdate(Point start, Point end);
    
    /**
     * This should be called, when the mouse button holding selection rectangle
     * is released. 
     * 
     * It switches selection of the shapes in the selection rectangle and
     * removes the selection rectangle.
     */
    public void selectionRectFinished();

    /**
     * Looks, if a point p is surrounded by a shape.
     * 
     * @param p the point in question
     * @return true, if the point is in a shape, else
     * otherwise.
     */
    public boolean isMouseInObject(Point p);

    /**
     * Looks, if a point p is in the edges of the
     * border, which is used when doing rotation
     * or scaling.
     * 
     * @param p the point.
     * @return true, if the point is in the border edges,
     * false otherwise.
     */
    public boolean isMouseInBorder(Point p);
    
    /**
     * Looks, if the point p is in the border center rectangle.
     * The border is used, when doing rotation or scaling.
     * 
     * @param p the point.
     * @return true, if the point is in the border center,
     * false otherwise.
     */
    public boolean isMouseInBorderCenter(Point p);

    /**
     * Returns the name of a shape, which surrounds 
     * the point p, if the name is abbreviated.
     * @param p The point.
     * @return The name of the shape, or null otherwise.
     */
    public String getShapeName(Point p);

    public void paintShapeName(Point p, String name);





}
