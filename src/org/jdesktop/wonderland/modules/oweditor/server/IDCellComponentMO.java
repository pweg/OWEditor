/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.server;

import org.jdesktop.wonderland.common.cell.ClientCapabilities;
import org.jdesktop.wonderland.common.cell.state.CellComponentClientState;
import org.jdesktop.wonderland.common.cell.state.CellComponentServerState;
import org.jdesktop.wonderland.modules.oweditor.common.IDCellComponentClientState;
import org.jdesktop.wonderland.modules.oweditor.common.IDCellComponentServerState;
import org.jdesktop.wonderland.server.cell.CellComponentMO;
import org.jdesktop.wonderland.server.cell.CellMO;
import org.jdesktop.wonderland.server.comms.WonderlandClientID;

/**
 *  This cell component is used for undo/redo. It contains the original
 * id of the cell, after it was deleted.
 * 
 * @author Patrick
 */
public class IDCellComponentMO extends CellComponentMO {

    // The id
    private long id = -1;

    
    /**
     * Constructor, takes the CellMO associated with the Cell Component.
     *
     * @param cell The CellMO associated with this component
     */
    public IDCellComponentMO(CellMO cell) {
        super(cell);
    }

    /**
     * {@inheritDoc}
     * @return The client class
     */
    @Override
    protected String getClientClass() {
        return "org.jdesktop.wonderland.modules.oweditor.client" +
               ".wonderlandadapter.components.IDCellComponent";
    }

    /**
     * {@inheritDoc}
     * @return The client state
     */
    @Override
    public CellComponentClientState getClientState(
            CellComponentClientState state, WonderlandClientID clientID,
            ClientCapabilities capabilities) {

        if (state == null) {
            state = new IDCellComponentClientState();
        }
        ((IDCellComponentClientState) state).setID(id);
        return super.getClientState(state, clientID, capabilities);
    }

    /**
     * {@inheritDoc}
     * @return The server state
     */
    @Override
    public CellComponentServerState getServerState(
            CellComponentServerState state) {
        
        if (state == null) {
            state = new IDCellComponentServerState();
        }
        ((IDCellComponentServerState) state).setID(id);
        return super.getServerState(state);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setServerState(CellComponentServerState state) {
        super.setServerState(state);
        id = ((IDCellComponentServerState) state).getID();
    }
}
