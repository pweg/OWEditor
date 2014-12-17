package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

/**
 * This is a normal translation, which translates x, y and z with
 * the original coordinates, used by the virtual world.
 * 
 * @author Patrick
 *
 */
public class TranslateFloat implements Command{

    ArrayList<Long> ids = new ArrayList<Long>();
    ArrayList<Vector3D> coordsOld = new ArrayList<Vector3D>();
    ArrayList<Vector3D> coordsNew = new ArrayList<Vector3D>();
    
    public TranslateFloat(ArrayList<Long> ids, ArrayList<Vector3D> coordsOld,
            ArrayList<Vector3D> coordsNew){
        
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
    private void translate(GUIObserverInterface goi, ArrayList<Vector3D> coords)
        throws Exception{
        int failcount = 0;
        
        try{
            for(int i=0;i < ids.size();i++){
                Long id = ids.get(i);
                Vector3D p = coords.get(i);
                
                try{
                    goi.notifyTranslation(id, p.x, p.y, p.z);
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
