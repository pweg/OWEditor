/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.components;

import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellComponent;
import org.jdesktop.wonderland.common.cell.state.CellComponentClientState;
import org.jdesktop.wonderland.modules.oweditor.common.IDCellComponentClientState;

/**
 *
 * @author Patrick
 */
public class IDCellComponent extends CellComponent {

    // The image.
    private long id = -1;

    /**
     * Constructor, takes the Cell associated with the Cell Component.
     *
     * @param cell The Cell associated with this component
     */
    public IDCellComponent(Cell cell) {
        super(cell);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setClientState(CellComponentClientState clientState) {
        super.setClientState(clientState);
        id = ((IDCellComponentClientState) clientState).getID();
    }

    /**
     * Returns the id.
     * 
     * @return The id
     */
    public long getID() {
        return id;
    }
}
