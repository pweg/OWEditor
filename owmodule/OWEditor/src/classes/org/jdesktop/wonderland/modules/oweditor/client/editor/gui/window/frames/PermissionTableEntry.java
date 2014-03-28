package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames;

public class PermissionTableEntry implements IPermissionTableEntry{

    protected String oldName;
    protected String oldType;
    protected String name;
    protected String type;
    protected boolean owner;
    protected boolean addSubObjects;
    protected boolean changeAbilities;
    protected boolean move;
    protected boolean view;
    protected boolean isEditable;
    protected boolean isEverybody;
    protected boolean toRemove = false;
    protected boolean isNew = false;
    
    public PermissionTableEntry(String type, String name, boolean owner, 
            boolean addSubObjects, boolean changeAbilities,
            boolean move, boolean view, boolean editable, boolean
            isEverybody){

        this.oldName = name;
        this.oldType = type;
        this.name = name;
        this.type = type;
        this.owner = owner;
        this.addSubObjects = addSubObjects;
        this.changeAbilities = changeAbilities;
        this.move = move;
        this.view = view;
        this.isEditable = editable;
        this.isEverybody = isEverybody;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof PermissionTableEntry))
            return false;
        
        PermissionTableEntry entry = (PermissionTableEntry) o;

        if(!entry.getName().equals(name))
            return false;
        if(!entry.getType().equals(type))
            return false;
        if(entry.getOwner() != owner)
            return false;
        if(entry.getPermAbilityChange() != changeAbilities)
            return false;
        if(entry.getPermSubObjects() != addSubObjects)
            return false;
        if(entry.getPermMove() != move)
            return false;
        if(entry.getPermView() != view)
            return false;
        if(entry.isEditable != isEditable)
            return false;
        if(entry.isEverybody != isEverybody)
            return false;
        
        return true;
    }
    
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getType() {
        return type;
    }
    @Override
    public boolean getOwner() {
        return owner;
    }
    @Override
    public boolean getPermSubObjects() {
        return addSubObjects;
    }
    @Override
    public boolean getPermAbilityChange() {
        return changeAbilities;
    }
    @Override
    public boolean getPermMove() {
        return move;
    }
    @Override
    public boolean getPermView() {
        return view;
    }

    @Override
    public boolean getToRemove() {
        return toRemove;
    }

    @Override
    public String getOldName() {
        return oldName;
    }

    @Override
    public String getOldType() {
        return oldType;
    }

}
