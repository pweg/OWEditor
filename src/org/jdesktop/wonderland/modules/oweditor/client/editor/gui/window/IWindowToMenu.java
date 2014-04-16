package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

public interface IWindowToMenu {
    
    /**
     * Shows the import kmz frame.
     */
    public void importKmz();

    /**
     * Signals for copying shapes.
     */
    public void copyShapes();
    
    /**
     * Signals for cutting shapes.
     */
    public void cutShapes();

    /**
     * Starts the paste process.
     */
    public void pasteShapes();

    /**
     * Starts the rotation process.
     */
    public void rotateShapes();

    /**
     * Starts the scaling process.
     */
    public void scaleShapes();

    /**
     * Selects all existing shapes.
     */
    public void selectAllShapes();
    
    /**
     * Sends items to the back or foreground
     * 
     * @param b If true the shapes are send to the background,
     * if false, they are send to the foreground.
     */
    public void setBackground(boolean b);

    /**
     * Sets the properties frame visible.
     * 
     * @param b If true, the frame becomes visible,
     * if false it will be hidden.
     */
    public void setPropertiesVisible(boolean b);

    /**
     * Deletes all shapes.
     * 
     * @return true, if all things were deleted, false otherwise.
     */
    public void deleteAll();

    /**
     * Loads a world.
     */
    public void loadWorld();

    /**
     * Saves a world.
     */
    public void saveWorld();

}
