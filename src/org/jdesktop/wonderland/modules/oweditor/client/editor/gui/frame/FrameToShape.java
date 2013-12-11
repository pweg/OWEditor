package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

public class FrameToShape implements FrameToShapeInterface{
    

    private WindowDrawingPanel drawingPan = null;
    
    public FrameToShape(WindowDrawingPanel drawingPan){
        this.drawingPan = drawingPan;
    }

    @Override
    public void repaint() {
        drawingPan.repaint();
    }

    @Override
    public double getScale() {
        return drawingPan.getScale();
    }

    @Override
    public Point revertBack(Point p) {
        try {
            Point revert = new Point(0,0);
            AffineTransform at = drawingPan.getTransformation();
            at.inverseTransform(p, revert);
            return revert;
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }
        return null;
    }

}
