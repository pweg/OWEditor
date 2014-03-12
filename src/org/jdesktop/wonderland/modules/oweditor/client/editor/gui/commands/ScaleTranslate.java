package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.awt.Point;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

/**
 * Combines scaling and translation into one command.
 * This is used durring a normal 2d scale process.
 * 
 * @author Patrick
 *
 */
public class ScaleTranslate implements Command{
    
    Command translate = null;
    Command scale = null;
    ArrayList<Long> ids;
    ArrayList<Point> coordsOld;
    ArrayList<Double> scaleOld;
    ArrayList<Point> coordsNew;
    ArrayList<Double> scaleNew;
    
    public ScaleTranslate(ArrayList<Long> ids, ArrayList<Point> coordsOld,
            ArrayList<Double> scaleOld, ArrayList<Point> coordsNew,
            ArrayList<Double> scaleNew){

        translate = new TranslateXY(ids, coordsOld, coordsNew);
        scale = new Scale(ids, scaleOld, scaleNew);
    }

    @Override
    public void execute(GUIObserverInterface goi)  throws Exception{
        translate.execute(goi);
        scale.execute(goi);
    }

    @Override
    public void undo(GUIObserverInterface goi)  throws Exception{
        translate.undo(goi);
        scale.undo(goi);
    }

    @Override
    public void redo(GUIObserverInterface goi)  throws Exception{
        translate.redo(goi);
        scale.redo(goi);
    }
    

}
