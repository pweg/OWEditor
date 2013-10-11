package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.util.logging.Logger;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectInterface;
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
    public void notify(DataObjectInterface dataObject) {
        gc.sm.getDataUpdate(dataObject);
        gc.drawingPan.repaint();
    }

    @Override
    public void notifyRemoval(long id) {
        gc.sm.removeShape(id);
        gc.drawingPan.repaint();
        
    }

}
