package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.util.ArrayList;

public interface InternalShapeMediatorInterface {
    
    public void registerShapeManager(ShapeManager sm);
    public void registerTranslationManager(ShapeTranslationManager stm);
    public void registerSelectionManager(ShapeSelectionManager ssm);
    
    public ArrayList<ShapeObject> getAllShapes();
    
    public ArrayList<ShapeObject> getSelectedShapes();
    
    public void clearDraggingShapes();

    public double getScale();

    public void translateDraggingShapes(double distance_x, double distance_y);

    public void createDraggingShapes(ArrayList<ShapeObject> selectedShapes);
    
    public void repaint();
    

    public void createSelectionRect(int x, int y, int width, int height);

    public ArrayList<ShapeObject> getShapesInSelectionRect();

    /**
     * gc
     * @param id
     */
    public void setObjectRemoval(long id);

    public void clearCurSelection();
    
    public ArrayList<ShapeObject> getDraggingShapes();
    
    public SimpleShapeObject getSelectionRectangle();
    
    public ShapeObject getShape(long id);
    
    public ShapeBorder getShapeBorder();
}
