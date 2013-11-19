package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

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

}
