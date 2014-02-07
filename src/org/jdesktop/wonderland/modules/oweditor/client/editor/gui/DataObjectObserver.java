package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.util.logging.Logger;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.ITransformedObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.IDataObjectObserver;

/**
 * Implements the DataObjectObserverInterface and is used
 * to get updates on data objects. 
 * 
 * @author Patrick
 *
 */
public class DataObjectObserver implements 
                                IDataObjectObserver{

    private static final Logger LOGGER =
            Logger.getLogger(DataObjectObserver.class.getName());
    private GUIController gc = null;
    
    /**
     * Creates a new dataObjectObserver instance.
     * 
     * @param gc a GUIController instance.
     */
    public DataObjectObserver(GUIController gc){
        this.gc = gc;
    }
    
    @Override
    public void notifyCreation(ITransformedObject dataObject) {
        gc.window.createShape(dataObject);
        gc.window.repaint();
    }

    @Override
    public void notifyRemoval(long id) {
        gc.window.removeShape(id);
        gc.window.repaint();
        
    }

    @Override
    public void notifyTranslation(long id, int x, int y) {
        gc.window.updateShapeCoordinates(id, x, y);
        gc.window.repaint();
    }

    @Override
    public void notifyChange(long id, int x, int y, String name) {
        gc.window.updateShape(id, x, y, name);
        gc.window.repaint();  
    }

    @Override
    public void notifyRotation(long id, double rotation) {
        gc.window.updateShapeRotation(id, rotation);
        gc.window.repaint();
    }

    @Override
    public void notifyScaling(long id, double scale) {
        gc.window.updateShapeScale(id, scale);
        gc.window.repaint();
    }

}
