/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellStatusChangeListener;
import org.jdesktop.wonderland.common.cell.CellStatus;

/**
 * Listener for cell status, which means, whether a cell is deleted or
 * created.
 * 
 * @author Patrick
 */
public class CellStatusListener implements CellStatusChangeListener{
    
    private ServerEventManager sua = null;    
    
    public CellStatusListener(ServerEventManager sua){
       this.sua = sua;
    }

    public void cellStatusChanged(Cell cell, CellStatus status) {
        if(status.equals(CellStatus.DISK)){
            sua.removalEvent(cell);
        }
        else if(status.equals(CellStatus.VISIBLE)){
            sua.creationEvent(cell);
        }
    }
    
}
