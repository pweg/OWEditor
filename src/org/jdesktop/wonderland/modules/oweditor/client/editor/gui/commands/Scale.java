package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.awt.Point;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

public class Scale implements Command{
    
    ArrayList<Long> ids;
    ArrayList<Point> coordsOld;
    ArrayList<Double> scaleOld;
    ArrayList<Point> coordsNew;
    ArrayList<Double> scaleNew;
    
    public Scale(ArrayList<Long> ids, ArrayList<Point> coordsOld,
            ArrayList<Double> scaleOld, ArrayList<Point> coordsNew,
            ArrayList<Double> scaleNew){
        this.ids = ids;
        this.scaleOld = scaleOld;
        this.coordsOld = coordsOld;
        this.scaleNew = scaleNew;
        this.coordsNew = coordsNew;

        //null pointer prevention
        if(ids == null)
            this.ids = new ArrayList<Long>();
    }

    @Override
    public void execute(GUIObserverInterface goi) {
        scale(goi, coordsNew, scaleNew);
    }

    @Override
    public void undo(GUIObserverInterface goi) {
        scale(goi, coordsOld, scaleOld);
        
    }

    @Override
    public void redo(GUIObserverInterface goi) {
        execute(goi);
    }
    
    /**
     * Scales, using the given lists.
     * 
     * @param goi A guiobserverinterface instance.
     * @param coords A list of coordinates.
     * @param scale A list of scale values.
     */
    private void scale(GUIObserverInterface goi, ArrayList<Point> coords,
            ArrayList<Double> scales){
        try{
            for(int i=0;i < ids.size();i++){
                Long id = ids.get(i);
                Point p = coords.get(i);
                double scale = scales.get(i);
                
                goi.notifyScaling(id, p.x, p.y, scale);
            }
        }catch(Exception e){
            System.err.println("Scale command: list size dont match");
        }
    }

}
