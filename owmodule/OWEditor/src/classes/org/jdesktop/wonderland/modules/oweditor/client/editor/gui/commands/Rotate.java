package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

public class Rotate implements Command{
    
    ArrayList<Long> ids;
    ArrayList<Vector3D> rotationOld;
    ArrayList<Vector3D> rotationNew;
    
    public Rotate(ArrayList<Long> ids, 
            ArrayList<Vector3D> rotationOld, ArrayList<Vector3D> rotationNew){
        
        this.ids = ids;
        this.rotationOld = rotationOld;
        this.rotationNew = rotationNew;

        //null pointer prevention
        if(ids == null)
            this.ids = new ArrayList<Long>();
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
