package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

public class Paste implements Command{
    
    ArrayList<Long> ids;
    ArrayList<Point> coords;
    
    public Paste(ArrayList<Long> ids, ArrayList<Point> coords) {
        this.ids = ids;
        this.coords = coords;

        //null pointer prevention
        if(ids == null)
            this.ids = new ArrayList<Long>();
    }

    @Override
    public void execute(GUIObserverInterface goi)  throws Exception{
        
        int failcount = 0;
        
        try{
            for(int i=0; i<ids.size();i++){
                Long id = ids.get(i);
                Point p = coords.get(i);
                try{
                    goi.notifyPaste(id, p.x, p.y);
                }catch(Exception e){
                    failcount++;
                }
            }
        }catch(Exception e){
            System.err.println("Paste command: list size dont match");
            throw e;
        }
        
        //the command only failed, when every operation failed!
        if(failcount == ids.size())
            throw new CommandException();
    }

    @Override
    public void undo(GUIObserverInterface goi)  throws Exception{
        int failcount = 0;
        
        for (Iterator<Long> iterator = ids.iterator(); iterator.hasNext();) {
            try{
                goi.undoObjectCreation();
            }catch(Exception e){
                failcount++;
            }
            iterator.next();
        }
        
        //the command only failed, when every operation failed!
        if(failcount == ids.size())
            throw new CommandException();
    }

    @Override
    public void redo(GUIObserverInterface goi)  throws Exception{
        int failcount = 0;
        
        for (Iterator<Long> iterator = ids.iterator(); iterator.hasNext();) {
            try{
                goi.redoObjectCreation();
            }catch(Exception e){
                failcount++;
            }
            iterator.next();
        }
        
        //the command only failed, when every operation failed!
        if(failcount == ids.size())
            throw new CommandException();
    }
    

}
