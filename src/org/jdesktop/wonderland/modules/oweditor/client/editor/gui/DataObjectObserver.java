package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.DataObjectObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.ServerUpdateAdapter;

public class DataObjectObserver implements 
                                DataObjectObserverInterface{

        private static final Logger LOGGER =
            Logger.getLogger(DataObjectObserver.class.getName());
    private GUIController gc = null;
    
    public DataObjectObserver(GUIController gc){
        this.gc = gc;
    }
    
    @Override
    public void notify(DataObjectInterface dataObject) {
        gc.sm.setDataUpdate(dataObject);
        gc.drawingPan.repaint();
    }

    @Override
    public void notifyRemoval(long id) {
        gc.sm.removeShape(id);
        gc.drawingPan.repaint();
        
    }

}
