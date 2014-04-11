package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

/**
 * A rights class.
 * 
 * @author Patrick
 */
public class Rights{
    
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

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public boolean getOwner() {
        return isOwner;
    }

    public boolean getPermChangeAbilities() {
        return permitAbilityChange;
    }

    public boolean getPermAddSubObjects() {
        return permitSubObjects;
    }

    public boolean getPermMove() {
        return permitMove;
    }

    public boolean getPermView() {
        return permitView;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public boolean isEverybody() {
        return isEverybody;
    }
    
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
    

}
