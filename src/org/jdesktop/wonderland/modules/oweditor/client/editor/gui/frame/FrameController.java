package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import javax.swing.JScrollPane;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.AdapterCommunicationInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.menu.MenuController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.menu.MenuInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicToFrame;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicToFrameInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.InputToFrameInterface;

public class FrameController {

    protected JScrollPane mainScrollPanel = null;
    protected WindowDrawingPanel drawingPan = null;
    
    protected InputToFrameInterface input = null;

    protected GraphicToFrameInterface graphic = null;
    
    protected MainFrame frame = null;

    protected FrameToGraphicInterface graphicInterface = null;
    protected FrameToInputInterface inputInterface = null;
    
    protected MouseCoordinates mouseCoords = null;
    protected MenuInterface menu = null;
    
    public FrameController(AdapterCommunicationInterface adapter){
        
        drawingPan = new WindowDrawingPanel(this);
        mainScrollPanel = new JScrollPane(drawingPan);
        mainScrollPanel.setWheelScrollingEnabled(false);


        frame = new MainFrame(mainScrollPanel);

        //Create graphics package
        graphic = new GraphicToFrame(adapter);
        
        //Creating menu
        menu = new MenuController();
        frame.setJMenuBar(menu.buildMenubar());
        
        //Create interfaces
        graphicInterface = new FrameToGraphic(drawingPan);
        graphic.registerFrameInterface(graphicInterface);
        
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

    public void registerDataManager(DataObjectManagerGUIInterface dm) {
        mouseCoords.registerDataManager(dm);
    }

    public void registerInputInterface(InputToFrameInterface input) {
        this.input = input;
        menu.registerInputInterface(input);
    }
   
}
