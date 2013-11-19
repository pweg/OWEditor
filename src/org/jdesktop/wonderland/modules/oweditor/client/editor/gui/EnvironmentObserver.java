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
        gc.frame.setNewWidth(width);
    }

    @Override
    public void notifyHeightChange(int height) {
        gc.frame.setNewHeight(height);
    }

    @Override
    public void notifyMinXChange(int x) {

        int translation = gc.frame.getTranslationX();
        
        gc.frame.setNewMinX(x);
        translation -= gc.frame.getTranslationX();

        gc.input.notifyMinXChange(translation);
        
        
    }

    @Override
    public void notifyMinYChange(int y) {
        
        int translation = gc.frame.getTranslationY();
        
        gc.frame.setNewMinY(y);
        translation -= gc.frame.getTranslationY();
        
        gc.input.notifyMinYChange(translation);
       
    }
    

}
