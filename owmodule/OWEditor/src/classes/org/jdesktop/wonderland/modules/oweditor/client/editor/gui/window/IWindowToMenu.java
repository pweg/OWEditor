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

}
