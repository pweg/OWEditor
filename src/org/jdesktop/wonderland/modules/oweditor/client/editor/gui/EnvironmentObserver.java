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
        gc.effi.setNewWidth(width);
    }

    @Override
    public void notifyHeightChange(int height) {
        gc.effi.setNewHeight(height);
    }

    @Override
    public void notifyMinXChange(int x) {

        int translation = gc.effi.getTranslationX();
        
        gc.effi.setNewMinX(x);
        translation -= gc.effi.getTranslationX();
        
        mlPasteStrategy strat = gc.mkListener.getPasteStrategy();
        if(strat != null)
            strat.notifyMinXChange(translation);
    }

    @Override
    public void notifyMinYChange(int y) {
        
        int translation = gc.effi.getTranslationY();
        
        gc.effi.setNewMinY(y);
        translation -= gc.effi.getTranslationY();
        
        mlPasteStrategy strat = gc.mkListener.getPasteStrategy();
        if(strat != null)
            strat.notifyMinYChange(translation);
    }
    

}
