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

    public void translationSave();

    public void createCopyShapes();

    /**
     * 
     * @return the point in the center of the copy
     */
    public Point copyInitialize();
    
    public void copyTranslate(int x, int y, Point start);
    
    public void copySave();

    public void translationInitialization(Point p);

    public void popupInitialize(Point p);

    public void selectionReleased();

    public void clean();


}
