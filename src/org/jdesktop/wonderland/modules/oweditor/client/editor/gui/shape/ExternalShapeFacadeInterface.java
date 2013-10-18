package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.TranslatedObjectInterface;

public interface ExternalShapeFacadeInterface {

    public void getDataUpdate(TranslatedObjectInterface dataObject);

    public void removeShape(long id);

    public void translateShape(long id, int x, int y);

    public void changeShape(long id, int x, int y, String name);

    public void clearCurSelection();

    public boolean checkCollision();

    public void clearDraggingShapes();

    public void saveDraggingShapes();

    public void translateShapeNormal(int x, int y, Point start);
    
    public void translateShapeCopy(int x, int y, Point start);

    public void setTranslatedShapes();

    public ArrayList<ShapeObject> getAllShapes();

    public void setSelected(ShapeObject shape, boolean selected);

    public boolean isMouseInObject(Point p);

    public ArrayList<ShapeObject> getDraggingShapes();

    public boolean isCurrentlySelected(ShapeObject shape);

    public ShapeObject getShapeSuroundingPoint(Point p);

    public void selectionRectShiftReleased();

    public void removeSelectionRect();

    public void resizeSelectionRect(Point start, Point end);

    public void selectionRectReleased();

    public void switchSelection(ShapeObject shape);

    public void deleteCurrentSelection();

    public void initilaizeCopy();

    public Point getSelectionCenter();

    public void drawShapes(Graphics2D g2, AffineTransform at, double scale);

    public ArrayList<ShapeObject> getUpdateShapes();

    public ArrayList<ShapeObject> getTranslatedShapes();

    public void createDraggingShapes();

    public void createCopyShapes();

}
