package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames;

public interface IPermissionTableEntry {
    
    public String getName();
    
    public String getType();
    
    public boolean getOwner();
    
    public boolean getPermSubObjects();
    
    public boolean getPermAbilityChange();
    
    public boolean getPermMove();
    
    public boolean getPermView();
    
    public boolean getToRemove();

}
