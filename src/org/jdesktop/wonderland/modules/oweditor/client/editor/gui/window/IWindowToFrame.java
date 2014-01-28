package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

public interface IWindowToFrame {
    
    /**
     * Load a kmz throught the adapter.
     * 
     * @param url The kmz url.
     * @return An integer array containing the following information:
     * int[0]: the width of the kml object.
     * int[1]: the height of the kml object.
     */
    public int[] loadKMZ(String url);
    
    /**
     * Creates a dragging object which lets the user pick a location with it.
     * 
     * @param width The shapes width.
     * @param height The shapes height.
     * @param rotation The shapes rotation.
     * @param scale The shapes scale.
     */
    public void chooseLocation(int width, int height, double rotation,
            double scale);

}
