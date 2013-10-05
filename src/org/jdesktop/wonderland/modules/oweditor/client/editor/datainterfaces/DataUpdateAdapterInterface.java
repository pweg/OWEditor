package org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces;

/**
 * This interface is used between the data and the adapter package.
 * 
 * @author Patrick
 *
 */
public interface DataUpdateAdapterInterface {
    
	/**
	 * Updates a data object with the data stored in the given
	 * object.
	 * 
	 * @param data the dataObject which holds all data necessary
	 * for updating the data object.
	 */
    public void updateObject(DataObjectInterface data);
    
    /**
     * Creates a new data object with the given data object.
     * 
     * @param data the data object which needs to be stored 
     * in the data manager.
     */
    public void createObject(DataObjectInterface data);
    
    /**
     * Creates an empty data object interface, which can be used
     * to fill with data and then transmitted via updateObject or
     * createObject to the data manager.
     * 
     * @return an empty data object, which has only dummy values.
     */
    public DataObjectInterface createEmptyObject();

}
