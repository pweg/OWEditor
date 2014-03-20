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
 * Client-side Image Cell Component.
 */
public class ImageCellComponent extends CellComponent {

    // The image.
    private String image = null;
    private String dir = null;
    private String name = null;
    
    private Cell cell = null;    
    private ArrayList<CellChangeListener> listenerList = null; 

    /**
     * Constructor, takes the Cell associated with the Cell Component.
     *
     * @param cell The Cell associated with this component
     */
    public ImageCellComponent(Cell cell) {
        super(cell);
        this.cell = cell;
        this.name = cell.getName();
        listenerList = new ArrayList<CellChangeListener>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setClientState(CellComponentClientState clientState) {
        super.setClientState(clientState);
        String image = ((ImageCellComponentClientState) clientState).getImage();
        String dir = ((ImageCellComponentClientState) clientState).getDir();
        
        for(CellChangeListener listener : listenerList){
            if(!image.equals(this.image) || !dir.equals(this.dir))
                listener.imageChanged(image, dir, cell);
            if(!cell.getName().equals(name)){
                listener.nameChanged(cell);
            }
        }
        this.image = image;
        this.dir = dir;
        this.name = name;
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
    public void registerChangeListener(CellChangeListener listener){
        listenerList.add(listener);
    }
}
