package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import javax.swing.JScrollPane;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.AdapterCommunicationInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicToWindowInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.InputToMenuInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames.FrameController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames.FrameInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.menu.MenuController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.menu.MenuInterface;

public class WindowController {

    protected JScrollPane mainScrollPanel = null;
    protected DrawingPanel drawingPan = null;
    
    protected InputToMenuInterface input = null;

    protected GraphicToWindowInterface graphic = null;
    
    protected MainFrame mainframe = null;

    protected WindowToInputInterface inputInterface = null;
    
    protected MouseCoordinates mouseCoords = null;
    protected MenuInterface menu = null;
    protected Window mainInterface = null;
    protected FrameInterface frame = null;
    
    public WindowController(AdapterCommunicationInterface adapter, Window mainInterface){
        
        this.mainInterface = mainInterface;
        
        drawingPan = new DrawingPanel(this);
        mainScrollPanel = new JScrollPane(drawingPan);
        mainScrollPanel.setWheelScrollingEnabled(false);


        mainframe = new MainFrame(mainScrollPanel);

        //Create graphics package
        graphic = new GraphicController(adapter);
        frame = new FrameController();
        menu = new MenuController();
        
        
        //Create interfaces
        inputInterface = new WindowToInput(this);
        
        mouseCoords = new MouseCoordinates();
        mouseCoords.setToolBar(mainframe.getBottomToolBar());
        
        registerComponents();
        
        //Creating menu
        mainframe.setJMenuBar(menu.buildMenubar());
    }

    private void registerComponents(){
        graphic.registerFrameInterface(new WindowToGraphic(drawingPan));
        
        mainInterface.registerComponents(drawingPan, mainframe);
        mainInterface.registerGraphicInterface(graphic);
        
        frame.registerMainFrame(mainframe);
        frame.registerWindow(new WindowToFrame(this));
        
        menu.registerWindowInterface(new WindowToMenu(this));
        
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

    public void registerInputInterface(InputToMenuInterface input) {
        this.input = input;
        menu.registerInputInterface(input);
    }
}
