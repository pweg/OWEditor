/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.components;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Logger;
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
    private Cell cell = null;
    
    private ArrayList<ImageChangeListener> listenerList = null; 
     private static final Logger LOGGER =
            Logger.getLogger(ImageCellComponentClientState.class.getName());

    /**
     * Constructor, takes the Cell associated with the Cell Component.
     *
     * @param cell The Cell associated with this component
     */
    public ImageCellComponent(Cell cell) {
        super(cell);
        this.cell = cell;
        listenerList = new ArrayList<ImageChangeListener>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setClientState(CellComponentClientState clientState) {
        LOGGER.warning("client STATE set");
        super.setClientState(clientState);
        img = ((ImageCellComponentClientState) clientState).getImage();
        
        for(ImageChangeListener listener : listenerList){
            listener.imageChanged(img, cell);
        }
    }
    
    public void registerChangeListener(ImageChangeListener listener){
        listenerList.add(listener);
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
