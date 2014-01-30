package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.IAdapterCommunication;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.IGraphicToWindow;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.IInputToWindow;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames.FrameController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames.IFrame;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.menu.IMenu;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.menu.MenuController;

public class WindowController {

    //protected JScrollPane mainScrollPanel = null;
    //protected DrawingPanel drawingPan = null;
    
    protected IInputToWindow input = null;

    protected IGraphicToWindow graphic = null;
    
    //protected MainFrame mainframe = null;

    protected WindowToInput inputInterface = null;
    protected WindowToFrame frameInterface = null;
    
    protected MouseCoordinates mouseCoords = null;
    protected IMenu menu = null;
    protected Window window = null;
    protected IFrame frame = null;
    
    protected IAdapterCommunication adapter = null;
    
    public WindowController(IAdapterCommunication adapter, Window mainInterface){
        
        this.window = mainInterface;
        this.adapter = adapter;
        
        

        //Create graphics package
        graphic = new GraphicController();
        frame = new FrameController();
        menu = new MenuController();
        
        
        //Create interfaces needed for later
        inputInterface = new WindowToInput(this);
        frameInterface = new WindowToFrame(this);
        
        mouseCoords = new MouseCoordinates();
        
        
        registerComponents();
        build();
        
        //Creating menu
    }

    private void registerComponents(){
        graphic.registerWindowInterface(new WindowToGraphic(this, adapter));
        
        //mainInterface.registerComponents(drawingPan, mainframe);
        window.registerGraphicInterface(graphic);
        window.registerFrameInterface(frame);
        
        //frame.registerMainFrame(mainframe);
        frame.registerWindow(frameInterface);
        
        inputInterface.registerDrawingPan(frame.getDrawingPan());
        
        menu.registerWindowInterface(new WindowToMenu(this));
    }
    
    private void build(){
        mouseCoords.setToolBar(frame.getBottomToolBar());
        frame.setJMenuBar(menu.buildMenubar());
    }

    public void repaint() {
        frame.repaint();
    }

    public void registerDataManager(DataObjectManagerGUIInterface dm) {
        mouseCoords.registerDataManager(dm);
        frameInterface.registerDataManager(dm);
    }

    public void registerInputInterface(IInputToWindow input) {
        this.input = input;
    }
}
