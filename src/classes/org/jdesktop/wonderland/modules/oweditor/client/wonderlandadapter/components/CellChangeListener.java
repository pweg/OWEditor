/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.components;

import org.jdesktop.wonderland.client.cell.Cell;

/**
 * An image change listener interface.
 * 
 * @author Patrick
 */
public interface CellChangeListener{

    /**
     * Is called when an image of a cell is changed.
     * 
     * @param img The new image name.
     * @param dir The new image directory.
     * @param cell The cell which has received a new image.
     */
    public void imageChanged(String img, String dir, Cell cell);
    
    /**
     * Is called when the name of a cell is changed.
     * 
     * @param cell  The cell.
     */
    public void nameChanged(Cell cell);
    
}
