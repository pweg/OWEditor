package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.awt.Point;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

/**
 * Paste command.
 * 
 * @author Patrick
 */
public class Paste implements Command{
    
    ArrayList<Long> ids = new ArrayList<Long>();
    ArrayList<Long> copyIDs = new ArrayList<Long>();
    ArrayList<Point> coords  = new ArrayList<Point>();
    
    public Paste(ArrayList<Long> ids, ArrayList<Point> coords) {
        
        //null pointer prevention
        if(ids == null || coords == null)
            return;
        

        this.ids.addAll(ids);
        this.coords.addAll(coords);
        
    }

    @Override
    public void execute(GUIObserverInterface goi)  throws Exception{
        
        int failcount = 0;
        copyIDs.clear();
        
        try{
            for(int i=0; i<ids.size();i++){
                Long id = ids.get(i);
                Point p = coords.get(i);
                try{
                    long cid = goi.notifyPaste(id, p.x, p.y);
                    if(cid != -1)
                        copyIDs.add(cid);
                    
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
        
        for (long cid : copyIDs) {
            try{
                goi.notifyRemoval(cid);
            }catch(Exception e){
                failcount++;
            }
        }
        
        //the command only failed, when every operation failed!
        if(failcount == ids.size())
            throw new CommandException();
    }

    @Override
    public void redo(GUIObserverInterface goi)  throws Exception{
        execute(goi);
    }
    

}
