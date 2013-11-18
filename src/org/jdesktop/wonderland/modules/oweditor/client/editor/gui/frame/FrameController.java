package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import javax.swing.JScrollPane;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape.ExternalShapeFacadeInterface;

public class FrameController {

    protected JScrollPane mainScrollPanel = null;
    protected WindowDrawingPanel drawingPan = null;
    protected ExternalShapeFacadeInterface shapes = null;
    protected WindowPopupMenu popupMenu = null;
    protected MainFrame frame = null;
    protected ExternalFrameFacade eff = null;
    
    protected ExternalFrameToShapeInterface efs = null;
    
    public FrameController(){
        frame = new MainFrame();
        
        drawingPan = new WindowDrawingPanel(this);
        mainScrollPanel = new JScrollPane(drawingPan);
        mainScrollPanel.setWheelScrollingEnabled(false);
        frame.getContentPane().add(mainScrollPanel);
        popupMenu = new WindowPopupMenu();
        efs = new ExternalFrameToShape(drawingPan);
    }
    
   
}
