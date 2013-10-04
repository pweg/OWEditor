package org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.ClientUpdateGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;


public interface GUIControllerInterface {
	
    /**
     * Creates the main frame, which houses
     * the whole editor.
     */
    public void createFrame();
    
    /**
     * Sets the visibility of the main frame.
     * @param visibility: True for showing the frame,
     * false for hiding it.
     */
    public void setVisibility(boolean visibility);

    /**
     * Shows the GUI that an change happened to one
     * of the data objects.
     * @param id: The id of the object that has changed. 
     */
    public void setDataUpdate(long id);
    
    /**
     * Sets a DataObjectManager instance.
     * @param dm: the instance.
     */
    public void setDataManager(DataObjectManagerGUIInterface dm);
    
    /**
     * Sets a ClientUpdateAdapter instance.
     * @param clientUpdateInterface: the instance.
     */
    public void setClientUpdateAdapter (ClientUpdateGUIInterface clientUpdateInterface);
    
    /**
     * Sets a new width for the drawing panel.
     * This is used for making the panel always get larger
     * when objects are out of the current size.
     * @param width: the new width.
     */
    public void setWidth(int width);
    
    /**
     * Sets a new height for the drawing panel.
     * This is used for making the panel always get larger
     * when objects are out of the current size.
     * @param height: the new height.
     */
    public void setHeight(int height);  
    
    /**
     * Sets a new minimal x coordinate, which is used
     * for centering all objects.
     * @param x: the new value.
     */
    public void setMinX(int x);
   
    /**
     * Sets a new minimal y coordinate, which is used
     * for centering all objects.
     * @param y: the new value.
     */
    public void setMinY(int y);

}
