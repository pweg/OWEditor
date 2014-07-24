package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.awt.Point;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

/**
 * This command is used for rotation and translation at the same time.
 * 
 * @author Patrick
 *
 */
public class RotateTranslate implements Command{
    
    Command translate = null;
    Command rotate = null;
    
    ArrayList<Long> ids;
    ArrayList<Point> coordsOld;
    ArrayList<Double> rotationOld;
    ArrayList<Point> coordsNew;
    ArrayList<Double> rotationNew;
    
    public RotateTranslate(ArrayList<Long> ids, ArrayList<Point> coordsOld,
            ArrayList<Vector3D> rotationOld, ArrayList<Point> coordsNew,
            ArrayList<Vector3D> rotationNew){
        translate = new TranslateXY(ids, coordsOld, coordsNew);
        rotate = new Rotate(ids, rotationOld, rotationNew);
    }

    @Override
    public void execute(GUIObserverInterface goi)  throws Exception{
        rotate.execute(goi);
        translate.execute(goi);
    }

    @Override
    public void undo(GUIObserverInterface goi)  throws Exception{
        rotate.undo(goi);
        translate.undo(goi);
    }

    @Override
    public void redo(GUIObserverInterface goi)  throws Exception{
        rotate.redo(goi);
        translate.redo(goi);
    }

}
