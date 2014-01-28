package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import javax.swing.JScrollPane;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.IAdapterCommunication;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.IGraphicToWindow;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.IInputToWindow;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames.FrameController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames.IFrame;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.menu.MenuController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.menu.IMenu;

public class WindowController {

    protected JScrollPane mainScrollPanel = null;
    protected DrawingPanel drawingPan = null;
    
    protected IInputToWindow input = null;

    protected IGraphicToWindow graphic = null;
    
    protected MainFrame mainframe = null;

    protected WindowToInput inputInterface = null;
    
    protected MouseCoordinates mouseCoords = null;
    protected IMenu menu = null;
    protected Window mainInterface = null;
    protected IFrame frame = null;
    
    protected IAdapterCommunication adapter = null;
    
    public WindowController(IAdapterCommunication adapter, Window mainInterface){
        
        this.mainInterface = mainInterface;
        this.adapter = adapter;
        
        drawingPan = new DrawingPanel(this);
        mainScrollPanel = new JScrollPane(drawingPan);
        mainScrollPanel.setWheelScrollingEnabled(false);


        mainframe = new MainFrame(mainScrollPanel);

        //Create graphics package
        graphic = new GraphicController();
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
        graphic.registerWindowInterface(new WindowToGraphic(drawingPan, adapter));
        
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
        inputInterface.registerDataManager(dm);
    }

    public void registerInputInterface(IInputToWindow input) {
        this.input = input;
    }
}
