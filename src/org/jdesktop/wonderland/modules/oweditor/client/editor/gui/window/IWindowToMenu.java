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

}
