package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

/**
 * Scale command.
 * 
 * @author Patrick
 */
public class Scale implements Command{
    
    ArrayList<Long> ids = new ArrayList<Long>();
    ArrayList<Double> scaleOld  = new ArrayList<Double>();
    ArrayList<Double> scaleNew = new ArrayList<Double>();
    
    public Scale(ArrayList<Long> ids,
            ArrayList<Double> scaleOld, ArrayList<Double> scaleNew){

        //null pointer prevention
        if(ids == null || scaleOld == null | scaleNew == null)
            return;
        
        this.ids.addAll(ids);
        this.scaleOld.addAll(scaleOld);
        this.scaleNew.addAll(scaleNew);

    }

    @Override
    public void execute(GUIObserverInterface goi)  throws Exception{
        scale(goi, scaleNew);
    }

    @Override
    public void undo(GUIObserverInterface goi)  throws Exception{
        scale(goi, scaleOld);
        
    }

    @Override
    public void redo(GUIObserverInterface goi)  throws Exception{
        execute(goi);
    }
    
    /**
     * Scales, using the given lists.
     * 
     * @param goi A guiobserverinterface instance.
     * @param coords A list of coordinates.
     * @param scale A list of scale values.
     */
    private void scale(GUIObserverInterface goi,
            ArrayList<Double> scales) throws Exception{
        int failcount = 0;
        
        try{
            for(int i=0;i < ids.size();i++){
                Long id = ids.get(i);
                double scale = scales.get(i);
                try{
                    goi.notifyScaling(id, scale);
                }catch(Exception e){
                    failcount++;
                }
            }
        }catch(Exception e){
            System.err.println("Scale command: list size dont match");
            throw e;
        }
        
        //the command only failed, when every operation failed!
        if(failcount == ids.size())
            throw new CommandException();
    }

}
