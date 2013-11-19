package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.util.logging.Logger;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.TranslatedObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.DataObjectObserverInterface;

/**
 * Implements the DataObjectObserverInterface and is used
 * to get updates on data objects. 
 * 
 * @author Patrick
 *
 */
public class DataObjectObserver implements 
                                DataObjectObserverInterface{

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
    public void notifyCreation(TranslatedObjectInterface dataObject) {
        gc.shape.createShape(dataObject);
        gc.frame.repaint();
    }

    @Override
    public void notifyRemoval(long id) {
        gc.shape.removeShape(id);
        gc.frame.repaint();
        
    }

    @Override
    public void notifyTranslation(long id, int x, int y) {
        gc.shape.updateShapeCoordinates(id, x, y);
        gc.frame.repaint();
    }

    @Override
    public void notifyChange(long id, int x, int y, String name) {
        gc.shape.updateShape(id, x, y, name);
        gc.frame.repaint();  
    }

    @Override
    public void notifyRotation(long id, double rotation) {
        gc.shape.updateShapeRotation(id, rotation);
        gc.frame.repaint();
    }

}
