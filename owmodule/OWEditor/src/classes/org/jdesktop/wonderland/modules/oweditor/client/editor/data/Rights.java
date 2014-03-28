package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IRights;

public class Rights implements IRights{
    
    private String type = null;
    private String name = null;
    private boolean isOwner;
    private boolean permitSubObjects;
    private boolean permitAbilityChange;
    private boolean permitMove;
    private boolean permitView;
    private boolean isEditable;
    private boolean isEverybody;
    
    public Rights(String type, String name, boolean isOwner,
            boolean permitSubObjects, boolean permitAbilityChange,
            boolean permitMove, boolean permitView, 
            boolean isEditable, boolean isEverybody){
        this.type = type;
        this.name = name;
        this.isOwner = isOwner;
        this.permitSubObjects = permitSubObjects;
        this.permitAbilityChange = permitAbilityChange;
        this.permitMove = permitMove;
        this.permitView = permitView;
        this.isEditable = isEditable;
        this.isEverybody = isEverybody;
    }


    @Override
    public void set(String type, String name, boolean owner,
            boolean addSubObjects, boolean changeAbilities, boolean move,
            boolean view) {
        this.type = type;
        this.name = name;
        this.isOwner = owner;
        this.permitSubObjects = addSubObjects;
        this.permitAbilityChange = changeAbilities;
        this.permitMove = move;
        this.permitView = view;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean getOwner() {
        return isOwner;
    }

    @Override
    public boolean getPermChangeAbilities() {
        return permitAbilityChange;
    }

    @Override
    public boolean getPermAddSubObjects() {
        return permitSubObjects;
    }

    @Override
    public boolean getPermMove() {
        return permitMove;
    }

    @Override
    public boolean getPermView() {
        return permitView;
    }

    @Override
    public boolean isEditable() {
        return isEditable;
    }

    @Override
    public boolean isEverybody() {
        return isEverybody;
    }
    
    @Override
    public boolean equals(Object o){
        
        if(!(o instanceof Rights))
            return false;
        
        Rights right = (Rights) o;
        
        if(!right.getName().equals(name))
            return false;
        if(!right.getType().equals(type))
            return false;
        if(right.isEditable() != isEditable)
            return false;
        if(right.isEverybody() != isEverybody)
            return false;
        if(right.getOwner() != isOwner)
            return false;
        if(right.getPermChangeAbilities() != permitAbilityChange)
            return false;
        if(right.getPermMove() != permitMove)
            return false;
        if(right.getPermAddSubObjects() != permitSubObjects)
            return false;
        if(right.getPermView() != permitView)
            return false;
        
        return true;
    }
    

}
