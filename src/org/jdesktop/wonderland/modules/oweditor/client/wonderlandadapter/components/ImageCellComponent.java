/**
 * Project Wonderland
 *
 * Copyright (c) 2004-2010, Sun Microsystems, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * Sun designates this particular file as subject to the "Classpath"
 * exception as provided by Sun in the License file that accompanied
 * this code.
 */
package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.components;

import java.util.ArrayList;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellComponent;
import org.jdesktop.wonderland.common.cell.state.CellComponentClientState;
import org.jdesktop.wonderland.modules.oweditor.common.ImageCellComponentClientState;

/**
 * Client-side Test Cell Component.
 * 
 * @author Jordan Slott <jslott@dev.java.net>
 */
public class ImageCellComponent extends CellComponent {

    // The image.
    private String image = null;
    private String dir = null;
    
    private Cell cell = null;    
    private ArrayList<ImageChangeListener> listenerList = null; 

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
        super.setClientState(clientState);
        image = ((ImageCellComponentClientState) clientState).getImage();
        dir = ((ImageCellComponentClientState) clientState).getDir();
        
        for(ImageChangeListener listener : listenerList){
            listener.imageChanged(image, dir, cell);
        }
    }

    /**
     * Returns the image name.
     * 
     * @return The image name
     */
    public String getImage() {
        return image;
    }
    
    /**
     * Returns the image directory.
     * 
     * @return The directory
     */
    public String getDir(){
        return dir;
    }
    
    /**
     * Registers a image change listener.
     * 
     * @param listener The listener
     */
    public void registerChangeListener(ImageChangeListener listener){
        listenerList.add(listener);
    }
}
