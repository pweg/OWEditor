/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.common;

import org.jdesktop.wonderland.common.cell.state.CellComponentClientState;

/**
 * The id cell component client state.
 * 
 * @author Patrick
 */
public class IDCellComponentClientState extends CellComponentClientState{
    
    private long id = -1;

    /** Default constructor */
    public IDCellComponentClientState() {
    }

    /**
     * Returns the id.
     *
     * @return The id
     */
    public long getID() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id The id
     */
    public void setID(long id) {
        this.id = id;
    }
    
}
