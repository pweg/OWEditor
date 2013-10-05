package org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectInterface;

/**
 * This interface is used as an observer for the dataObjectManager.
 * Note that there should only be one observer, because only one 
 * gui needs to be updated. Creating one observer for each data object
 * does seem a little bit too costly.
 * 
 * @author Patrick
 *
 */
public interface DataObjectManagerObserverInterface {

	/**
	 * Notifies, that an object has changed.
	 * 
	 * @param dataObject the object which has been changed.
	 */
	public void notify(DataObjectInterface dataObject);
}
