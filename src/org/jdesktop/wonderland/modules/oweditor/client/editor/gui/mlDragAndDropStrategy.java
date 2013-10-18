package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.awt.Shape;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape.ShapeObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape.sCollisionNotSelectedStrategy;

/**
 * This mouse listener strategy is used for dragging
 * objects and creates the dragging shapes in the 
 * ShapeManager.
 * 
 * @author Patrick
 *
 */
public class mlDragAndDropStrategy implements mlMouseStrategy{

    private GUIController controller;
    private Point start = new Point();
    private boolean dragging = false;
    
    public mlDragAndDropStrategy(GUIController contr){
        controller = contr;
    }
    
    /**
     * Selects a shape, if the mouse pointer is in one
     * and activates dragging.
     */
    @Override
    public void mousePressed(Point p) {

        ArrayList<ShapeObject> shapes = controller.esmi.getAllShapes();
        
        for(ShapeObject shape_obj : shapes){
            
            Shape shape = shape_obj.getTransformedShape();
            
            if(shape.contains(p)) {
                 if(!shape_obj.isSelected()){
                     controller.esmi.clearCurSelection();
                     controller.esmi.setSelected(shape_obj, true);
                 }
                 controller.esmi.createDraggingShapes();
                 controller.drawingPan.repaint();
            }
        }
        
        if(controller.esmi.isMouseInObject(p)){
            start.x = p.x;
            start.y = p.y;
            dragging = true;
        }
    }

    @Override
    public void mouseReleased(Point p) {
        dragging = false;
        
        if(!controller.esmi.checkCollision()){
            controller.esmi.saveDraggingShapes();
            controller.setTranslationUpdate();
        }
        controller.esmi.clearDraggingShapes();
        controller.drawingPan.repaint();
    }

    @Override
    public void mouseDragged(Point p) {
        if(dragging) {
            
            controller.esmi.translateShapeNormal(p.x, p.y, start);
            start.x = p.x;
            start.y = p.y;
        }
    }

    @Override
    public void mouseMoved(Point p) {
        // TODO Auto-generated method stub
        
    }

}
