package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

public class SetRights implements Command{

    private String oldName;
    private String oldType;
    private boolean oldOwner;
    private boolean oldAddSubObjects;
    private boolean oldChangeAbilities;
    private boolean oldMove;
    private boolean oldView;
    private String newName;
    private String newType;
    private boolean newOwner;
    private boolean newAddSubObjects;
    private boolean newChangeAbilities;
    private boolean newMove;
    private boolean newView;
    private boolean toRemove;
    private ArrayList<Long> ids;
    
    public SetRights(ArrayList<Long> ids,
            String oldType, String oldName, boolean oldOwner, 
            boolean oldAddSubObjects, boolean oldChangeAbilities,
            boolean oldMove, boolean oldView,
            String newType, String newName, boolean newOwner, 
            boolean newAddSubObjects, boolean newChangeAbilities,
            boolean newMove, boolean newView, boolean toRemove){
        this.ids = ids;
        this.oldName = oldName;
        this.oldType = oldType;
        this.oldOwner = oldOwner;
        this.oldAddSubObjects = oldAddSubObjects;
        this.oldChangeAbilities = oldChangeAbilities;
        this.oldMove = oldMove;
        this.oldView = oldView;

        this.newName = newName;
        this.newType = newType;
        this.newOwner = newOwner;
        this.newAddSubObjects = newAddSubObjects;
        this.newChangeAbilities = newChangeAbilities;
        this.newMove = newMove;
        this.newView = newView;
        this.toRemove = toRemove;
    }
    
    

    @Override
    public void execute(GUIObserverInterface goi) throws Exception {
        for(long id : ids){
            if(!toRemove)
                goi.setRight(id, oldType, oldName, 
                        newType, newName, newOwner, newAddSubObjects,
                        newChangeAbilities, newMove, newView);
            else
                goi.removeRight(id, oldType, oldName);
        }
    }

    @Override
    public void undo(GUIObserverInterface goi) throws Exception {
        for(long id : ids){
            if(oldName == null)
                goi.removeRight(id, newType, newName);
            else
                goi.setRight(id, newType, newName,
                        oldType, oldName, oldOwner, oldAddSubObjects,
                        oldChangeAbilities, oldMove, oldView); 
        }
        
    }

    @Override
    public void redo(GUIObserverInterface goi) throws Exception {
        execute(goi);
    }

}
