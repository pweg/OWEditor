package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.awt.Point;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

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
    public void execute(GUIObserverInterface goi) {
        translate(goi, coordsNew);
    }

    @Override
    public void undo(GUIObserverInterface goi) {
        translate(goi, coordsOld);
    }

    @Override
    public void redo(GUIObserverInterface goi) {
        execute(goi);        
    }
    
    /**
     * Translates, using the given lists.
     * 
     * @param goi A guiobserverinterface instance.
     * @param coords A list of coordinates.
     */
    private void translate(GUIObserverInterface goi, ArrayList<Point> coords){
        try{
            for(int i=0;i < ids.size();i++){
                Long id = ids.get(i);
                Point p = coords.get(i);
                
                goi.notifyTranslationXY(id, p.x, p.y);
            }
        }catch(Exception e){
            System.err.println("Translate command: list size dont match");
        }
    }

}
