package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames;

/**
 * The permission table entry interface.
 * 
 * @author Patrick
 *
 */
public interface IPermissionTableEntry {
    
    /**
     * Returns the user name.
     * 
     * @return The name.
     */
    public String getName();
    
    /**
     * Returns the type.
     * 
     * @return The type.
     */
    public String getType();
    
    /**
     * Returns whether or not the user is its owner or not.
     * 
     * @return A boolean.
     */
    public boolean getOwner();
    
    /**
     * Returns whether or not the user is allowed to create
     * sub-objects.
     * 
     * @return A boolean.
     */
    public boolean getPermSubObjects();
    
    /**
     * Returns whether or not the user is allowed to change
     * abilities.
     * 
     * @return A boolean.
     */
    public boolean getPermAbilityChange();
    
    /**
     * Returns whether or not the user is allowed to move the object.
     * 
     * @return A boolean.
     */
    public boolean getPermMove();
    
    /**
     * Returns whether or not the user is allowed to view
     * the object.
     * 
     * @return A boolean.
     */
    public boolean getPermView();
    
    /**
     * Returns whether or not this permission is
     * to be deleted.
     *  
     * @return A boolean.
     */
    public boolean getToRemove();

    /**
     * Returns the old user name.
     * 
     * @return The user name.
     */
    public String getOldName();

    /**
     * Returns the old type.
     * 
     * @return  The old type.
     */
    public String getOldType();

}
