package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import javax.xml.bind.Marshaller;
import org.jdesktop.wonderland.client.cell.Cell;

import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.components.CellChangeListener;
    
/**
 * Class for the image change listener, which also gets notified on a name change.
 */
class ChangeListener extends Marshaller.Listener implements CellChangeListener{
    
    private ServerEventManager sem = null;
    
    public ChangeListener(ServerEventManager sem){
        this.sem = sem;
    }

    @Override
    public void imageChanged(String img, String dir, Cell cell) {
        sem.imageChangedEvent(img, dir, cell);
    }

    @Override
    public void nameChanged(Cell cell) {
        sem.nameChanged(cell);
    }
}