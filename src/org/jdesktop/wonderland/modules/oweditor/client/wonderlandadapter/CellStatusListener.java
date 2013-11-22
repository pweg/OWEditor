/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellStatusChangeListener;
import org.jdesktop.wonderland.common.cell.CellStatus;

/**
 *
 * @author Patrick
 */
public class CellStatusListener implements CellStatusChangeListener{
    
    private UpdateManager sua = null;    
    
    public CellStatusListener(UpdateManager sua){
       this.sua = sua;
    }

    /*public void componentChanged(Cell cell, ChangeType type, CellComponent component) {
        
        if(type.equals(ChangeType.ADDED)){
            win.addCell(cell);
        }else if(type.equals(ChangeType.REMOVED)){
            win.removeCell(cell);   
        }
    }*/

    public void cellStatusChanged(Cell cell, CellStatus status) {
        
        if(status.equals(CellStatus.DISK)){
            sua.serverRemovalEvent(cell);
        }
        else if(status.equals(CellStatus.VISIBLE)){
            sua.createObject(cell);
        }
    }
    
}
