package org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;


public interface GUIControllerInterface {
    
    /**
     * Creates the main frame, which houses
     * the whole editor.
     */
    public void initializeGUI();
    
    /**
     * Sets the visibility of the main frame.
     * 
     * @param visibility: True for showing the frame,
     * false for hiding it.
     */
    public void setVisible(boolean visibility);

    /**
     * Returns a DataObjectObserver instance.
     * 
     * @return a dataObjectObserverInterface.
     */
    public DataObjectObserverInterface getDataObjectObserver();
    
    /**
     * Returns a EnvironmentObserver instance.
     * 
     * @return a environmentObserverInterface.
     */
    public EnvironmentObserverInterface getEnvironmentObserver();
    
    /**
     * Sets a DataObjectManager instance.
     * 
     * @param dm the instance.
     */
    public void registerDataManager(DataObjectManagerGUIInterface dm);
    
    /**
     * Sets an observer from the adapter package to listen for 
     * changes made in the gui.
     * 
     * @param clientUpdateInterface the instance.
     */
    public void registerGUIObserver (GUIObserverInterface clientUpdateInterface);

    /**
     * Sets the coordinate translator from the adapter package.
     * This is needed for the correct mouse coordinates.
     * 
     * @param coordinateTranslator A coordinateTranslaterInterface.
     */
    public void registerCoordinateTranslator(CoordinateTranslatorInterface coordinateTranslator);

}
