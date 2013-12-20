package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.awt.Point;

public class FrameToGraphic implements FrameToGraphicInterface{
    

    private WindowDrawingPanel drawingPan = null;
    
    public FrameToGraphic(WindowDrawingPanel drawingPan){
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
        return drawingPan.revertBack(p);
    }

}
