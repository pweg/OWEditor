package org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces;

import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.DataObjectObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.EnvironmentObserverInterface;

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
    public AdapterObserverInterface getDataUpdateInterface();
    
    /**
     * Returns a dataObjectManager instance, which is used for the GUI
     * to get updates from the dataManager.
     * @return a DataManagerInterface.
     */
    public DataObjectManagerGUIInterface getDataManagerInterface();
        
    /**
     * Registers an observer for the data object manager, which informs
     * the gui on object creation and object changes.
     * 
     * @param domo the observer, which observes the data manager.
     */
    public void registerDataObjectObserver(DataObjectObserverInterface domo);
    
    /**
     * Registers an observer for the environment manager, which informs 
     * the gui on environmental changes, such as new widths/heights and
     * offsets.
     * 
     * @param en the observer, which observes the environment manager.
     */
    public void registerEnvironmentObserver(EnvironmentObserverInterface en);

}
