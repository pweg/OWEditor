/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.imagecomponent;

import java.awt.image.BufferedImage;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellComponent;
import org.jdesktop.wonderland.common.cell.state.CellComponentClientState;
import org.jdesktop.wonderland.modules.oweditor.common.ImageCellComponentClientState;

/**
 *
 * @author Patrick
 */
public class ImageCellComponent extends CellComponent {

    // The image of the component.
    private BufferedImage img = null;

    /**
     * Constructor, takes the Cell associated with the Cell Component.
     *
     * @param cell The Cell associated with this component
     */
    public ImageCellComponent(Cell cell) {
        super(cell);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setClientState(CellComponentClientState clientState) {
        super.setClientState(clientState);
        img = ((ImageCellComponentClientState) clientState).getImage();
    }

    /**
     * Returns the Image.
     * 
     * @return The buffered image.
     */
    public BufferedImage getImage() {
        return img;
    }
}
