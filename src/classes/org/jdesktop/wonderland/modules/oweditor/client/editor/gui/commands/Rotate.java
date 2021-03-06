package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

/**
 * Rotate command.
 * 
 * @author Patrick
 */
public class Rotate implements Command{
    
    ArrayList<Long> ids = new ArrayList<Long>();
    ArrayList<Vector3D> rotationOld = new ArrayList<Vector3D>();
    ArrayList<Vector3D> rotationNew= new ArrayList<Vector3D>();
    
    public Rotate(ArrayList<Long> ids, 
            ArrayList<Vector3D> rotationOld, ArrayList<Vector3D> rotationNew){
        
        //null pointer prevention
        if(ids == null || rotationOld == null || rotationNew == null)
            return;
        
        this.ids.addAll(ids);
        this.rotationOld.addAll(rotationOld);
        this.rotationNew.addAll(rotationNew);

    }


    @Override
    public void execute(GUIObserverInterface goi)  throws Exception{
        rotate(goi, rotationNew);
    }

    @Override
    public void undo(GUIObserverInterface goi)  throws Exception{
        rotate(goi, rotationOld);
    }

    @Override
    public void redo(GUIObserverInterface goi)  throws Exception{
        execute(goi);
    }
    
    /**
     * Rotates, using the given lists.
     * 
     * @param goi A guiobserverinterface instance.
     * @param coords A list of coordinates.
     * @param rotation A list of rotation values.
     */
    private void rotate(GUIObserverInterface goi, 
            ArrayList<Vector3D> rotation) throws Exception{
        int failcount = 0;
        
        try{
            for(int i=0;i < ids.size();i++){
                Long id = ids.get(i);
                Vector3D rot = rotation.get(i);
                
                try{
                    goi.notifyRotation(id, rot.x, rot.y, rot.z);
                }catch(Exception e){
                    failcount++;
                }
            }
        }catch(Exception e){
            System.err.println("Rotate command: list size dont match");
            throw e;
        }
        
        //the command only failed, when every operation failed!
        if(failcount == ids.size())
            throw new CommandException();
    }


}
