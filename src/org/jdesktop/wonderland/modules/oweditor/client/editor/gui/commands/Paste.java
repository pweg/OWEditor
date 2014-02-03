package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.awt.Point;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

public class Paste implements Command{
    
    ArrayList<Long> ids;
    ArrayList<Point> coords;
    
    public Paste(ArrayList<Long> ids, ArrayList<Point> coords){
        this.ids = ids;
        this.coords = coords;

        //null pointer prevention
        if(ids == null)
            this.ids = new ArrayList<Long>();
    }

    @Override
    public void execute(GUIObserverInterface goi) {
        
        try{
            for(int i=0; i<ids.size();i++){
                Long id = ids.get(i);
                Point p = coords.get(i);
                goi.notifyPaste(id, p.x, p.y);
            }
        }catch(Exception e){
            System.err.println("Paste command: list size dont match");
        }
    }

    @Override
    public void undo(GUIObserverInterface goi) {
        for(long id: ids)
            goi.undoObjectCreation();
    }

    @Override
    public void redo(GUIObserverInterface goi) {
        for(long id: ids)
            goi.redoObjectCreation();
    }
    

}
