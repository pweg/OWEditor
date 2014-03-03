/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.TransformChangeListener;

/**
 *
 * @author Patrick
 */
public class TransformListener implements TransformChangeListener{
    
    private ServerEventManager sua = null;
    
    public TransformListener(ServerEventManager sua){
       this.sua = sua;
    }
    
    public void transformChanged(Cell cell, ChangeSource source) {
        sua.transformEvent(cell);
    }
    
}