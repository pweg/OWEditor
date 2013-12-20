package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import javax.swing.JScrollPane;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicToFrameInterface;

public class FrameController {

    protected JScrollPane mainScrollPanel = null;
    protected WindowDrawingPanel drawingPan = null;

    protected GraphicToFrameInterface graphic = null;
    protected WindowPopupMenu popupMenu = null;
    
    protected MainFrame frame = null;

    protected FrameToGraphicInterface graphicInterface = null;
    protected FrameToInputInterface inputInterface = null;
    
    protected MouseCoordinates mouseCoords = null;
    
    public FrameController(){
        
        drawingPan = new WindowDrawingPanel(this);
        mainScrollPanel = new JScrollPane(drawingPan);
        mainScrollPanel.setWheelScrollingEnabled(false);

        frame = new MainFrame(mainScrollPanel);
        popupMenu = new WindowPopupMenu();
        
        graphicInterface = new FrameToGraphic(drawingPan);
        inputInterface = new FrameToInput(this);
        
        mouseCoords = new MouseCoordinates();
        mouseCoords.setToolBar(frame.getBottomToolBar());
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

    public void setCoordinateTranslator(CoordinateTranslatorInterface coordinateTranslator) {
        mouseCoords.setCoordinateTranslator(coordinateTranslator);
    }
   
}
