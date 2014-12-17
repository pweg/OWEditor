package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

public interface stateInput {
    
    /**
     * Sets width/height, for further usage.
     * 
     * @param width The width of an object.
     * @param height The height of an object.
     */
    public void setBounds(int width, int height);
    
    /**
     * Starts the finish routine.
     */
    public void finished();

}
