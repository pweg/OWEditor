package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

public class ExternalFrameToShape implements ExternalFrameToShapeInterface{
    

    private WindowDrawingPanel drawingPan = null;
    
    public ExternalFrameToShape(WindowDrawingPanel drawingPan){
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
