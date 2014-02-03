package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.awt.Point;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

public class Rotate implements Command{
    
    ArrayList<Long> ids;
    ArrayList<Point> coordsOld;
    ArrayList<Double> rotationOld;
    ArrayList<Point> coordsNew;
    ArrayList<Double> rotationNew;
    
    public Rotate(ArrayList<Long> ids, ArrayList<Point> coordsOld,
            ArrayList<Double> rotationOld, ArrayList<Point> coordsNew,
            ArrayList<Double> rotationNew){
        this.ids = ids;
        this.rotationOld = rotationOld;
        this.coordsOld = coordsOld;
        this.rotationNew = rotationNew;
        this.coordsNew = coordsNew;

        //null pointer prevention
        if(ids == null)
            this.ids = new ArrayList<Long>();
    }

    @Override
    public void execute(GUIObserverInterface goi) {
        rotate(goi, coordsNew, rotationNew);
    }

    @Override
    public void undo(GUIObserverInterface goi) {
        rotate(goi, coordsOld, rotationOld);
    }

    @Override
    public void redo(GUIObserverInterface goi) {
        execute(goi);
    }

    /**
     * Rotates, using the given lists.
     * 
     * @param goi A guiobserverinterface instance.
     * @param coords A list of coordinates.
     * @param rotation A list of rotation values.
     */
    private void rotate(GUIObserverInterface goi, ArrayList<Point> coords,
            ArrayList<Double> rotation){
        try{
            for(int i=0;i < ids.size();i++){
                Long id = ids.get(i);
                Point p = coords.get(i);
                double rot = rotation.get(i);
                
                goi.notifyRotation(id, p.x, p.y, rot);
            }
        }catch(Exception e){
            System.err.println("Rotate command: list size dont match");
        }
    }

}
