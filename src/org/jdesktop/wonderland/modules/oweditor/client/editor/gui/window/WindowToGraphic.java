package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;


public class WindowToGraphic implements WindowToGraphicInterface{
    

    private DrawingPanel drawingPan = null;
    
    public WindowToGraphic(DrawingPanel drawingPan){
        this.drawingPan = drawingPan;
    }

    @Override
    public void repaint() {
        drawingPan.repaint();
    }
}
