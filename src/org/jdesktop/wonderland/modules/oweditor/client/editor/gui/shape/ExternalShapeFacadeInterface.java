package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.TranslatedObjectInterface;

public interface ExternalShapeFacadeInterface {

    public void createShape(TranslatedObjectInterface dataObject);

    public void updateShape(long id, int x, int y, String name);

    public void removeShape(long id);

    public void clearCurSelection();

    public ShapeObject getShapeSuroundingPoint(Point p);

    public boolean isMouseInObject(Point p);

    public void selectionUpdate(Point start, Point end);

    public boolean selectionSwitch(Point p);

    public void deleteCurrentSelection();
    
    public void drawShapes(Graphics2D g2, AffineTransform at, double scale);

    public void updateShapeCoordinates(long id, int x, int y);

    public void translation(int x, int y, Point start);

    public void translationSetUpdate();

    /**
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

    public void pasteInitialize();
    
    public void pasteTranslate(int x, int y, Point start);
    
    public void pasteInsertShapes();

    public void translationInitialization(Point p);

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

    public void selectionReleased();

    public void cleanAll();
    
    public void cleanHelpingShapes();

    public void rotationInitialize();
    
    public void isMouseInBorder(Point p);
    
    public void rotate(Point p);
    
    public void rotateSetUpdate();

    public void updateShapeRotation(long id, int x, int y, double rotation);


}
