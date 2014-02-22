package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.GUIEventManager;

public class TranslateXY implements Command{

    ArrayList<Long> ids;
    ArrayList<Point> coordsOld;
    ArrayList<Point> coordsNew;
    
    public TranslateXY(ArrayList<Long> ids, ArrayList<Point> coordsOld,
            ArrayList<Point> coordsNew){
        this.ids = ids;
        this.coordsOld = coordsOld;
        this.coordsNew = coordsNew;
        
    }

    @Override
    public void execute(GUIObserverInterface goi)  throws Exception{
        translate(goi, coordsNew);
    }

    @Override
    public void undo(GUIObserverInterface goi)  throws Exception{
        translate(goi, coordsOld);
    }

    @Override
    public void redo(GUIObserverInterface goi)  throws Exception{
        execute(goi);        
    }
    
    /**
     * Translates, using the given lists.
     * 
     * @param goi A guiobserverinterface instance.
     * @param coords A list of coordinates.
     */
    private void translate(GUIObserverInterface goi, ArrayList<Point> coords)
        throws Exception{
        int failcount = 0;
        
        try{
            for(int i=0;i < ids.size();i++){
                Long id = ids.get(i);
                Point p = coords.get(i);
                
                try{
                    goi.notifyTranslationXY(id, p.x, p.y);
                }catch(Exception e){
                    failcount++;
                }
            }
        }catch(Exception e){
            System.err.println("Translate command: list size dont match");
            throw e;
        }
        
        //the command only failed, when every operation failed!
        if(failcount == ids.size())
            throw new CommandException();
    }

}
