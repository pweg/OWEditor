/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.common;

import java.awt.image.BufferedImage;
import org.jdesktop.wonderland.common.cell.state.CellComponentClientState;

/**
 *
 * @author Patrick
 */
public class ImageCellComponentClientState extends CellComponentClientState {

    // The text of the tooltip
    private BufferedImage img = null;

    /** Default constructor */
    public ImageCellComponentClientState() {
    }

    /**
     * Returns the stored image.
     *
     * @return The image.
     */
    public BufferedImage getImage() {
        return img;
    }

    /**
     * Sets the image.
     *
     * @param img The tooltip text
     */
    public void setImage(BufferedImage img) {
        this.img = img;
    }
    
}
