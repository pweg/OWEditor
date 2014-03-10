package org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces;

import java.awt.image.BufferedImage;

public interface IImage {
    
    /**
     * Returns the image.
     * 
     * @return The image as buffered image.
     */
    public BufferedImage getImage();
    
    /**
     * Returns the name of the image.
     * 
     * @return The name.
     */
    public String getName();

}
