package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.DraggingObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.ShapeObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.SimpleShapeObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.ITransformationBorder;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToGraphic;

public interface IInternalMediator {
    
    public void registerShapeManager(ShapeManager sm);
    public void registerTranslationManager(TranslationManager stm);
    public void registerSelectionManager(SelectionManager ssm);
    
    public ArrayList<ShapeObject> getAllShapes();
    
    public ArrayList<ShapeObject> getSelectedShapes();
    
    public void clearDraggingShapes();

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
    
    public ArrayList<DraggingObject> getDraggingShapes();
    
    public SimpleShapeObject getSelectionRectangle();
    
    public ShapeObject getShape(long id);
    
    public ITransformationBorder getShapeBorder();
    
    public void registerWindowInterface(
            IWindowToGraphic frameInterface);
}
