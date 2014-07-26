package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.awt.Point;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

/**
 * This is a translation, which only translates x and y. This is 
 * used by the editors normal translate option, where the user 
 * moves a 2d shape. The coordinates are from the editor, which 
 * need to be transformed by the adapter.
 * 
 * @author Patrick
 *
 */
public class TranslateXY implements Command{

    ArrayList<Long> ids = new ArrayList<Long>();
    ArrayList<Point> coordsOld = new ArrayList<Point>();
    ArrayList<Point> coordsNew = new ArrayList<Point>();
    
    public TranslateXY(ArrayList<Long> ids, ArrayList<Point> coordsOld,
            ArrayList<Point> coordsNew){
        
        if(ids == null || coordsOld == null || coordsNew == null)
            return;
        
        this.ids.addAll(ids);
        this.coordsOld.addAll(coordsOld);
        this.coordsNew.addAll(coordsNew);
        
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
