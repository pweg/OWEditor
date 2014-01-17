package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;


public class FrameToGraphic implements FrameToGraphicInterface{
    

    private WindowDrawingPanel drawingPan = null;
    
    public FrameToGraphic(WindowDrawingPanel drawingPan){
        this.drawingPan = drawingPan;
    }

    @Override
    public void repaint() {
        drawingPan.repaint();
    }
}
