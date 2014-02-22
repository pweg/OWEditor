/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.jdesktop.wonderland.common.cell.state.CellComponentServerState;
import org.jdesktop.wonderland.common.cell.state.annotation.ServerState;

/**
 *
 * @author Patrick
 */
@XmlRootElement(name="id-cell-component")
@ServerState
public class IDCellComponentServerState extends CellComponentServerState{
    
     // The id
    @XmlElement(name = "original_id")
    private long id = -1;

    /** Default constructor */
    public IDCellComponentServerState() {
    }

    @Override
    public String getServerComponentClassName() {
        return "org.jdesktop.wonderland.modules.oweditor.server" +
               ".IDCellComponentMO";
    }

    /**
     * Returns the id.
     *
     * @return The id
     */
    @XmlTransient
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
