package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import javax.swing.JScrollPane;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape.ExternalShapeToFrameInterface;

public class FrameController {

    protected JScrollPane mainScrollPanel = null;
    protected WindowDrawingPanel drawingPan = null;
    protected ExternalShapeToFrameInterface shapes = null;
    protected WindowPopupMenu popupMenu = null;
    
    protected MainFrame frame = null;

    protected FrameToShapeInterface shapeInterface = null;
    protected FrameToInputInterface inputInterface = null;
    
    public FrameController(){
        frame = new MainFrame();
        
        drawingPan = new WindowDrawingPanel(this);
        mainScrollPanel = new JScrollPane(drawingPan);
        mainScrollPanel.setWheelScrollingEnabled(false);
        frame.getContentPane().add(mainScrollPanel);
        popupMenu = new WindowPopupMenu();
        
        shapeInterface = new FrameToShape(drawingPan);
        inputInterface = new FrameToInput(this);
    }

    public void repaint() {
        drawingPan.repaint();
    }

    public int getTranslationX() {
        return drawingPan.getTranslationX();
    }

    public int getTranslationY() {
        return drawingPan.getTranslationY();
    }
    
   
}
