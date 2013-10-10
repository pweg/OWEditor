package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.EnvironmentObserverInterface;

/**
 * Implements the EnvironmentObserverInterface and is used to
 * get updates on environment changes.
 * 
 * @author Patrick
 *
 */
public class EnvironmentObserver implements EnvironmentObserverInterface{

    private GUIController gc = null;
            
    /**
     * Creates a new environmentObserver instance.
     * 
     * @param gc a GUIController instance.
     */
    EnvironmentObserver(GUIController gc){
        this.gc = gc;
    }
    
    @Override
    public void notifyWidthChange(int width) {
        gc.drawingPan.setNewWidth(width);
    }

    @Override
    public void notifyHeightChange(int height) {
        gc.drawingPan.setNewHeight(height);
    }

    @Override
    public void notifyMinXChange(int x) {
        gc.drawingPan.setNewMinX(x);
    }

    @Override
    public void notifyMinYChange(int y) {
        gc.drawingPan.setNewMinY(y);
    }
    

}
