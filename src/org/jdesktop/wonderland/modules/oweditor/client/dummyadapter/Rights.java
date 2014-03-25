package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

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
    

}
