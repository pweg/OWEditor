package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

public class stateDraggingShapeTranslation implements stateDraggingShape{

    @Override
    public int getX(ShapeDraggingObject shape, double scale) {
        return (int) Math.round(shape.getShape().getBounds().x/scale);
    }

    @Override
    public int getY(ShapeDraggingObject shape, double scale) {
        return (int) Math.round(shape.getShape().getBounds().y/scale);
    }

}
