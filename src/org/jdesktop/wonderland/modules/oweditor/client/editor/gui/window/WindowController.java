package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.IAdapterCommunication;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.IDataManager;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.IInputToWindow;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames.FrameController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames.IFrame;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.GraphicController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.IGraphicToWindow;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.menu.IMenu;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.menu.MenuController;

/**
 * This is the controller for the window package
 * 
 * @author Patrick
 *
 */
public class WindowController {
    
    protected IInputToWindow input = null;

    protected IGraphicToWindow graphic = null;
    protected WindowToGraphic graphicInterface;

    protected WindowToInput inputInterface = null;
    protected WindowToFrame frameInterface = null;
    
    protected MouseCoordinates mouseCoords = null;
    protected IMenu menu = null;
    protected Window window = null;
    protected IFrame frame = null;

    protected IDataManager dm = null;
    
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
        graphicInterface = new WindowToGraphic(this);
        
        mouseCoords = new MouseCoordinates(this);
        
        registerComponents();
        build();
    }

    private void registerComponents(){
        graphic.registerWindowInterface(graphicInterface);
        window.registerGraphicInterface(graphic);        
        frame.registerWindow(frameInterface);
        
        menu.registerWindowInterface(new WindowToMenu(this));
    }
    
    /**
     * Setup for menus.
     */
    private void build(){
        frame.setJMenuBar(menu.getMenubar());
    }

    /**
     * Repaints the drawing panel.
     */
    public void repaint() {
        frame.repaint();
    }

    public void registerDataManager(IDataManager dm) {
        this.dm = dm;
        frameInterface.registerDataManager(dm);
    }

    public void registerInputInterface(IInputToWindow input) {
        this.input = input;
        window.addMouseListener(input.getMouseListener());
        window.addKeyListener(input.getKeyListener());
        window.addMouseWheelListener(input.getMouseWheelListener());
    }
}
