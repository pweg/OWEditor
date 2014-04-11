package org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IAdapterObserver;

/**
 * This interface is used between the adapter package and the 
 * main controller package and is used to initialize the adapter
 * and get certain interfaces from it.
 * 
 * @author Patrick
 *
 */
public interface AdapterControllerMainControllerInterface {
    
    /**
     * Initializes the whole adapter.
     */
    public void initialize();
    
    /**
     * Starts to load the currently existing world and then building
     * the data for the data package.
     */
    public void getCurrentWorld();
        
    /**
     * Sets an data update interface, which is used to build 
     * and update data objects and environment data.
     * 
     * @param dua a dataUpdate interface.
     */
    public void registerDataUpdateInterface(IAdapterObserver dua);
    
    /**
     * Returns the clientUpdateInterface, which is needed for the 
     * gui to transmit changes to the adapter.
     * 
     * @return a client update instance.
     */
    public GUIObserverInterface getClientUpdateInterface();
    
    /**
     * Returns the instace of the coordiante translator.
     * 
     * @return The translator.
     */
    public CoordinateTranslatorInterface getCoordinateTranslator();
    

}
