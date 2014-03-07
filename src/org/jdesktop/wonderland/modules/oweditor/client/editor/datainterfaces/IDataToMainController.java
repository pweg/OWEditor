package org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;

/**
 * An interface used between the data and the controller package.
 * @author Patrick
 *
 */
public interface IDataToMainController {
    
    /**
     * Initializes the data controller.
     */
    public void initialize();
    
    /**
     * Returns a dataUpdate instance, which is used for the adapter to 
     * update data objects.
     * 
     * @return a DataUpdateInterface.
     */
    public IAdapterObserver getDataUpdateInterface();
    
    /**
     * Returns a dataObjectManager instance, which is used for the GUI
     * to get updates from the dataManager.
     * @return a DataManagerInterface.
     */
    public IDataToGUI getDataManagerInterface();
    
    /**
     * Registers a coordinate translator instance from the adapter.
     * 
     * @param ct The coordinate translator.
     */
    public void registerCoordinateTranslator(CoordinateTranslatorInterface ct);

}
