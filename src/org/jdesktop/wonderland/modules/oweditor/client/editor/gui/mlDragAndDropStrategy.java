package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class mlDragAndDropStrategy implements MouseStrategy{

    private GUIController controller;
    private Point start = new Point();
    private boolean dragging = false;
    
    public mlDragAndDropStrategy(GUIController contr){
        controller = contr;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        Point p = e.getPoint();

        ArrayList<ShapeObject> shapes = controller.sm.getShapes();
        
        for(ShapeObject shape_obj : shapes){
            
            Shape shape = shape_obj.getTransformedShape();
            
            if(shape.contains(p)) {
                 if(!shape_obj.isSelected()){
                     controller.samm.removeCurSelection();
                     controller.samm.setSelected(shape_obj, true);
                     controller.drawingPan.repaint();
                 }
            }
        }
        
        
        ShapeObject shape = controller.sm.getShapeSuroundingPoint(p);
        
        if(shape != null){
            start.x = p.x;
            start.y = p.y;
            dragging = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragging = false;
        
        if(!controller.samm.collision){
            controller.sm.saveDraggingShapes();
            controller.setTranslationUpdate();
        }
        controller.sm.clearDraggingShapes();
        controller.drawingPan.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(dragging) {
            controller.samm.translateShape(e.getX(), e.getY(), start);
            start.x = e.getX();
            start.y = e.getY();
        }
    }

}
