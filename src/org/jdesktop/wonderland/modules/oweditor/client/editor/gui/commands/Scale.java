package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

public class Scale implements Command{
    
    ArrayList<Long> ids;
    ArrayList<Double> scaleOld;
    ArrayList<Double> scaleNew;
    
    public Scale(ArrayList<Long> ids,
            ArrayList<Double> scaleOld, ArrayList<Double> scaleNew){
        this.ids = ids;
        this.scaleOld = scaleOld;
        this.scaleNew = scaleNew;

        //null pointer prevention
        if(ids == null)
            this.ids = new ArrayList<Long>();
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
