package org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces;

import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.GUIControllerInterface;

/**
 * An interface used between the data and the controller package.
 * @author Patrick
 *
 */
public interface DataControllerMainControllerInterface {
    
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
    public DataUpdateAdapterInterface getDataUpdateInterface();
    
    /**
     * Returns a dataObjectManager instance, which is used for the GUI
     * to get updates from the dataManager.
     * @return a DataManagerInterface.
     */
    public DataObjectManagerGUIInterface getDataManagerInterface();
    
    /**
     * Sets a guiController interface, which is used for communication
     * to the gui package.
     * 
     * @param gui the interface in question.
     */
    public void setGUIControler(GUIControllerInterface gui);

}
